package todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import todo.model.Task;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HibernateTaskRepositoryTest {
    private static Session session;
    private static SessionFactory sf;
    private static TaskRepository taskRepository;

    @BeforeAll
    public static void setup() {
        Configuration configuration = new Configuration();
        /*configuration.configure("hibernate.cfg.xml");*/
        configuration.configure("hibernate_test.cfg.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        sf = configuration.buildSessionFactory(serviceRegistry);
        sf = configuration.buildSessionFactory();
        session = sf.openSession();
        taskRepository = new HibernateTaskRepository(sf);
    }

    @AfterEach
    public void tearDown() {
        session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE Task").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    void whenSaveThenShouldGetTask() {
        var task = new Task(0, "title1", "text1",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false);
        task.setTitle("Test");
        task.setDescription("Test");
        task.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        var savedTask = Optional.of(taskRepository.save(task));
        var foundTasks1 = taskRepository.findAll();
        assertThat(savedTask).isPresent();
        assertThat(foundTasks1).isNotEmpty();
        assertThat(foundTasks1).first().hasFieldOrPropertyWithValue("title", task.getTitle());
        assertThat(foundTasks1).first().hasFieldOrPropertyWithValue("description", task.getDescription());
        assertThat(savedTask.get().getId()).isNotNull();
    }

    @Test
    void whenDeleteByIdThenShouldNotFindTask() {
        var task = new Task(0, "Test Title", "Test Description",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false);
        var savedTask = taskRepository.save(task);
        var id = savedTask.getId();
        taskRepository.deleteById(id);
        var foundTask = taskRepository.findById(id);
        assertThat(foundTask).isEmpty();
    }

    @Test
    void whenUpdateThenTaskShouldReflectChanges() {
        var task = new Task(0, "Original Title", "Original Description",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false);
        var savedTask = taskRepository.save(task);
        savedTask.setTitle("Updated Title");
        taskRepository.update(savedTask);
        var updatedTask = taskRepository.findById(savedTask.getId());
        assertThat(updatedTask).isPresent();
        assertThat(updatedTask.get().getTitle()).isEqualTo("Updated Title");
    }

    @Test
    void whenMarkDoneThenTaskShouldBeMarkedAsDone() {
        var task = new Task(0, "Test Title", "Test Description",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false);
        var savedTask = taskRepository.save(task);
        taskRepository.markDone(savedTask);
        var foundTask = taskRepository.findById(savedTask.getId());
        assertThat(foundTask).isPresent();
        assertThat(foundTask.get().isDone()).isTrue();
    }

    @Test
    void whenFindByStatusThenShouldReturnCorrectTasks() {
        var task1 = new Task(0, "Task 1", "Description 1",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), true);
        var task2 = new Task(0, "Task 2", "Description 2",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false);
        taskRepository.save(task1);
        taskRepository.save(task2);
        var doneTasks = taskRepository.findByStatus(true);
        var notDoneTasks = taskRepository.findByStatus(false);
        assertThat(doneTasks).hasSize(1);
        assertThat(doneTasks.iterator().next().getTitle()).isEqualTo("Task 1");
        assertThat(notDoneTasks).hasSize(1);
        assertThat(notDoneTasks.iterator().next().getTitle()).isEqualTo("Task 2");
    }
}