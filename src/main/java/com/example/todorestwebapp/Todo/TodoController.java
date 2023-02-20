package com.example.todorestwebapp.Todo;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class TodoController {

    private final TodoRepository repository;
    private final TodoModelAssembler assembler;

    TodoController(TodoRepository repository, TodoModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/todo/{id}")
    EntityModel<Todo> one(@PathVariable Long id) {

        Todo todo = repository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));

        return assembler.toModel(todo);
    }

    @GetMapping("/todo")
    CollectionModel<EntityModel<Todo>> all() {

        List<EntityModel<Todo>> todo = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(todo, linkTo(methodOn(TodoController.class).all()).withSelfRel());
    }

    @PostMapping("/todo")
    ResponseEntity<?> newTodo(@RequestBody Todo newTodo) {

        EntityModel<Todo> entityModel = assembler.toModel(repository.save(newTodo));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }

    @PutMapping("/todo/{id}")
    ResponseEntity<?> checkTodo(@PathVariable Long id){

        Todo updatedTodo = repository.findById(id)
                .map(todo -> {
                    todo.setCompleted(!todo.isCompleted());
                    return repository.save(todo);
                })
                .orElseThrow(() -> new TodoNotFoundException(id));

        EntityModel<Todo> entityModel = assembler.toModel(updatedTodo);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
