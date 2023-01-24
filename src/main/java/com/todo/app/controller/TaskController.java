package com.todo.app.controller;

import com.todo.app.entity.Task;
import com.todo.app.producer.RabbitMQProducer;
import com.todo.app.service.TaskService;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/task")
@CrossOrigin(origins = "*")
public class TaskController {
    @Autowired
    TaskService service;

    @Autowired
    RabbitMQProducer producer;

    @PostMapping("/add")
    public Task addTask(@RequestBody Task todo) {
        Task response = service.addTask(todo);
        producer.sendMessage(response);
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteTask(@PathVariable int id) {
        ResponseEntity<Map<String, Boolean>> response = service.deleteTask(id);
        producer.sendMessage(response);
        return response;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Task task) {
        Task updatedTask = service.updateTask(id, task);
        ResponseEntity<Task> response = ResponseEntity.ok(updatedTask);
        producer.sendMessage(response);
        return response;
    }

    @DeleteMapping("deleteAll")
    public void deleteAll() {
        service.deleteAll();
    }

    @GetMapping("/getAll")
    public List<Task> getAllTasks() {
        List<Task> response = service.getAllTasks();
        producer.sendMessage(response);
        return response;
    }

    @GetMapping("/load")
    public void executeBatch() {
        try {
            service.load();
        } catch (JobParametersInvalidException e) {
            throw new RuntimeException(e);
        } catch (JobExecutionAlreadyRunningException e) {
            throw new RuntimeException(e);
        } catch (JobRestartException e) {
            throw new RuntimeException(e);
        } catch (JobInstanceAlreadyCompleteException e) {
            throw new RuntimeException(e);
        }
    }
}
