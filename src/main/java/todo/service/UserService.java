package todo.service;

import todo.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {
    Optional<User> save(User user);

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findById(int id);

    Collection<User> findAll();

    boolean deleteById(int id);
}
