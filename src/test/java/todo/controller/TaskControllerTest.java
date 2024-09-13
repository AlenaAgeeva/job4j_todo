package todo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import todo.model.Priority;
import todo.model.Task;
import todo.model.User;
import todo.service.SimpleTaskService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskControllerTest {
    private SimpleTaskService taskService;
    private TaskController taskController;

    @BeforeEach
    public void initService() {
        taskService = mock(SimpleTaskService.class);
        taskController = new TaskController(taskService);
    }

    @Test
    void whenGetAllTasksThenGetIndex() {
        var task1 = new Task(1, "title1", "text1",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false, new User(), new Priority());
        var task2 = new Task(2, "title2", "text2",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), true, new User(), new Priority());
        var model = new ConcurrentModel();
        var expectedTasks = List.of(task1, task2);
        when(taskService.findAll()).thenReturn(expectedTasks);
        var indexController = new IndexController(taskService);
        var viewName = indexController.getAll(model);
        assertThat(viewName).isEqualTo("index");
        assertThat(model.getAttribute("tasks")).usingRecursiveComparison().isEqualTo(expectedTasks);
    }

    @Test
    void whenCreateNewTaskThenGetCreationPage() {
        var viewName = taskController.getCreationPage();
        assertThat(viewName).isEqualTo("tasks/create");
    }

    @Test
    void whenCreateTaskSuccessfully() {
        var task = new Task(1, "title1", "text1",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false, new User(), new Priority());
        when(taskService.save(task)).thenReturn(task);
        var viewName = taskController.createTask(task);
        assertThat(viewName).isEqualTo("redirect:/");
    }

    @Test
    void whenGetByIdThenShouldReturnErrorPageIfNotFound() {
        var model = new ConcurrentModel();
        when(taskService.findById(1)).thenReturn(Optional.empty());
        var viewName = taskController.getById(model, 1);
        assertThat(viewName).isEqualTo("errors/404");
        assertThat(model.getAttribute("message")).isEqualTo("Task with id=1 is not found");
    }

    @Test
    void whenGetAllDoneShouldReturnDoneTasks() {
        when(taskService.findByStatus(true)).thenReturn(Arrays.asList(new Task(), new Task()));
        var model = new ConcurrentModel();
        var viewName = taskController.getAllDone(model);
        assertThat(viewName).isEqualTo("tasks/list");
        assertThat(model.getAttribute("tasks")).isEqualTo(taskService.findByStatus(true));
    }

    @Test
    void whenGetAllNewShouldReturnNewTasks() {
        when(taskService.findByStatus(false)).thenReturn(Arrays.asList(new Task()));
        var model = new ConcurrentModel();
        var viewName = taskController.getAllNew(model);
        assertThat(viewName).isEqualTo("tasks/list");
        assertThat(model.getAttribute("tasks")).isEqualTo(taskService.findByStatus(false));
    }

    @Test
    void whenGetByIdThenShouldReturnTaskIfExists() {
        var task = new Task(1, "title1", "text1",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false, new User(), new Priority());
        var model = new ConcurrentModel();
        when(taskService.findById(1)).thenReturn(Optional.of(task));
        var viewName = taskController.getById(model, 1);
        var actualTask = model.getAttribute("task");
        assertThat(viewName).isEqualTo("tasks/one");
        assertThat(actualTask).usingRecursiveComparison().isEqualTo(task);
    }

    @Test
    void whenUpdateThenShouldRedirectOnSuccessfulUpdate() {
        var task = new Task(1, "title1", "text1",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false, new User(), new Priority());
        var model = new ConcurrentModel();
        when(taskService.update(task)).thenReturn(true);
        var viewName = taskController.update(task, model);
        assertThat(viewName).isEqualTo("redirect:/");
    }

    @Test
    void whenUpdateThenShouldReturnErrorPageIfTaskNotFound() {
        var task = new Task(1, "title1", "text1",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false, new User(), new Priority());
        var model = new ConcurrentModel();
        when(taskService.update(task)).thenReturn(false);
        var viewName = taskController.update(task, model);
        assertThat(viewName).isEqualTo("errors/404");
        assertThat(model.getAttribute("message")).isEqualTo("Task with id=1 is not found");
    }
}