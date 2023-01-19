package com.todo.app.controller;

import com.todo.app.entity.Task;
import com.todo.app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@CrossOrigin(origins = "*")
public class TodoListController {
    @Autowired
    TaskService service;

    @PostMapping("/add")
    public void addTask(@RequestBody Task todo) {
        service.addTask(todo);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable int id) {
        service.deleteTask(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Task task) {
        Task updatedTask = service.updateTask(id, task);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("deleteAll")
    public void deleteAll() {
        service.deleteAll();
    }

    @GetMapping("/getAll")
    public List<Task> getAllTasks() {
        return service.getAllTasks();
    }
}
