package com.todo.app.repository;

import com.todo.app.entity.Task;
import com.todo.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT new Task(id, task, userId) FROM Task t WHERE userId = :userId")
    public List<Task> getTasks(int userId);

    public List<User> findByUsername(String username);
}
