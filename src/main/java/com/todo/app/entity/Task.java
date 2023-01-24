package com.todo.app.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

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
    @Column(name = "creationTS")
    private Date creationTS;

    public Task(int id, String task, int userId, Date creationTS) {
        this.id = id;
        this.task = task;
        this.userId = userId;
        this.creationTS = creationTS;
    }
}
