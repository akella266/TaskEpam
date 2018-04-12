package by.epam.training.Model.MySQLDao;

import by.epam.training.Model.Interfaces.GiftDAO;
import by.epam.training.Model.Interfaces.MessageDAO;
import by.epam.training.Model.Interfaces.UserDAO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBFactory {

    public static Connection getConnection(){
        try{
            InitialContext initContext= new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/project_payment");
            Connection con = ds.getConnection();
            return con;
        }
        catch (SQLException | NamingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserDAO getUserDao(){
        return new UserMySqlDao();
    }

    public MessageDAO getMessageDao(){
        return new MessageMySqlDao();
    }

    public GiftDAO getGiftDao(){
        return new GiftMySqlDao();
    }
}
