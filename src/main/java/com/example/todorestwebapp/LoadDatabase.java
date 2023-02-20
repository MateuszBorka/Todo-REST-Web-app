package com.example.todorestwebapp;

import com.example.todorestwebapp.Todo.Todo;
import com.example.todorestwebapp.Todo.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(TodoRepository repository) {

        return args -> {
            log.info("Preloading..." + repository.save(new Todo("Complete todo app")));
            log.info("Preloading..." + repository.save(new Todo("Registrate to PE in University")));
        };
    }
}
