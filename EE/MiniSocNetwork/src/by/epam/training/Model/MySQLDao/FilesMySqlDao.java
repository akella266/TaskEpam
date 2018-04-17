package by.epam.training.Model.MySQLDao;

import by.epam.training.Model.Interfaces.FilesDao;

import java.sql.*;

public class FilesMySqlDao implements FilesDao {

    private final String STORE_FILE = "insert into files (idMessage, path) values (?, ?)";
    private final String GET_FILEPATH = "select path from files where idMessage = ?";

    @Override
    public void storeFile(long idMessage, String fullPath) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBFactory.getConnection();
            ps = conn.prepareStatement(STORE_FILE);
            ps.setLong(1, idMessage);
            ps.setString(2, fullPath);
            ps.execute();
        }
        catch (SQLException e){
            System.out.println("Error! Execution failed (storeFile(idMessage,fullPath))");
            System.out.println(e.getMessage());
        }
        finally {
            DBFactory.closeStatement(ps, "storeFile(idMessage,fullPath)");
            DBFactory.closeConnection(conn, "storeFile(idMessage,fullPath)");
        }
    }

    @Override
    public String getFilePath(long idMessage) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBFactory.getConnection();
            ps = conn.prepareStatement(GET_FILEPATH);
            ps.setLong(1, idMessage);
            ResultSet rs = ps.executeQuery();
            String path = "";
            if(rs.next()){
                path = rs.getString("path");
            }
            return path;
        }
        catch (SQLException e){
            System.out.println("Error! Execution failed (getFilePath(idMessage))");
            System.out.println(e.getMessage());
        }
        finally {
            DBFactory.closeStatement(ps, "getFilePath(idMessage)");
            DBFactory.closeConnection(conn, "getFilePath(idMessage)");
        }
        return null;
    }
}
