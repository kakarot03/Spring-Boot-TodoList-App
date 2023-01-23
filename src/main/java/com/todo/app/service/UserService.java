package com.todo.app.service;

import com.todo.app.entity.Task;
import com.todo.app.entity.User;
import com.todo.app.exception.UserNotFoundException;
import com.todo.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User addUser(User user) {
        List<User> userList = userRepository.findByUsername(user.getUsername());
        User u = userList.isEmpty() ? null : userList.get(0);
        if (u != null) {
            throw new RuntimeException("User already exists!");
        }
        return userRepository.save(user);
    }

    public ResponseEntity<Map<String, Boolean>> deleteUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No User exists with id " + id));
        userRepository.delete(user);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, String>> deleteAll() {
        userRepository.deleteAll();
        Map<String, String> map = new HashMap<>();
        map.put("status","202");
        map.put("message","Deleted every users");
        return ResponseEntity.ok(map);
    }

    public ResponseEntity<Map<String, String>> login(User userDetails) {
        Map<String, String> response = new HashMap<>();
        List<User> list = userRepository.findByUsername(userDetails.getUsername());
        User user = list.isEmpty() ? null : list.get(0);

        if (user == null || !user.getPassword().equals(userDetails.getPassword())) {
            response.put("status", "failed");
            response.put("message", "Wrong Credentials");
            response.put("username", userDetails.getUsername());
            response.put("password", userDetails.getPassword());
            return ResponseEntity.badRequest().body(response);
        }
        response.put("status", "success");
        response.put("userId", user.getId() + "");

        return ResponseEntity.ok(response);
    }

    public List<Task> getAllTasks(int userId) {
        return userRepository.getTasks(userId);
    }
}
