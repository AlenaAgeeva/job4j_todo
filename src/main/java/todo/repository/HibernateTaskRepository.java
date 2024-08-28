package todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import todo.model.Task;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {
    private final SessionFactory sf;

    @Override
    public Task save(Task task) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(task);
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
        return task;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        boolean result = false;
        try {
            result = session.createQuery("delete Task where id = :fId")
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

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(task);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return false;
    }

    @Override
    public boolean markDone(Task task) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        boolean result = false;
        try {
            result = session.createQuery(
                            "update Task set done = :fDone where id = :fId")
                    .setParameter("fDone", true)
                    .setParameter("fId", task.getId())
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

    @Override
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<Task> query = session.createQuery("from Task where id = :fId", Task.class)
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
    public Collection<Task> findAll() {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            List<Task> result = session.createQuery("from Task", Task.class)
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
    public Collection<Task> findByStatus(boolean status) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            List<Task> result = session.createQuery("from Task where done = :fStatus", Task.class)
                    .setParameter("fStatus", status)
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
}
