package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoHibernateImpl.class);

    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                            "name VARCHAR(255), " +
                            "last_name VARCHAR(255), " +
                            "age TINYINT)"
            ).executeUpdate();
            transaction.commit();
            logger.info("Таблица users создана.");
        } catch (Exception e) {
            logger.error("Ошибка при создании таблицы:", e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
            logger.info("Таблица users удалена.");
        } catch (Exception e) {
            logger.error("Ошибка при удалении таблицы:", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, Byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            logger.info("User с именем {} добавлен в базу данных.", name);
        } catch (Exception e) {
            logger.error("Ошибка при добавлении пользователя:", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
            logger.info("User с id {} удален из базы данных.", id);
        } catch (Exception e) {
            logger.error("Ошибка при удалении пользователя:", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            logger.error("Ошибка при получении пользователей:", e);
            return List.of();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
            transaction.commit();
            logger.info("Таблица users очищена.");
        } catch (Exception e) {
            logger.error("Ошибка при очистке таблицы:", e);
        }
    }
}