package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class UserDaoJDBCImpl extends Util implements UserDao {

    Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Statement statement = null;
        try  {
            statement = connection.createStatement();
            String newTable = "CREATE TABLE `user_connect`.`users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NULL,\n" +
                    "  `lastName` VARCHAR(45) NULL,\n" +
                    "  `age` INT NULL,\n" +
                    "  PRIMARY KEY (`id`));";
            statement.executeUpdate(newTable);
        } catch (SQLException t) {
            t.printStackTrace();
        }

    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()){
            String drop = "DROP TABLE user_connect.users";
            statement.executeUpdate(drop);
        } catch ( SQLException t) {
            System.out.println("Таблица уже удалена!");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String save ="INSERT INTO user_connect.users (NAME, LASTNAME,AGE) VALUES (?,?,?) ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(save)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);

            preparedStatement.executeUpdate();
            System.out.println("User " + " " + name + " " + "добавлен в базу!");
        } catch (SQLException t) {
            t.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String remove ="DELETE FROM user_connect.users WHERE ID=? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(remove)){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException t) {
            t.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String sql = "SELECT ID,NAME,LASTNAME,AGE FROM user_connect.users";
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                allUsers.add(user);
            }
            resultSet.close();
        } catch (SQLException t) {
            t.printStackTrace();
        }
        System.out.print(allUsers.toString());
        return allUsers;

    }

    public void cleanUsersTable() {
        String delete = "DELETE FROM user_connect.users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(delete)){
                preparedStatement.executeUpdate();
        } catch (SQLException t) {
            t.printStackTrace();
        }

    }
}
