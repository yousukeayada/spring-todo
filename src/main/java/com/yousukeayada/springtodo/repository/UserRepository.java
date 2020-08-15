package com.yousukeayada.springtodo.repository;

import com.yousukeayada.springtodo.entity.LoginUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<LoginUser, Long> {
    
}