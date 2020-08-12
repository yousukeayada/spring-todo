package com.yousukeayada.springtodo.repository;

import com.yousukeayada.springtodo.entity.TodoEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoEntity, Integer> {
    
}