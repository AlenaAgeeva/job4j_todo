package todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import todo.service.TaskService;

@Controller
@RequestMapping("/")
public class IndexController {
    private final TaskService taskService;

    public IndexController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "index";
    }
}
