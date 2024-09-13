package todo.service;

import todo.model.Priority;

import java.util.Collection;

public interface PriorityService {
    Collection<Priority> findAll();
}
