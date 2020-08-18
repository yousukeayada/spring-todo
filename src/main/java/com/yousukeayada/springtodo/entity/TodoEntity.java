package com.yousukeayada.springtodo.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "todolist")
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    @Column(nullable = false)
    public String todo;

    @Column(nullable = true, columnDefinition = "DATE")
    public LocalDate deadline;
}