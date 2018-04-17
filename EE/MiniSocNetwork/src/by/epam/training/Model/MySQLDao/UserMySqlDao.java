package by.epam.training.Model.MySQLDao;

import by.epam.training.Model.Beans.User;
import by.epam.training.Model.Interfaces.UserDAO;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserMySqlDao implements UserDAO {

    private final String GET_USER_BY_LOGIN = "select * from users where login = ?";
    private final String GET_USER_BY_ID = "select * from users where idUser = ?";
    private final String CHECK_LOGIN = "select count(*) as count from users where login = ?";
    private final String ADD_USER = "insert into users (firstName, secondName, age, sex, avatar, login, password) " +
            "values (?, ?, ?, ?, ?, ?, ?)";
    private final String GET_FRIENDS = "select * from friends inner join users on friends.friendId = users.idUser " +
            "where friends.userId = ?";
    private final String GET_LONELY_USERS = "select * from users where users.idUser not in (select userId from friends)";
    private final String GET_NOT_FRIENDS_USERS = "select * from users inner join friends on users.idUser = friends.userId where friends.userId != ? and friends.friendId != ?";
    private final String ADD_FRIEND = "insert into friends (userId, friendId) values(?, ?)";
    private final String REMOVE_FRIEND = "delete from friends where userId = ? and friendId = ?";
    private final String UPDATE_AVATAR = "update users set avatar = ? where idUser = ?";

    @Override
    public User getUser(String login) throws SQLException, IllegalArgumentException {
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = DBFactory.getConnection();
            ps = conn.prepareStatement(GET_USER_BY_LOGIN);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            User user = null;
            if (rs.next()){
                user = new User(
                   rs.getLong("idUser"),
                   rs.getString("firstName"),
                   rs.getString("secondName"),
                   rs.getString("avatar"),
                   rs.getInt("age"),
                   rs.getBoolean("sex"),
                   login,
                   rs.getString("password")
                );
                rs.close();
            }
            else{
                throw new IllegalArgumentException("Неверный логин");
            }
            return user;
        }
        catch(SQLException e){
            System.out.println("Error! Execution failed (getUser(login))");
            System.out.println(e.getMessage());
        }
        finally {
            DBFactory.closeStatement(ps, "getUser(login)");
            DBFactory.closeConnection(conn, "getUser(login)");
        }
        return null;
    }

    @Override
    public User getUser(long userId) throws SQLException, IllegalArgumentException{
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = DBFactory.getConnection();
            ps = conn.prepareStatement(GET_USER_BY_ID);
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            User user = null;
            if (rs.next()){
                user = new User(
                        rs.getLong("idUser"),
                        rs.getString("firstName"),
                        rs.getString("secondName"),
                        rs.getString("avatar"),
                        rs.getInt("age"),
                        rs.getBoolean("sex")
                );
                rs.close();
            }
            else{
                throw new IllegalArgumentException("Неверный ID");
            }
            return user;
        }
        catch(SQLException e){
            System.out.println("Error! Execution failed (getUser(userId))");
            System.out.println(e.getMessage());
        }
        finally {
            DBFactory.closeStatement(ps, "getUser(userId)");
            DBFactory.closeConnection(conn, "getUser(userId)");
        }
        return null;
    }

    @Override
    public boolean availableLogin(String login) throws SQLException, IllegalArgumentException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBFactory.getConnection();
            ps = conn.prepareStatement(CHECK_LOGIN);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt("count");
            rs.close();
            return count < 1;
        }
        catch (SQLException e){
            System.out.println("Error! Execution failed (availableLogin(login))");
            System.out.println(e.getMessage());
        }
        finally {
            DBFactory.closeStatement(ps, "availableLogin(login)");
            DBFactory.closeConnection(conn, "availableLogin(login)");
        }
        return false;
    }

    @Override
    public long addUser(User user) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBFactory.getConnection();
            ps = conn.prepareStatement(ADD_USER);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getSecondName());
            ps.setInt(3, user.getAge());
            ps.setBoolean(4, user.isSex());
            ps.setString(5, null);
            ps.setString(6, user.getLogin());
            ps.setString(7, user.getPass());
            ps.execute();
            ResultSet rs = ps.executeQuery("SELECT LAST_INSERT_ID() as last");
            rs.next();
            long userId = rs.getLong("last");
            return userId;
        }
        catch (SQLException e){
            System.out.println("Error! Execution failed (addUser(user))");
            System.out.println(e.getMessage());
        }
        finally {
            DBFactory.closeStatement(ps, "addUser(user)");
            DBFactory.closeConnection(conn, "addUser(user)");
        }
        return -1;
    }

    @Override
    public List<User> getFriends(long userId) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBFactory.getConnection();
            ps = conn.prepareStatement(GET_FRIENDS);
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            List<User> friends = new LinkedList<>();
            while(rs.next()){
                friends.add(new User(
                        rs.getLong("idUser"),
                        rs.getString("firstName"),
                        rs.getString("secondName"),
                        rs.getString("avatar"),
                        rs.getInt("age"),
                        rs.getBoolean("sex"),
                        true));
            }
            rs.close();
            return friends;
        }
        catch (SQLException e){
            System.out.println("Error! Execution failed (getFriends(userId))");
            System.out.println(e.getMessage());
        }
        finally {
            DBFactory.closeStatement(ps, "getFriends(userId)");
            DBFactory.closeConnection(conn, "getFriends(userId)");
        }
        return null;
    }

    @Override
    public List<User> getAllPeople(long userId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBFactory.getConnection();
            ps = conn.prepareStatement(GET_LONELY_USERS);
            ResultSet rs = ps.executeQuery();
            List<User> people = new LinkedList<>();
            while(rs.next()){
                people.add(new User(
                        rs.getLong("idUser"),
                        rs.getString("firstName"),
                        rs.getString("secondName"),
                        rs.getString("avatar"),
                        rs.getInt("age"),
                        rs.getBoolean("sex")));
            }
            rs.close();
            ps = conn.prepareStatement(GET_NOT_FRIENDS_USERS);
            ps.setLong(1, userId);
            ps.setLong(2, userId);
            rs = ps.executeQuery();
            while(rs.next()){
                people.add(new User(
                        rs.getLong("idUser"),
                        rs.getString("firstName"),
                        rs.getString("secondName"),
                        rs.getString("avatar"),
                        rs.getInt("age"),
                        rs.getBoolean("sex")));
            }
            rs.close();
            return people;
        }
        catch (SQLException e){
            System.out.println("Error! Execution failed (getAllPeople)");
            System.out.println(e.getMessage());
        }
        finally {
            DBFactory.closeStatement(ps, "getAllPeople");
            DBFactory.closeConnection(conn, "getAllPeople");
        }
        return null;
    }

    @Override
    public void addFriend(long userid, long newFriendid) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBFactory.getConnection();
            ps = conn.prepareStatement(ADD_FRIEND);
            ps.setLong(1, userid);
            ps.setLong(2, newFriendid);
            ps.execute();
            ps.setLong(1, newFriendid);
            ps.setLong(2, userid);
            ps.execute();
        }
        catch (SQLException e){
            System.out.println("Error! Execution failed (addFriend(user, friend))");
            System.out.println(e.getMessage());
        }
        finally {
            DBFactory.closeStatement(ps, "addFriend(user, friend)");
            DBFactory.closeConnection(conn, "addFriend(user, friend)");
        }
    }

    @Override
    public void removeFriend(long userid, long newFriendid) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBFactory.getConnection();
            ps = conn.prepareStatement(REMOVE_FRIEND);
            ps.setLong(1, userid);
            ps.setLong(2, newFriendid);
            ps.execute();
            ps.setLong(2, userid);
            ps.setLong(1, newFriendid);
            ps.execute();
        }
        catch (SQLException e){
            System.out.println("Error! Execution failed (removeFriend(user, friend))");
            System.out.println(e.getMessage());
        }
        finally {
            DBFactory.closeStatement(ps, "removeFriend(user, friend)");
            DBFactory.closeConnection(conn, "removeFriend(user, friend)");
        }
    }

    @Override
    public void updateAvatar(long userId, String path) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBFactory.getConnection();
            ps = conn.prepareStatement(UPDATE_AVATAR);
            ps.setString(1, path);
            ps.setLong(2, userId);
            ps.execute();
        }
        catch (SQLException e){
            System.out.println("Error! Execution failed (updateAvatar(user,path))");
            System.out.println(e.getMessage());
        }
        finally {
            DBFactory.closeStatement(ps, "updateAvatar(user,path)");
            DBFactory.closeConnection(conn, "updateAvatar(user,path)");
        }
    }
}
