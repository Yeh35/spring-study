package me.sangoh.demoinflearnrestapi.common;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import me.sangoh.demoinflearnrestapi.index.IndexController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.validation.Errors;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public class ErrorsResource extends RepresentationModel {

    @JsonUnwrapped
    private final Errors errors;

    public ErrorsResource(Errors errors, Link... links) {
        this.errors = errors;
        this.add(links);
        this.add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
    }

    public Errors getErrors() {
        return errors;
    }

}
