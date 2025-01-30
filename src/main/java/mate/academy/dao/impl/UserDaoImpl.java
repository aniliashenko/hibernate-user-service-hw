package mate.academy.dao.impl;

import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDaoImpl implements UserDao {
    @Override
    public User add(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new DataProcessingException(
                    "Can not create a new user, an error occurred."
                            + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return (User) session.get(email, User.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataProcessingException(
                    "Can not find user by email, an error occurred."
                            + e.getMessage(), e);
        }
    }
}
