package com.todo.app.consumer;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "consumer")
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int taskCount;

    public Consumer(String name, int taskCount) {
        this.name = name;
        this.taskCount = taskCount;
    }
}
