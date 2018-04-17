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

@WebServlet(name = "UsersServlet",
urlPatterns = "/users")
public class UsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        boolean findPeople = (boolean)session.getAttribute("findPeople");
        try {
            long userId = (long)session.getAttribute("userId");
            List<User> users = null;
            if (findPeople){
                users = DBFactory.getFactory().getUserDao().getAllPeople(userId);
            }
            else{
                users = DBFactory.getFactory().getUserDao().getFriends(userId);
            }
            Gson gson = new Gson();
            String usersJson = gson.toJson(users);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(usersJson);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean findPeople = Boolean.parseBoolean(req.getParameter("findPeople"));

        HttpSession session = req.getSession();
        session.setAttribute("findPeople", findPeople);

    }
}
