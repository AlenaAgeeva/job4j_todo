package todo.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import todo.model.Priority;
import todo.model.Task;
import todo.model.User;
import todo.service.SimpleTaskService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IndexControllerTest {
    private SimpleTaskService taskService;

    public IndexControllerTest() {
        this.taskService = mock(SimpleTaskService.class);
    }

    @Disabled
    @Test
    void whenGetAllTasksThenGetIndexAndListOfTasks() {
        var task1 = new Task(1, "title1", "text1",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false, new User(), new Priority());
        var task2 = new Task(2, "title2", "text2",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), true, new User(), new Priority());
        var expectedTasks = List.of(task1, task2);
        var model = new ConcurrentModel();
        when(taskService.findAll()).thenReturn(expectedTasks);
        var indexController = new IndexController(taskService);
        var viewName = indexController.getAll(model);
        var actualTasks = model.getAttribute("tasks");
        assertThat(viewName).isEqualTo("index");
        assertThat(actualTasks).isEqualTo(expectedTasks);
    }
}