package org.szy.service.Impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.lang.Pair;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.szy.common.BaseContext;
import org.szy.entity.Event;
import org.szy.entity.User;
import org.szy.mapper.EventMapper;
import org.szy.service.EventService;
import org.szy.service.UserService;
import org.szy.utils.AlgorithmUtils;
import org.szy.utils.RedisData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.szy.utils.RedisConstants.*;

@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService {
    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);
    @Autowired
    private UserService userService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Event queryById(String id) {
        // Event event = queryWithMutex(id);
        Event event = queryWithLogicalExpire(id);
        return event;
    }

    private Event queryWithLogicalExpire(String id) {
        String key = CACHE_EVENT_KEY + id;
        // query redis
        String eventJSON = stringRedisTemplate.opsForValue().get(key);
        // since we put data in redis in advance, if not in redis, directly return null
        if (StringUtils.isBlank(eventJSON)) {
            return null;
        }
        // deserialize json, then check logical expire time
        RedisData redisData = JSON.parseObject(eventJSON, RedisData.class);
        JSONObject eventJsonObject = (JSONObject) redisData.getData();
        Event event = eventJsonObject.toJavaObject(Event.class);
        LocalDateTime expireTime = redisData.getExpireTime();
        if (expireTime.isAfter(LocalDateTime.now())) {
            // not expired, return data
            return event;
        }
        // expired, try to acquire lock
        String lockKey = LOCK_EVENT_KEY + id;

        boolean isLock = tryLock(lockKey);
        if (isLock) {
            // get lock, return expired data, start new thread and rebuild cache
            CACHE_REBUILD_EXECUTOR.submit(() -> {
                try {
                    saveEvent2Redis(Long.valueOf(id), 20L);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    unlock(lockKey);
                }
            });
        }

        // can't get lock, return expired data
        return event;
    }

    /**
     * Mutual exclusive lock
     * @param id
     * @return
     */
    private Event queryWithMutex(String id) {
        String key = CACHE_EVENT_KEY + id;
        // query redis
        String eventJSON = stringRedisTemplate.opsForValue().get(key);

        if (StringUtils.isNotBlank(eventJSON)) {
            Event event = JSON.parseObject(eventJSON, Event.class);
            return event;
        }
        // if we have sth in redis, since we put "" to avoid cache penetration, we need to check
        if (eventJSON != null) {
            return null;
        }
        // not exist in redis, cache rebuild
        // 1) get lock
        String lockKey = LOCK_EVENT_KEY + id;
        Event event = null;
        try {
            boolean isLock = tryLock(lockKey);
            // 2) check if we get lock successfully
            // 3) can't get lock, sleep and retry
            if (!isLock) {
                Thread.sleep(50);
                return queryWithMutex(id); // recursion, retry
            }
            // 4) get lock, query redis again, query mysql
            eventJSON = stringRedisTemplate.opsForValue().get(key);

            if (StringUtils.isNotBlank(eventJSON)) {
                event = JSON.parseObject(eventJSON, Event.class);
                return event;
            }
            // then query mysql
            event = getById(id);
            // simulate the latency of cache rebuild
            Thread.sleep(200);
            // 5) not in mysql, put "" in redis to avoid cache penetration
            if (event == null) {
                stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
                return null;
            }
            // 6) write redis
            stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(event), CACHE_EVENT_TTL, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {   // no matter try or catch
            // 7) free lock
            unlock(lockKey);
        }

        return event;
    }
    public Event queryWithPenetration(String id) {
        String key = CACHE_EVENT_KEY + id;
        // query redis
        String eventJSON = stringRedisTemplate.opsForValue().get(key);

        if (StringUtils.isNotBlank(eventJSON)) {
            Event event = JSON.parseObject(eventJSON, Event.class);
            return event;
        }
        // if we have sth in redis, since we put "" to avoid cache penetration, we need to check
        if (eventJSON != null) {
            return null;
        }
        // not exist, query mysql
        Event event = getById(id);
        // not in mysql, put "" in redis to avoid cache penetration
        if (event == null) {
            stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
            return null;
        }
        // write redis
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(event), CACHE_EVENT_TTL, TimeUnit.MINUTES);
        return event;
    }

    @Override
    @Transactional
    public boolean update(Event event) {
        Long eventId = event.getId();
        if (eventId == null) return false;
        // first update database
        updateById(event);
        // then delete cache
        stringRedisTemplate.delete(CACHE_EVENT_KEY + event.getId());
        return true;
    }

    @Override
    public List<Event> matchEvents() {
        Long userID = BaseContext.getCurrentId();
        User user = userService.getById(userID);
        LambdaQueryWrapper<Event> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Event::getCity, user.getPreferCity());
        List<Event> eventList = this.list(queryWrapper);
        String tagsCategory = user.getPreferCategory();
        String tagsFormat = user.getPreferFormat();
        List<String> tagCategoryList = JSON.parseArray(tagsCategory, String.class);
        List<String> tagsFormatList = JSON.parseArray(tagsFormat, String.class);
        tagCategoryList.addAll(tagsFormatList);
        List<String> userTagList = tagCategoryList;
        // event and similarity
        List<Pair<Event, Long>> list = new ArrayList<>();

        for (int i = 0; i < eventList.size(); i++) {
            Event event = eventList.get(i);

            List<String> eventTagList = new LinkedList<>();
            eventTagList.add(event.getCategory());
            eventTagList.add(event.getFormat());
            // calculate similarity
            long distance = AlgorithmUtils.minDistance(userTagList, eventTagList);
            list.add(new Pair<>(event, distance));
        }
        // sort
        List<Pair<Event, Long>> topEventPairList = list.stream()
                .sorted((a, b) -> (int) (a.getValue() - b.getValue()))
                .limit(16)
                .toList();

        return topEventPairList.stream().map(Pair::getKey).collect(Collectors.toList());
    }

    private boolean tryLock(String key) {
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 10L, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(flag);  // use util to avoid null pointer exception
    }
    private void unlock(String key) {
        stringRedisTemplate.delete(key);
    }
    // customize logical expire time
    public void saveEvent2Redis(Long id, Long expireSeconds) throws InterruptedException {
        // query
        Event event = getById(id);
        // simulate the cache rebuild process
        // Thread.sleep(200);
        // set logical expire time
        RedisData redisData = new RedisData();
        redisData.setData(event);
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(expireSeconds));
        // write to redis
        stringRedisTemplate.opsForValue().set(CACHE_EVENT_KEY + id, JSON.toJSONString(redisData));
        // no real expire time, only logical expire time
    }
}
