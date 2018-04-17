package by.epam.training.Model.Interfaces;

import by.epam.training.Model.Beans.Message;

import java.sql.SQLException;
import java.util.List;

public interface MessageDAO {
    List<Message> getMessages(long userId, long friendId) throws SQLException;
    long sendMessage(long userId, long friendId, String message, String type) throws SQLException;
    boolean deleteMessage(long messageId) throws SQLException;
    Message getMessage(long id) throws SQLException;
}
