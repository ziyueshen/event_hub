package org.szy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.szy.entity.Event;

import java.util.List;

public interface EventService  extends IService<Event> {
    Event queryById(String id);
    boolean update(Event event);
    List<Event> matchEvents();

    void saveEvent2Redis(Long id, Long expireSeconds) throws InterruptedException;
}
