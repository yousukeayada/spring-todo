package com.yousukeayada.springtodo.controller;

import java.util.List;

import com.yousukeayada.springtodo.entity.TodoEntity;
import com.yousukeayada.springtodo.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @Autowired
    TodoService todoService;
    

    @GetMapping("/api/todos/{id}")
    public TodoEntity get(@PathVariable("id") int id) {
        return todoService.getTodo(id);
    }


    @GetMapping("/api/todos")
    public List<TodoEntity> getAll() {
        return todoService.getAllTodo();
    }


    @PostMapping("/api/todos")
    public TodoEntity add(@RequestBody TodoEntity te) {
        todoService.addTodo(te);
        System.out.println("追加：" + te.todo);
        return te;
    }


    @PutMapping("/api/todos/{id}")
    public TodoEntity update(@PathVariable("id") int id, @RequestBody TodoEntity te) {
        te.setId(id);
        te = todoService.updateTodo(te);
        System.out.println("更新：" + te.todo);
        return te;
    }


    @DeleteMapping("/api/todos/{id}")
    public TodoEntity delete(@PathVariable("id") int id) {
        TodoEntity te = todoService.getTodo(id);
        todoService.deleteTodo(id);
        System.out.println("削除：" + te.todo);
        return te;
    }
}