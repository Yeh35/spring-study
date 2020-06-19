package me.sangoh.demoinflearnrestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebMvc
public class EventControllerTests {

    // Mocking 되어있는 디스페쳐 서블릿 상대로 가짜 요청 만들어서 서블릿에 보내고 응답을 받는다.
    // Spring MVC Test의 핵심 Class
    // 웹서버를 띄위지 않기 떄문에 조금 더 빠르다. 하지만 디스페쳐 서블릿 만들어야하기에 단위 테스트보다 빠르지 않다.
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void createEvent() throws Exception {
        EventDto event = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2020, 6, 19, 10, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2020, 6, 21, 10, 0))
                .beginEventDateTime(LocalDateTime.of(2020, 6, 21, 11, 0))
                .endEventDateTime(LocalDateTime.of(2020, 6, 25, 12, 0))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .build();

        mockMvc.perform(post("/api/events/")
                    .contentType(MediaType.APPLICATION_JSON) // 요청하는 형태
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(event))) //원하는 응답 형태
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("free").value(Matchers.not(true)))
        ;

    }

    @Test
    public void createEvent_Bad_c() throws Exception {
        Event event = Event.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2020, 6, 19, 10, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2020, 6, 21, 10, 0))
                .beginEventDateTime(LocalDateTime.of(2020, 6, 21, 11, 0))
                .endEventDateTime(LocalDateTime.of(2020, 6, 25, 12, 0))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .free(true)
                .offline(false)
                .build();

        mockMvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON) // 요청하는 형태
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event))) //원하는 응답 형태
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;

    }
}
