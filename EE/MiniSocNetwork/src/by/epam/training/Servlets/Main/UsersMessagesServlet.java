package by.epam.training.Servlets.Main;

import by.epam.training.Model.Beans.User;
import by.epam.training.Model.MySQLDao.DBFactory;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UsersMessagesServlet",
urlPatterns = "/usersMessages")
public class UsersMessagesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        long userId = (long)session.getAttribute("userId");

        try {
            List<User> users = DBFactory.getFactory().getUserDao().getFriends(userId);
            Gson gson = new Gson();
            String usersJson = gson.toJson(users);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(usersJson);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
