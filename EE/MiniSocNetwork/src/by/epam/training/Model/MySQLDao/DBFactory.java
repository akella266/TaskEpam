package by.epam.training.Model.MySQLDao;

import by.epam.training.Model.Interfaces.FilesDao;
import by.epam.training.Model.Interfaces.GiftDAO;
import by.epam.training.Model.Interfaces.MessageDAO;
import by.epam.training.Model.Interfaces.UserDAO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBFactory {

    public static Connection getConnection(){
        try{
            InitialContext initContext= new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/minisocnetwork");
            Connection con = ds.getConnection();
            return con;
        }
        catch (SQLException | NamingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(Connection conn, String info){
        try{
            if (conn != null) {
                conn.close();
            }
        }
        catch (SQLException e){
            System.out.println("Error when closing Connection " + info);
        }
    }
    public static void closeStatement(Statement s, String info){
        try {
            if (s != null) {
                s.close();
            }
        }
        catch(SQLException e){
            System.out.println("Error when closing PreparedStatment " + info);
        }
    }

    public static DBFactory getFactory(){
        return new DBFactory();
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

    public FilesDao getFilesDao() {return new FilesMySqlDao();}
}
