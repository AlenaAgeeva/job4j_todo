package todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import todo.service.SimpleTaskService;

@Controller
@RequestMapping("/")
public class IndexController {
    private SimpleTaskService taskService;

    public IndexController(SimpleTaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "index";
    }
}
