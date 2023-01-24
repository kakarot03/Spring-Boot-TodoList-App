/**
 * @author debpaul
 *
 */

package com.todo.app.step;

import com.todo.app.entity.Task;
import com.todo.app.repository.TaskRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
public class TaskWriter implements ItemWriter<Task> {

    @Autowired
    private TaskRepository taskRepository;
    private static final Logger logger = LogManager.getLogger(TaskWriter.class);

    // Saves the Tasks generated from the CSV file to DB
    @Override
    public void write(List<? extends Task> tasks) throws Exception {

        logger.info("Data Saved for Tasks : " + tasks);
        taskRepository.saveAll(tasks);
    }
}
