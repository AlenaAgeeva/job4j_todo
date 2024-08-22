package todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import todo.service.SimpleTaskService;

@Controller
public class TaskController {
    private SimpleTaskService taskService;

    public TaskController(SimpleTaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "index";
    }
}
