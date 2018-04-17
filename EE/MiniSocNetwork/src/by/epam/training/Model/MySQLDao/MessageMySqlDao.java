package by.epam.training.Model.MySQLDao;

import by.epam.training.Model.Beans.File;
import by.epam.training.Model.Beans.Message;
import by.epam.training.Model.Beans.User;
import by.epam.training.Model.Interfaces.MessageDAO;

import java.sql.*;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class MessageMySqlDao implements MessageDAO {

    private final String GET_DIALOG = "select * from messages inner join users on messages.senderId = users.idUser " +
//            "inner join files on messages.idmessages = files.idMessage " +
            "where (senderId = ? and receiverId = ?) or (senderId = ? and receiverId = ?)";
    private final String SEND_MESSAGE = "insert into messages (senderId, receiverId, textMessage, time, type) values (?, ?, ?, ?, ?)";
    private final String DELETE_MESSAGE = "delete from messages where idmessages = ?";
    private final String DELETE_FILE = "delete from files where idMessage = ?";
    private final String GET_MESSAGE = "select * from messages where idmessages = ?";

    @Override
    public List<Message> getMessages(long userId, long friendId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBFactory.getConnection();
            ps = conn.prepareStatement(GET_DIALOG);
            ps.setLong(1, userId);
            ps.setLong(2, friendId);
            ps.setLong(3, friendId);
            ps.setLong(4, userId);
            ResultSet rs = ps.executeQuery();
            List<Message> messages= new LinkedList<>();
            while(rs.next()){
                User user = new User(
                        rs.getLong("idUser"),
                        rs.getString("firstName"),
                        rs.getString("secondName"),
                        null,
                        rs.getInt("age"),
                        rs.getBoolean("sex")
                );
                Message mes = new Message(rs.getLong("idmessages"),
                        rs.getLong("senderId"),
                        rs.getLong("receiverId"),
                        rs.getDate("time"),
                        rs.getString("textMessage"),
                        rs.getString("type")
                );
                mes.setSender(user);
                if (rs.getString("type").equals("file")){
                    File file = new File(DBFactory.getFactory().getFilesDao().getFilePath(mes.getId()));
                    mes.setFile(file);
                }
                messages.add(mes);
            }
            rs.close();
            return messages;
        }
        catch (SQLException e){
            System.out.println("Error! Execution failed (getMessages(userId,friendId))");
            System.out.println(e.getMessage());
        }
        finally {
            DBFactory.closeStatement(ps, "getMessages(userId,friendId)");
            DBFactory.closeConnection(conn, "getMessages(userId,friendId)");
        }
        return null;
    }

    @Override
    public long sendMessage(long userId, long friendId, String message, String type) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBFactory.getConnection();
            ps = conn.prepareStatement(SEND_MESSAGE);
            ps.setLong(1, userId);
            ps.setLong(2, friendId);
            ps.setString(3, message);
            ps.setDate(4, new Date(Calendar.getInstance().getTimeInMillis()));
            ps.setString(5, type);
            ps.execute();
            ResultSet rs = ps.executeQuery("SELECT LAST_INSERT_ID() as last");
            rs.next();
            long id = rs.getLong("last");
            return id;
        }
        catch (SQLException e){
            System.out.println("Error! Execution failed (sendMessage(userId,friendId,message))");
            System.out.println(e.getMessage());
        }
        finally {
            DBFactory.closeStatement(ps, "sendMessage(userId,friendId,message)");
            DBFactory.closeConnection(conn, "sendMessage(userId,friendId,message)");
        }
        return -1;
    }

    @Override
    public boolean deleteMessage(long messageId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBFactory.getConnection();
            ps = conn.prepareStatement(DELETE_MESSAGE);
            ps.setLong(1, messageId);
            ps.execute();
            ps = conn.prepareStatement(DELETE_FILE);
            ps.setLong(1, messageId);
            ps.execute();
            return true;
        }
        catch (SQLException e){
            System.out.println("Error! Execution failed (deleteMessage(messageId))");
            System.out.println(e.getMessage());
        }
        finally {
            DBFactory.closeStatement(ps, "deleteMessage(messageId)");
            DBFactory.closeConnection(conn, "deleteMessage(messageId)");
        }
        return false;
    }

    @Override
    public Message getMessage(long id) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBFactory.getConnection();
            ps = conn.prepareStatement(GET_MESSAGE);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Message mes = new Message(rs.getLong("idmessages"),
                        rs.getLong("senderId"),
                        rs.getLong("receiverId"),
                        rs.getDate("time"),
                        rs.getString("textMessage"),
                        rs.getString("type")
                );
            rs.close();
            return mes;
        }
        catch (SQLException e){
            System.out.println("Error! Execution failed (getMessages(userId,friendId))");
            System.out.println(e.getMessage());
        }
        finally {
            DBFactory.closeStatement(ps, "getMessages(userId,friendId)");
            DBFactory.closeConnection(conn, "getMessages(userId,friendId)");
        }
        return null;
    }
}
