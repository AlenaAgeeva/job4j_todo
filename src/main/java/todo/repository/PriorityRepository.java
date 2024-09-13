package todo.repository;

import todo.model.Priority;

import java.util.Collection;

public interface PriorityRepository {
    Collection<Priority> findAll();
}
