package com.todo.app.service;

import com.todo.app.entity.Task;
import com.todo.app.exception.TaskNotFoundException;
import com.todo.app.exception.UserNotFoundException;
import com.todo.app.repository.TaskRepository;
import com.todo.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    @Autowired
    TaskRepository todoRepository;
    @Autowired
    UserRepository userRepository;

    public Task addTask(Task task) {
        if(!checkUser(task.getUserId())) {
            throw new UserNotFoundException("No User exists with id " + task.getUserId());
        }
        Task t = todoRepository.save(task);
        return t;
    }

    public ResponseEntity<Map<String, Boolean>> deleteTask(int id) {
        Task todo = checkTask(id);
        todoRepository.delete(todo);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
    public Task updateTask(int id, Task taskDetails) {
        Task task = checkTask(id);

        task.setTask(taskDetails.getTask());
        task.setCompleted(taskDetails.isCompleted());

        todoRepository.save(task);
        return task;
    }

    public void deleteAll() {
        todoRepository.deleteAll();
    }

    public List<Task> getAllTasks() {
        return todoRepository.findAll();
    }

    private boolean checkUser(int userId) {
        return !userRepository.findById(userId).isEmpty();
    }

    private Task checkTask(int taskId) {
        return todoRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("No Task exists with id :" + taskId));
    }
}
