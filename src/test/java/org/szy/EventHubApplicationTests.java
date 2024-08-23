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
        eventService.saveEvent2Redis(1826605428809981953L, 10L);
    }
}
