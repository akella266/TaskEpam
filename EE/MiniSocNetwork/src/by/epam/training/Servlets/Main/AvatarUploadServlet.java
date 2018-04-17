package by.epam.training.Servlets.Main;

import by.epam.training.Model.MySQLDao.DBFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AvatarUploadServlet",
urlPatterns = "/avatarUpload")
@MultipartConfig
public class AvatarUploadServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "uploadFiles/avatars";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        long userId = (long)session.getAttribute("avatarTo");

        String applicationPath = getServletContext().getRealPath(""),
                uploadPath = applicationPath + UPLOAD_DIR;

        File fileUploadDirectory = new File(uploadPath);
        if (!fileUploadDirectory.exists()) {
            fileUploadDirectory.mkdirs();
        }

        String fileName = "";
        for (Part part : req.getParts()) {
            fileName = extractFileName(part);
            try {
                DBFactory.getFactory().getUserDao().updateAvatar(userId,
                        "http://localhost:8080/" + UPLOAD_DIR + File.separator + fileName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                part.write(uploadPath + File.separator + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
