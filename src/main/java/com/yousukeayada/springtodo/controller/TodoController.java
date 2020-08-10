package com.yousukeayada.springtodo.controller;

import java.util.List;

import com.yousukeayada.springtodo.entity.TodoEntity;
import com.yousukeayada.springtodo.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodoController {
    @Autowired
    TodoService todoService;
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("todoForm", new TodoEntity());

        List<TodoEntity> todoList = todoService.getAllTodo();
        model.addAttribute("todoList", todoList);

        return "index";
    }


    @PostMapping("/add")
    public String addTodo(@ModelAttribute("todoForm") TodoEntity te) {
        todoService.addTodo(te);
        System.out.println("追加：" + te.todo);
        return "redirect:/";
    }

    
    @PostMapping("/delete")
    public String deleteTodo(@RequestParam("id") int id, @RequestParam("todo") String todo) {
        todoService.deleteTodo(id);
        System.out.println("削除：" + todo);
        return "redirect:/";
    }
}