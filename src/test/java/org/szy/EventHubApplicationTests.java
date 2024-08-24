package org.szy;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.szy.service.Impl.EventServiceImpl;

@SpringBootTest
public class EventHubApplicationTests {
    @Resource
    private EventServiceImpl eventService;
    @Test
    void testSaveEvent() throws InterruptedException {
        eventService.saveEvent2Redis(1826937712104808450L, 20L);
    }
}
