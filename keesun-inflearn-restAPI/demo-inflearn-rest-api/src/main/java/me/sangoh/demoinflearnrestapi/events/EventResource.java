package me.sangoh.demoinflearnrestapi.events;


import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

//Object Mapper에 의해서 Json으로 변환되는데 그때 Bean Sirealriser을 이용
public class EventResource extends EntityModel<Event> {

    public EventResource(Event event, Link... links) {
        super(event, links);
        this.add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
    }

}
