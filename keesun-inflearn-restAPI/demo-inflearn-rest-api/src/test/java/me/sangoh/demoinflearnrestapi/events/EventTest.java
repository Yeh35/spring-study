package me.sangoh.demoinflearnrestapi.events;


import me.sangoh.demoinflearnrestapi.common.TestDescription;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testFreeIsTrue() {
        // given
        Event event = Event.builder()
                .basePrice(0)
                .maxPrice(0)
                .build();

        // when
        event.update();

        //then
        assertTrue(event.isFree());
    }

    @Test
    void testFreeIsFalse() {
        // given
        Event[] Events = new Event[3];

        Events[0] = Event.builder()
                .basePrice(100)
                .maxPrice(0)
                .build();

        Events[1] = Event.builder()
                .basePrice(0)
                .maxPrice(100)
                .build();

        Events[2] = Event.builder()
                .basePrice(100)
                .maxPrice(200)
                .build();

        // when
        for (Event event: Events) {
            event.update();

            //then
            assertFalse(event.isFree());
        }
    }
    
    @Test
    public void testOfflineIsTrue() {
        // given
        Event event = Event.builder()
                .location("강남역 네이버 D2 스타텁 팩토리")
                .build();

        // when
        event.update();

        //then
        assertTrue(event.isOffline());
    }


    @Test
    public void testOfflineIsFalse() {

        // given
        Event[] Events = new Event[2];

        Events[0] = Event.builder()
                .build();

        Events[1] = Event.builder()
                .location("")
                .build();

        for (Event event: Events) {
            // when
            event.update();

            //then
            assertFalse(event.isOffline());
        }
    }
}