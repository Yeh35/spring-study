package me.sangoh.demoinflearnrestapi.common;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import me.sangoh.demoinflearnrestapi.index.IndexController;
import org.springframework.validation.Errors;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public class ErrorsResource extends EntityModel<Errors> {

    public ErrorsResource(Errors errors, Link... links) {
        super(errors, links);
        this.add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
    }

}
