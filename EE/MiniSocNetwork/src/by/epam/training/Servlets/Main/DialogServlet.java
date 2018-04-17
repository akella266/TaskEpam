package by.epam.training.Servlets.Main;

import by.epam.training.Model.Beans.Message;
import by.epam.training.Model.MySQLDao.DBFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet(name = "DialogServlet",
urlPatterns = "/dialog")
public class DialogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        long userId = (long)session.getAttribute("userId");
        long friendId = (long)session.getAttribute("friendId");

        try {
            List<Message> messages = DBFactory.getFactory().getMessageDao().getMessages(userId, friendId);
            Gson gson = new GsonBuilder().setDateFormat("h:mm").create();
            String mesJson = gson.toJson(messages);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(mesJson);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long friendId = Long.parseLong(req.getParameter("friendId"));

        HttpSession session = req.getSession();
        session.setAttribute("friendId", friendId);
    }
}
