package com.yousukeayada.springtodo.controller;

import java.time.LocalDate;
import java.util.List;

import com.yousukeayada.springtodo.entity.TodoEntity;
import com.yousukeayada.springtodo.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodoController {
    @Autowired
    TodoService todoService;

    @RequestMapping("/home")
    public String home() {
        return "home";
    }
    
    @RequestMapping("/")
    public String index(Model model) {
        // model.addAttribute("todoForm", new TodoEntity());

        List<TodoEntity> todoList = todoService.getAllTodo();
        model.addAttribute("todoList", todoList);


        return "index";
    }


    @PostMapping("/add")
    public String addTodo(@RequestParam("todo") String todo, @RequestParam("deadline") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deadline) {
        TodoEntity te = new TodoEntity();
        te.setTodo(todo);
        te.setDeadline(deadline);
        todoService.addTodo(te);
        System.out.println("追加：" + todo);
        return "redirect:/";
    }


    @PostMapping("/edit")
    public String editTodo(@RequestParam("id") int id, Model model) {
        TodoEntity te = todoService.getTodo(id);
        model.addAttribute("id", te.getId());
        model.addAttribute("todo", te.getTodo());
        model.addAttribute("deadline", te.getDeadline());
        return "edit";
    }


    @PostMapping("/update")
    public String updateTodo(@RequestParam("id") int id, @RequestParam("todo") String todo,
                            @RequestParam("deadline") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deadline) {
        TodoEntity te = new TodoEntity();
        te.setId(id);
        te.setTodo(todo);
        te.setDeadline(deadline);
        todoService.updateTodo(te);
        System.out.println("更新：" + id);
        return "redirect:/";
    }

    
    @PostMapping("/delete")
    public String deleteTodo(@RequestParam("id") int id, @RequestParam("todo") String todo) {
        todoService.deleteTodo(id);
        System.out.println("削除：" + todo);
        return "redirect:/";
    }
}