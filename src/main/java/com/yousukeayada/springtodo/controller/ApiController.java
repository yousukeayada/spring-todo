package com.yousukeayada.springtodo.controller;

import com.yousukeayada.springtodo.entity.TodoEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    
    @GetMapping("/api")
    public TodoEntity api() {
        TodoEntity te = new TodoEntity();
        te.id = 1;
        te.todo = "sample";
        return te;
    }
}