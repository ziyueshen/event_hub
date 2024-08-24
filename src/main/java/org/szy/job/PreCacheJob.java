package org.szy.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import jakarta.annotation.Resource;
import org.szy.entity.Event;
import org.szy.service.EventService;
import org.szy.service.Impl.EventServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.szy.utils.RedisConstants.CACHE_PRE_KEY;

@Component
@Slf4j
public class PreCacheJob {

    @Resource
    private EventServiceImpl eventService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedissonClient redissonClient;


    // execute every day
    @Scheduled(cron = "0 0 10 * * *")   // sec, min, hr, day, mon, yr
    public void doCacheRecommendUser() {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id");
        List<Event> eventList = eventService.list(queryWrapper);
        RLock lock = redissonClient.getLock(CACHE_PRE_KEY);
        try {
            // only one thread can get lock
            if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
                System.out.println("getLock: " + Thread.currentThread().getId());
                for (Event event: eventList) {
                    eventService.saveEvent2Redis(event.getId(), 10L);
                }
                log.info("cache warming completed");
            }
        } catch (InterruptedException e) {
            log.error("doCacheRecommendUser error", e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                System.out.println("unLock: " + Thread.currentThread().getId());
                lock.unlock();
            }
        }
    }

}
