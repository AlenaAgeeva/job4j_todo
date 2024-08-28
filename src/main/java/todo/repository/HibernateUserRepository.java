package todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import todo.model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {
    private final SessionFactory sf;

    @Override
    public Optional<User> save(User user) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<User> query = session.createQuery("from User where email = :fEmail and password = :fPass", User.class)
                    .setParameter("fEmail", email)
                    .setParameter("fPass", password);
            transaction.commit();
            return query.uniqueResultOptional();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(int id) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<User> query = session.createQuery("from User where id = :fId", User.class)
                    .setParameter("fId", id);
            transaction.commit();
            return query.uniqueResultOptional();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<User> findAll() {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            List<User> result = session.createQuery("from User", User.class)
                    .getResultList();
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Collections.emptyList();
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        boolean result = false;
        try {
            result = session.createQuery("delete User where id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate() > 0;
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }
}
