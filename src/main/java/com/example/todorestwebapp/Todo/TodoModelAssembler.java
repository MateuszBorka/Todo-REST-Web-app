package com.example.todorestwebapp.Todo;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TodoModelAssembler {

    public EntityModel<Todo> toModel(Todo todo){

        return EntityModel.of(todo,
                linkTo(methodOn(TodoController.class).one(todo.getId())).withSelfRel(),
                linkTo(methodOn(TodoController.class).all()).withRel("todo"));
    }
}
