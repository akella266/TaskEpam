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

@WebServlet(name = "UserPageServlet",
urlPatterns = "/userpage")
public class UserPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        long userpageId = (long)(session.getAttribute("friendId"));
        boolean isFriend = (boolean)(session.getAttribute("isFriend"));

        try {
            User user = DBFactory.getFactory().getUserDao().getUser(userpageId);
            user.setFriend(isFriend);
            Gson gson = new Gson();
            String userJson = gson.toJson(user);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(userJson);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long userpageId = Long.parseLong(req.getParameter("userpageId"));
        boolean isFriend = Boolean.parseBoolean(req.getParameter("isFriend"));
        HttpSession session = req.getSession();
        session.setAttribute("friendId", userpageId);
        session.setAttribute("isFriend", isFriend);
    }
}
