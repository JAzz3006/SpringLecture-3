package com.example.simple_web.listener;
import com.example.simple_web.Task;
import com.example.simple_web.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataBaseTaskCreator {

    private final TaskService taskService;

    @EventListener(ApplicationReadyEvent.class)
    public void CreateTaskData(){
        log.debug("Calling DataBaseTaskCreator -> CreateTaskData()");

        List<Task> tasks = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            int value = i + 1;
            Task task = new Task();
            //task.setId((long) value);
            task.setTitle("Title " + value);
            task.setDescription("Description " + value);
            task.setPriority(value);

            tasks.add(task);
        }
        taskService.batchInsert(tasks);

    }
}
