package by.epam.training.Model.Interfaces;

import by.epam.training.Model.Beans.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

    User getUser(String login) throws SQLException, IllegalArgumentException;
    User getUser(long userId) throws SQLException, IllegalArgumentException;
    boolean availableLogin(String login) throws SQLException, IllegalArgumentException;
    long addUser(User user) throws SQLException;
    List<User> getFriends(long userId) throws SQLException;
    List<User> getAllPeople(long userId) throws SQLException;
    void addFriend(long userid, long newFriendid) throws SQLException;
    void removeFriend(long userid, long newFriendid) throws SQLException;
    void updateAvatar(long userId, String path) throws SQLException;
}
