package com.example.simple_web.service;
import com.example.simple_web.Task;
import com.example.simple_web.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;

    @Override
    public List<Task> findAll() {
        log.debug("Call findAll() from TaskServiceImpl");
        return taskRepository.findAll();
    }

    @Override
    public Task findById(Long id) {
        log.debug("Call findById() from TaskServiceImpl");
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public Task save(Task task) {
        log.debug("Call save() from TaskServiceImpl");
        return taskRepository.save(task);
    }

    @Override
    public Task update(Task task) {
        log.debug("Call update() from TaskServiceImpl");
        return taskRepository.update(task);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Call deleteById() from TaskServiceImpl");
        taskRepository.deleteById(id);
    }

    @Override
    public void batchInsert(List<Task> tasks) {
        taskRepository.batchInsert(tasks);
    }
}