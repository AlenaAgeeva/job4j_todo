package todo.service;

import todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {
    Task save(Task task);

    boolean deleteById(int id);

    boolean update(Task task);

    boolean markDone(Task task);

    Optional<Task> findById(int id);

    Collection<Task> findAll();

    Collection<Task> findByStatus(boolean status);
}
