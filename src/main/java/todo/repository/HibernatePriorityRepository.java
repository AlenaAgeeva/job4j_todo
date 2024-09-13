package todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import todo.model.Priority;

import java.util.Collection;
import java.util.Collections;

@Repository
@AllArgsConstructor
public class HibernatePriorityRepository implements PriorityRepository {
    private final CrudRepository crudRepository;

    @Override
    public Collection<Priority> findAll() {
        try {
            return crudRepository.query("from Priority",
                    Priority.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
