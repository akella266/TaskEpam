package by.epam.training.Servlets.Auth;

import by.epam.training.Model.Beans.User;
import by.epam.training.Model.MySQLDao.DBFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RegistrationServlet",
            urlPatterns = "/reg")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName"),
                secondName = req.getParameter("secondName"),
                age = req.getParameter("age"),
                sex = req.getParameter("sex"),
                login = req.getParameter("login"),
                password = req.getParameter("password");

        User user = new User(0,
                firstName,
                secondName,
                null,
                Integer.parseInt(age),
                Boolean.parseBoolean(sex),
                login,
                password.hashCode() + "");

        try {
            long userId = DBFactory.getFactory().getUserDao().addUser(user);
            HttpSession session = req.getSession();
            session.setAttribute("avatarTo", userId);
        } catch (SQLException e) {
            System.out.println("Error when register user");
            e.printStackTrace();
        }
        resp.setContentType("json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("{ \"valid\": true}");
    }
}
