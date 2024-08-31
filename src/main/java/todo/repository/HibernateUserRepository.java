package todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import todo.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<User> save(User user) {
        try {
            crudRepository.run(session -> session.save(user));
            return Optional.of(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return crudRepository.optional("from User where email = :fEmail and password = :fPass",
                User.class,
                Map.of("fEmail", email, "fPass", password));
    }

    @Override
    public Optional<User> findById(int id) {
        return crudRepository.optional("from User where id = :fId",
                User.class,
                Map.of("fId", id));
    }

    @Override
    public Collection<User> findAll() {
        return crudRepository.query("from User", User.class);
    }

    @Override
    public boolean deleteById(int id) {
        try {
            crudRepository.run("delete User where id = :fId",
                    Map.of("fId", id));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
