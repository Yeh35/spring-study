package me.sangoh.demoinflearnrestapi.events;


import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

//Object Mapper에 의해서 Json으로 변환되는데 그때 Bean Sirealriser을 이용
public class EventResource extends RepresentationModel {

    @JsonUnwrapped //안 사용하면 "event": {} 이런식으로 안에 들어간다.
    private Event event;

    public EventResource(Event event) {
        this.event = event;
        this.add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
    }

    public Event getEvent() {
        return event;
    }

}
