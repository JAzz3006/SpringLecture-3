package com.example.simple_web;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    private final List<Task> tasks = new ArrayList<>();

    @GetMapping("/")
    //класс Model является частью Spring MVC
    //с его помощью передаются данные из коонтроллера в представление
    //инициализировать класс не нужно, он передается как св-во в методах index средствами спринга
    //нам нужно его только использовать
    public String index(Model model){
        model.addAttribute("tasks", tasks); //этот метод помещает наш список задач в модель по ключу "tasks"
        return "index";//'nj это имя представления - то, что увидим на экране. Веб-страница то есть.
        // По дефолту спринг будет искать страницы по адресу resources/templates
        //создадим по этому пути файл index.html
    }

    @GetMapping("/task/create")
    //этот метод нужен для отображения формы, с помощью которой мы будем создават нашу задачу
    public String showCreateForm(Model model){
        model.addAttribute("task", new Task());
        return "create";
    }

    @PostMapping("/task/create")
    //а это обработка пост-запроса.
    public String createTask(@ModelAttribute Task task){
        task.setId(System.currentTimeMillis());
        tasks.add(task);

        return "redirect:/";
    }

    @GetMapping("/task/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        Task task = findTaskById(id);
        if (task != null){
            model.addAttribute("task", task);
            return "edit";
        }
        return "redirect:/";
    }

    private Task findTaskById(Long id){
        return tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);

    }
}
