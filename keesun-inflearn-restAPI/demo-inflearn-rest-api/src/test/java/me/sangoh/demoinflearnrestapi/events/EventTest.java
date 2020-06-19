package me.sangoh.demoinflearnrestapi.events;


import me.sangoh.demoinflearnrestapi.common.TestDescription;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EventTest {

    @Test
    public void builder() {
        Event event = Event.builder()
                .name("Inflearn Spring REST API")
                .description("REST API development with Spring")
                .build();
        assertNotNull(event);
    }

    @Test
    public void javaBean() {
        //given
        Event event = new Event();
        String bean_event = "bean Event";
        String spring = "Spring";

        //when
        event.setName(bean_event);
        event.setDescription(spring);

        //then
        assertNotNull(event);
        assertEquals(bean_event, event.getName());
        assertEquals(spring, event.getDescription());
    }

}