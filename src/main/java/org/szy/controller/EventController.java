package org.szy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.szy.common.BaseContext;
import org.szy.common.R;
import org.szy.entity.Event;
import org.szy.entity.User;
import org.szy.service.EventService;
import org.szy.service.UserService;
import org.szy.utils.JwtUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/card")
@CrossOrigin
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;

    @GetMapping("/match")
    public R<List<Event>> match() {
        List<Event> matchedEvents = eventService.matchEvents();
        return R.success(matchedEvents);
    }

    @GetMapping("/recommend")
    public R<Page<Event>> recommendUsers(long pageSize, long pageNum) {
        User loginUser = userService.getById(BaseContext.getCurrentId());
//        String redisKey = String.format("yupao:user:recommend:%s", loginUser.getId());
//        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
//
//        Page<User> userPage = (Page<User>) valueOperations.get(redisKey);
//        if (userPage != null) {
//            return ResultUtils.success(userPage);
//        }

        Page<Event> eventPage = new Page(pageNum, pageSize);
        LambdaQueryWrapper<Event> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Event::getCity,
                loginUser.getPreferCity());
        queryWrapper.orderByDesc(Event::getUpdateTime);
        eventService.page(eventPage, queryWrapper);
        // 写缓存
//        try {
//            valueOperations.set(redisKey, userPage, 30000, TimeUnit.MILLISECONDS);
//        } catch (Exception e) {
//            log.error("redis set key error", e);
//        }
        return R.success(eventPage);
    }
    @GetMapping("/list")
    public R list() {
        LambdaQueryWrapper<Event> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.lt(Event::getStatus, 4);
        List<Event> list = null;
        list = eventService.list(queryWrapper);
        Map<String, List<Event>> mp = new HashMap<>();
        mp.put("items", list);
        return R.success(mp);
    }
    @PostMapping("/create")
    public R create(@RequestBody Event event) throws InterruptedException {
        Long userID = BaseContext.getCurrentId();
        event.setCreatorId(userID);
        event.setStatus(0);
        event.setNumSignedUp(0);
        //event.setStartSignUpTime(LocalDateTime.now());
        //event.setEndSignUpTime(event.getStartTime());
        event.setEndTime(event.getStartTime());
        event.setCreateTime(LocalDateTime.now());
        event.setUpdateTime(LocalDateTime.now());
        eventService.save(event);
        eventService.saveEvent2Redis(event.getId(), 10L);
        return R.success();
    }
    @PostMapping("/update")
    public R update(@RequestBody Event event) throws InterruptedException {
        Long userID = BaseContext.getCurrentId();
        event.setCreatorId(userID);
        event.setStatus(0);
        event.setNumSignedUp(0);
        event.setEndTime(event.getStartTime());
        event.setUpdateTime(LocalDateTime.now());
        eventService.update(event);
        eventService.saveEvent2Redis(event.getId(), 10L);
        return R.success();
    }
    @GetMapping("/created")
    public R<Page<Event>> created(long pageSize, long pageNum) {
        Page<Event> eventPage = new Page(pageNum, pageSize);
        LambdaQueryWrapper<Event> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Event::getStatus, 0);
        Long userId = BaseContext.getCurrentId();
        queryWrapper.eq(Event::getCreatorId, userId);
        eventService.page(eventPage, queryWrapper);
        return R.success(eventPage);
    }
    @GetMapping("/detail")
    public R detail(String id) {
        LambdaQueryWrapper<Event> queryWrapper = new LambdaQueryWrapper<>();
        Event event = eventService.queryById(id);
        if (event == null) return R.error("event does not exist");
        event.setImage("/images/" + event.getImage());
        Map<String, Event> mp = new HashMap<>();
        mp.put("items", event);
        return R.success(mp);
    }
}
