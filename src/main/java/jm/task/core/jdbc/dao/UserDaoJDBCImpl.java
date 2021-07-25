package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Util util;

    public UserDaoJDBCImpl() {
        this.util = new Util();
    }

    public void createUsersTable() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = util.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS mytable (Id BIGINT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(20), lastName VARCHAR(20), age TINYINT)");
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
                System.out.println("произошел ролбек");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public void dropUsersTable() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = util.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS mytable");
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
                System.out.println("произошел ролбек");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public void saveUser(String name, String lastName, byte age) {
        Connection connection = null;
        PreparedStatement prStatement = null;
        try {
            connection = util.getConnection();
            prStatement = connection.prepareStatement("INSERT INTO mytable (Name , lastName, age) VALUES (?,?,?)");
            prStatement.setString(1, name);
            prStatement.setString(2, lastName);
            prStatement.setByte(3, age);
            prStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
                System.out.println("произошел ролбек");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                prStatement.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public void removeUserById(long id) {
        Connection connection = null;
        PreparedStatement prStatement = null;
        try {
            connection = util.getConnection();
            prStatement = connection.prepareStatement("delete from mytable where id = ?");
            prStatement.setLong(1, id);
            prStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
                System.out.println("произошел ролбек");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                prStatement.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = util.getConnection();
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select id, name, lastName, age from mytable");
                while (result.next()) {
                    User user = new User(result.getLong("id"), result.getString("name"), result.getString("lastName"), result.getByte("age"));
                    userList.add(user);
                }
                System.out.println(userList);
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
                System.out.println("произошел ролбек");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return userList;
    }
    public void cleanUsersTable() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = util.getConnection();
            statement = connection.createStatement();
            statement.execute("TRUNCATE TABLE mytable");
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
                System.out.println("произошел ролбек");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
