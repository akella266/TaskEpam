package by.epam.training.Servlets.Auth;

import by.epam.training.Model.MySQLDao.DBFactory;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.SQLException;

@WebFilter(urlPatterns = {"/reg"})
public class RegistrationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain filterChain) throws IOException, ServletException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String firstName = req.getParameter("firstName"),
                secondName = req.getParameter("secondName"),
                age = req.getParameter("age"),
                sex = req.getParameter("sex"),
                avatar = req.getParameter("avatar"),
                login = req.getParameter("login"),
                password = req.getParameter("password");

        if (firstName.length() == 0 || secondName.length() == 0){
            resp.getWriter().write("{ \"valid\": false, \"error\":\"Ошибка в поле имя и/или фамилия\"}");
            return;
        }
        if (firstName.matches("(.*)[_?!.(),/0-9](.*)") || secondName.matches("(.*)[_?!.(),/0-9](.*)")) {
            resp.getWriter().write("{ \"valid\": false, \"error\":\"Ошибка в поле имя и/или фамилия\"}");
            return;
        }

        if (age.length() == 0 ){
            resp.getWriter()
                    .write("{ \"valid\": false, \"error\":\"Поле возраст не должно быть пустым или содержать буквы\"}");
            return;
        }
        else{
            int newAge = Integer.parseInt(age);
            if (newAge < 0 || newAge > 100){
                resp.getWriter().write("{ \"valid\": false, \"error\":\"Некорректно указанный возраст!\"}");
                return;
            }
        }

        if (login.length() < 4 || password.length() < 6) {
            resp.getWriter().write("{ \"valid\": false, \"error\":\"Поле логин и/или пароль короткое\"}");
            return;
        }
        if (login.matches("(.*)[_?!.(),/](.*)") || password.matches("(.*)[_?!.(),/](.*)")) {
            resp.getWriter().write("{ \"valid\": false, \"error\":\"Поле логин и/или пароль содержит недопустимые символы\"}");
            return;
        }
        try {
            if (!DBFactory.getFactory().getUserDao().availableLogin(login)) {
                resp.getWriter().write("{ \"valid\": false, \"error\":\"Данный логин уже занят!\"}");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        filterChain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }
}
