package com.todo.app.service;

import com.todo.app.entity.Task;
import com.todo.app.exception.TaskNotFoundException;
import com.todo.app.exception.UserNotFoundException;
import com.todo.app.repository.TaskRepository;
import com.todo.app.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
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
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    Job job;

    private static final Logger logger = LogManager.getLogger(TaskService.class);

    // Add a new Task with provided details
    public Task addTask(Task task) {
        if(!checkUser(task.getUserId())) {
            throw new UserNotFoundException("No User exists with id " + task.getUserId());
        }
        Task t = todoRepository.save(task);
        return t;
    }

    // Delete a task with given Id
    public ResponseEntity<Map<String, Boolean>> deleteTask(int id) {
        Task todo = checkTask(id);
        todoRepository.delete(todo);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    // Update values of existing Task
    public Task updateTask(int id, Task taskDetails) {
        Task task = checkTask(id);

        task.setTask(taskDetails.getTask());
        task.setCompleted(taskDetails.isCompleted());

        todoRepository.save(task);
        return task;
    }

    // Delete every tasks
    public void deleteAll() {
        todoRepository.deleteAll();
    }

    // Get a list of every tasks
    public List<Task> getAllTasks() {
        return todoRepository.findAll();
    }

    // Check if an User exists with the given Id
    private boolean checkUser(int userId) {
        return !userRepository.findById(userId).isEmpty();
    }

    // Check if a Task exists with the given Id
    private Task checkTask(int taskId) {
        return todoRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("No Task exists with id :" + taskId));
    }

    public void load() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        JobParameters jobParams = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(job, jobParams);
        logger.info("Batch is Running...");

        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

        logger.info("JobExecution Status = " + jobExecution.getStatus());
    }
}
