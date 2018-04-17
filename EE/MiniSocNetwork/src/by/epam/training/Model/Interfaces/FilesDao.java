package by.epam.training.Model.Interfaces;

import java.sql.SQLException;

public interface FilesDao {

    void storeFile(long idMessage, String fullPath) throws SQLException;
    String getFilePath(long idMessage) throws SQLException;
}
