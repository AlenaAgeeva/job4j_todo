package todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import todo.model.Task;
import todo.service.SimpleTaskService;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private SimpleTaskService taskService;

    public TaskController(SimpleTaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/done")
    public String getAllDone(Model model) {
        model.addAttribute("tasks", taskService.findByStatus(true));
        return "tasks/done";
    }

    @GetMapping("/new")
    public String getAllNew(Model model) {
        model.addAttribute("tasks", taskService.findByStatus(false));
        return "tasks/new";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute("Task") Task task, Model model) {
        try {
            taskService.save(task);
            model.addAttribute("tasks", taskService.findAll());
            return "redirect:/";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }
}
