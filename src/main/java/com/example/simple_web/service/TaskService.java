package com.example.simple_web.service;
import com.example.simple_web.Task;
import java.util.List;

public interface TaskService {
List<Task> findAll();
Task findById(Long id);
Task save(Task task);
Task update(Task task);
void deleteById(Long id);
}
