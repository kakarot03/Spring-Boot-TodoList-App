package com.todo.app.step;

import com.todo.app.entity.Task;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TaskProcessor implements ItemProcessor<Task, Task> {

    public TaskProcessor() {
    }

    // Add TimeStamp to the Task Object
    @Override
    public Task process(Task task) throws Exception {

        task.setCreationTS(new Date());
        return task;
    }
}
