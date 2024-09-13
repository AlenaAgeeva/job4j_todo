package todo.service;

import org.springframework.stereotype.Service;
import todo.model.Priority;
import todo.repository.PriorityRepository;

import java.util.Collection;

@Service
public class SimplePriorityService implements PriorityService {
    private final PriorityRepository priorityRepository;

    public SimplePriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @Override
    public Collection<Priority> findAll() {
        return priorityRepository.findAll();
    }
}
