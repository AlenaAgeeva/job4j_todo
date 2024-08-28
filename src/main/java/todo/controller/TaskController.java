package todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import todo.model.Task;
import todo.service.SimpleTaskService;
import todo.service.TaskService;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;

    public TaskController(SimpleTaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/done")
    public String getAllDone(Model model) {
        model.addAttribute("tasks", taskService.findByStatus(true));
        return "tasks/list";
    }

    @GetMapping("/new")
    public String getAllNew(Model model) {
        model.addAttribute("tasks", taskService.findByStatus(false));
        return "tasks/list";
    }

    @GetMapping("/create")
    public String getCreationPage() {
        return "tasks/create";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute("Task") Task task) {
        taskService.save(task);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Task with id=" + id + " is not found");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/one";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, Model model) {
        var isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Task with id=" + task.getId() + " is not found");
            return "errors/404";
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        var isDeleted = taskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Task with id=" + id + " is not found");
            return "errors/404";
        }
        return "redirect:/";
    }

    @PostMapping("/complete")
    public String markFinished(@ModelAttribute Task task, Model model) {
        var isDone = taskService.markDone(task);
        if (!isDone) {
            model.addAttribute("message", "Task with id=" + task.getId() + " is not found");
            return "errors/404";
        }
        return "redirect:/";
    }
}
