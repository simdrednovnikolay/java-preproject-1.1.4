package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoHibernateImpl extends Util implements UserDao {

    Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = getSessionFactory();
        Transaction transaction = session.beginTransaction();

        Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)").addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory();
        Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = getSessionFactory();
        Transaction transaction = session.beginTransaction();
        session.save(new User(name, lastName, age));
        logger.info("User " + " " + name + " " + "добавлен в базу!");
        transaction.commit();
        session.close();

    }

    @Override
    public void removeUserById(long id) {
        Session session = getSessionFactory();
        Transaction transaction = session.beginTransaction();
        User user = (User)session.load(User.class, id);
        session.delete(user);
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = getSessionFactory();
        Transaction transaction = session.beginTransaction();
        List<User> list= session.createQuery("FROM " +User.class.getSimpleName()).list();
        transaction.commit();
        session.close();
        logger.info(list.toString());
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = getSessionFactory();
        Transaction transaction = session.beginTransaction();
        List<User> list= session.createQuery("FROM " + User.class.getName()).list();
        for (User user : list) {
            session.delete(user);
        }
        transaction.commit();
        session.close();
    }
}
