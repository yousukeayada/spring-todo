package com.yousukeayada.springtodo.service;

import java.util.ArrayList;
import java.util.List;

import com.yousukeayada.springtodo.entity.TodoEntity;
import com.yousukeayada.springtodo.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;


    public List<TodoEntity> getAllTodo() {
        Iterable<TodoEntity> result = todoRepository.findAll();
        ArrayList<TodoEntity> todoList = new ArrayList<>();
        result.forEach(todoList::add);

        return todoList;
    }

    
    public void addTodo(TodoEntity te) {
        todoRepository.save(te);
    }


    public void deleteTodo(int id) {
        todoRepository.deleteById(id);
    }
}