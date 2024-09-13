package todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import todo.model.Task;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {
    private final CrudRepository crudRepository;

    @Override
    public Task save(Task task) {
        try {
            crudRepository.run(session -> session.save(task));
            return task;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        try {
            crudRepository.run("delete Task where id = :fId", Map.of("fId", id));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Task task) {
        try {
            crudRepository.run(session -> session.update(task));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean markDone(Task task) {
        try {
            crudRepository.run("update Task set done = 'true' where id = :fId",
                    Map.of("fId", task.getId()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Task> findById(int id) {
        try {
            return crudRepository.optional(
                    "from Task where id = :fId",
                    Task.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Collection<Task> findAll() {
        try {
            return crudRepository.query("from Task t JOIN FETCH t.priority",
                    Task.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<Task> findByStatus(boolean status) {
        try {
            return crudRepository.query("from Task t JOIN FETCH t.priority where done = :fStatus",
                    Task.class,
                    Map.of("fStatus", status));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
