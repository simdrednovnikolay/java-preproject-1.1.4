package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Property;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class Util {
    static final String URL = "jdbc:mysql://localhost:3306/user_connect";
    static final String USER = "root";
    static final String PASSWORD = "root";

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Соединение установлено!");
        } catch (SQLException t) {
            t.printStackTrace();
            System.out.print("Произошла ошибка");
        }
        return connection;
    }

    static SessionFactory sessionFactory;

    static {
        try {
            Properties property = new Properties();
            property.setProperty("hibernate.connection.url", URL);
            property.setProperty("hibernate.connection.username", USER);
            property.setProperty("hibernate.connection.password", PASSWORD);
            property.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");


            Configuration configuration = new Configuration();
            configuration.setProperties(property);
            configuration.addAnnotatedClass(User.class);

            LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE);
            configuration.setProperties(property);
            sessionFactory = configuration.buildSessionFactory();


        } catch (Exception r) {
            System.out.printf("Какая-то ошибка!");
        }
    }

    public  Session getSessionFactory() {
        Session session = sessionFactory.openSession();
        return  session;
    }


}
