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

@WebServlet(name = "MyPageServlet",
    urlPatterns = "/mypage")
public class MyPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        long userId = (long)session.getAttribute("userId");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            User user = DBFactory.getFactory().getUserDao().getUser(userId);
            Gson gson = new Gson();
            String userJson = gson.toJson(user);
            resp.getWriter().write(userJson);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
