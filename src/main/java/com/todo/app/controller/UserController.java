package com.todo.app.controller;

import com.todo.app.consumer.Consumer;
import com.todo.app.entity.Task;
import com.todo.app.entity.User;
import com.todo.app.producer.RabbitMQProducer;
import com.todo.app.service.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService service;

    @Autowired
    private RabbitMQProducer producer;

    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        User newUser = null;
        try {
            newUser = service.addUser(user);
        } catch (Exception e) {
            return newUser;
        }
        producer.sendMessage(newUser);
        producer.sendMessageToUserQueue(new Consumer(user.getUsername(), 0));
        return user;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable int id) {
        ResponseEntity<Map<String, Boolean>> response = service.deleteUser(id);
        producer.sendMessage(response);
        return response;
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Map<String, String>> deleteAll() {
        ResponseEntity<Map<String, String>> response = service.deleteAll();
        producer.sendMessage(response);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        ResponseEntity<Map<String, String>> response = service.login(user);
        producer.sendMessage(response);
        return response;
    }

    @GetMapping("/task/{id}")
    public List<Task> getTasks(@PathVariable int id) {
        List<Task> list = service.getAllTasks(id);
        producer.sendMessage(list);
        return list;
    }

}
