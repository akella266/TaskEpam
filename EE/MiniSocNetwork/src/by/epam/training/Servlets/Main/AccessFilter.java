package by.epam.training.Servlets.Main;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/views/dialog.jsp",
        "/views/mypage.jsp",
        "/views/userpage.jsp",
        "/views/user.jsp",
        "/views/usersMessages.jsp"})
public class AccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object o = session.getAttribute("userId");
            if(o == null){
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            else{
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
        else
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    public void destroy() {

    }
}
