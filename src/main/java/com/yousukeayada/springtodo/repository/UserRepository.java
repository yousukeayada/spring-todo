package com.yousukeayada.springtodo.repository;

import com.yousukeayada.springtodo.entity.LoginUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<LoginUser, Long> {
    // 引数名はエンティティのフィールド名と同じにする。
    LoginUser findByUsername(String username);
}