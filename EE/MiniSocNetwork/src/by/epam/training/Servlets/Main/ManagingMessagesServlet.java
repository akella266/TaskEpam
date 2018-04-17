package by.epam.training.Servlets.Main;

import by.epam.training.Model.MySQLDao.DBFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ManagingMessagesServlet",
    urlPatterns = "/managingMessage")
public class ManagingMessagesServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");

        switch (method){
            case "send":{
                String message = req.getParameter("message");
                HttpSession session = req.getSession();

                long userId = (long)session.getAttribute("userId");
                long friendId = (long)session.getAttribute("friendId");

                try {
                    long id = DBFactory.getFactory().getMessageDao().sendMessage(userId, friendId, message, "text");
                    resp.getWriter().write((id != -1) + "");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            break;
            case "del":{
                long messageId = Long.parseLong(req.getParameter("messageId"));

                try {
                    boolean success = DBFactory.getFactory().getMessageDao().deleteMessage(messageId);
                    resp.getWriter().write(success + "");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            break;
            default:{
                resp.getWriter().write("false");
            }
        }






    }
}
