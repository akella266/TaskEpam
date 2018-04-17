package by.epam.training.Servlets.Main;

import by.epam.training.Model.MySQLDao.DBFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "FilesUploadServlet",
urlPatterns = "/fileupload")
@MultipartConfig
public class FilesUploadServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "uploadFiles";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        long userId = (long)session.getAttribute("userId");
        long friendId = (long)session.getAttribute("friendId");

        String applicationPath = getServletContext().getRealPath(""),
                uploadPath = applicationPath + UPLOAD_DIR;

        File fileUploadDirectory = new File(uploadPath);
        if (!fileUploadDirectory.exists()) {
            fileUploadDirectory.mkdirs();
        }

        String fileName = "";
        long id = -1;
        for (Part part : req.getParts()) {
            fileName = extractFileName(part);
            try {
                id = DBFactory.getFactory().getMessageDao().sendMessage(userId, friendId, fileName, "file");
                DBFactory.getFactory().getFilesDao().storeFile(id,"http://localhost:8080/" + UPLOAD_DIR + File.separator + fileName + id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                part.write(uploadPath + File.separator + fileName + id);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        resp.getWriter().write((id != -1) + "");

    }


    private String extractFileName(Part part) {
        String fileName = "",
                contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                fileName = item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return fileName;
    }
}
