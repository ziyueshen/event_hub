package org.szy;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;
import org.szy.entity.Event;
import org.szy.service.EventService;
import org.szy.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.szy.utils.SeedHelper.*;

@SpringBootTest
public class InsertEventTest {
    @Resource
    private EventService eventService;
    @Test
    public void doInsertEvent() {
        Random random = new Random();
        LocalDateTime now = LocalDateTime.now();
        long oneYear = 365 * 24 * 60 * 60; // a year in sec
        long timeRange = 2 * oneYear - oneYear; // 1-2 yrs

        long randomSeconds = TimeUnit.SECONDS.convert(random.nextInt((int) timeRange) + oneYear, TimeUnit.SECONDS);
        LocalDateTime futureDateTimeStart = now.plusSeconds(randomSeconds);
        LocalDateTime futureDateTimeEnd = futureDateTimeStart.plusSeconds(60 * 60);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Event> eventList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < city.length; j++) {
                for (int k = 0; k < 10; k++) {
                    Event event = new Event();
                    int capacity = random.nextInt(7) + 3;
                    String[] formats = allFormat[i];
                    int format = random.nextInt(formats.length);
                    int address = random.nextInt(20);
                    int image = random.nextInt(6);
                    event.setCategory(categories[i]);
                    event.setName(allEvents[i][k]);
                    event.setStatus(0);
                    event.setNumSignedUp(0);
                    event.setDetail(desc);
                    event.setState("State");
                    event.setCity(city[j]);
                    event.setImage(images[image]);
                    event.setCapacity(capacity);
                    event.setAddress(addresses[address]);
                    event.setFormat(formats[format]);
                    event.setStartTime(futureDateTimeStart);
                    event.setEndTime(futureDateTimeEnd);
                    event.setUpdateTime(now);
                    event.setCreateTime(now);
                    event.setCreatorId(1L);
                    eventList.add(event);
                }
            }
        }
        eventService.saveBatch(eventList, 100);
        stopWatch.stop();
        // 15s, 35k
        System.out.println(stopWatch.getTotalTimeMillis());
    }
}
