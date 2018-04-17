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

@WebServlet(name = "ManagingFriendsServlet",
urlPatterns = "/managingFriends")
public class ManagingFriendsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long newFriendId = Long.parseLong(req.getParameter("userId"));
        boolean isFriend = Boolean.parseBoolean(req.getParameter("isFriend"));

        HttpSession session = req.getSession();
        long userId = (long)session.getAttribute("userId");

        try {
            if (isFriend){
                DBFactory.getFactory().getUserDao().removeFriend(userId, newFriendId);
                session.setAttribute("isFriend", false);
            }
            else{
                DBFactory.getFactory().getUserDao().addFriend(userId, newFriendId);
                session.setAttribute("isFriend", true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
