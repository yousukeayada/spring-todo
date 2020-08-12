package com.yousukeayada.springtodo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.yousukeayada.springtodo.entity.TodoEntity;
import com.yousukeayada.springtodo.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;


    public TodoEntity getTodo(int id) {
        Optional<TodoEntity> result = todoRepository.findById(id);
        try {
            return result.get();
        } catch (Exception e) {
            System.out.println(e);
            return new TodoEntity();
        }
    }


    public List<TodoEntity> getAllTodo() {
        Iterable<TodoEntity> result = todoRepository.findAll(Sort.by(Sort.Direction.ASC, "deadline"));
        ArrayList<TodoEntity> todoList = new ArrayList<>();
        result.forEach(todoList::add);

        return todoList;
    }

    
    public void addTodo(TodoEntity te) {
        todoRepository.save(te);
    }


    public TodoEntity updateTodo(TodoEntity te) {
        Optional<TodoEntity> result = todoRepository.findById(te.getId());
        if(result.isPresent()) {
            todoRepository.save(te);
            return te;
        }else {
            return new TodoEntity();
        }
    }


    public void deleteTodo(int id) {
        todoRepository.deleteById(id);
    }
}