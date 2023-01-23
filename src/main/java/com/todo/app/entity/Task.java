package com.todo.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "task")
    private String task;
    @Column(name = "completed")
    private boolean isCompleted = false;

    @Column(name = "user_id", nullable = false)
    private int userId;

    public Task(int id, String task, int userId) {
        this.id = id;
        this.task = task;
        this.userId = userId;
    }
}
