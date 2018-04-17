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

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login"),
                password=  req.getParameter("password");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            User user = DBFactory.getFactory().getUserDao().getUser(login);
            if (user != null && user.getPass().equals(password.hashCode() + "")){
                HttpSession session = req.getSession();
                session.setAttribute("userId", user.getId());
                req.setAttribute("user", user);
                resp.getWriter().write("{ \"valid\":true}");
//                req.getServletContext().getRequestDispatcher("/mypage").forward(req, resp);
            }
            else{
                resp.getWriter().write("{ \"valid\":false, \"error\":\"Неверный логин или пароль\"}");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            resp.getWriter().write("{ \"valid\":false, \"error\":\"sql error\"}");
        }
        catch (IllegalArgumentException e){
            resp.getWriter().write(e.getMessage());
        }
    }
}
