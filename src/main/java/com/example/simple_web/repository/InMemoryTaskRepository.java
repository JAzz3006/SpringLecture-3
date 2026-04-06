package com.example.simple_web.repository;
import com.example.simple_web.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class InMemoryTaskRepository implements TaskRepository{

    private final List<Task> tasks = new ArrayList<>();
    @Override
    public List<Task> findAll() {
        log.debug("Call findAll() from InMemoryTaskRepository");
        return tasks;
    }

    @Override
    public Optional<Task> findById(Long id) {
        log.debug("Call findById() from InMemoryTaskRepository");
        return tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    @Override
    public Task save(Task task) {
        log.debug("Call save() from InMemoryTaskRepository. Task is {}", task);
        task.setId(System.currentTimeMillis());
        tasks.add(task);
        return task;
    }

    @Override
    public Task update(Task task) {
        log.debug("Call update() from InMemoryTaskRepository. Task is {}", task);
        Task existTask = findById(task.getId()).orElse(null);
        if (existTask != null){
            existTask.setPriority(task.getPriority());
            existTask.setDescription(task.getDescription());
            existTask.setTitle(task.getTitle());
        }
        return task;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Call delete() from InMemoryTaskRepository? Id is {}", id);
        findById(id).ifPresent(tasks::remove);
    }
}