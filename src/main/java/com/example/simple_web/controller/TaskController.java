package com.example.simple_web.controller;
import com.example.simple_web.Task;
import com.example.simple_web.service.TaskService;
import com.example.simple_web.service.TaskServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Profile("v1")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("tasks", taskService.findAll());
        return "index";
    }

    @GetMapping("task/create")
    public String showCreateForm(Model model){
        model.addAttribute("task", new Task());
        return "create";
    }

    @PostMapping("task/create")
    public String createTask(@ModelAttribute Task task){
        taskService.save(task);
        return "redirect:/";
    }

    @GetMapping("task/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        Task task = taskService.findById(id);
        if (task != null){
            model.addAttribute("task", task);
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("task/edit")
    public String editTask(@ModelAttribute Task task){
        taskService.update(task);
        return "redirect:/";
    }

    @GetMapping("task/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        taskService.deleteById(id);
        return "redirect:/";
    }
}