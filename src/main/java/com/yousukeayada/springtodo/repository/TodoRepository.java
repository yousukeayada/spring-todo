package com.yousukeayada.springtodo.repository;

import com.yousukeayada.springtodo.entity.TodoEntity;

import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<TodoEntity, Integer> {
    
}