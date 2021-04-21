package com.myfirstservlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

@WebServlet(
        description = "Login Servlet Testing",
        urlPatterns = {"/LoginServlet"},
        initParams = {
                @WebInitParam(name="user", value="^[A-Z]{1}[a-zA-Z]{2,}$"),
                @WebInitParam(name="password", value="^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=[^$@!#%*?&]*[$#@!%*?&][^$@!#%*?&]*$).{8,}")
        }
)
public class LoginServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                //get request parameter for userId and password
                String user = req.getParameter("user");
                String pwd = req.getParameter("pwd");
                //get servlet config init params
                String userID = getServletConfig().getInitParameter("user");
                String password = getServletConfig().getInitParameter("password");

                if(Pattern.compile(userID).matcher(user).matches() && Pattern.compile(password).matcher(pwd).matches()){
                        req.setAttribute("user", user);
                        req.getRequestDispatcher("LoginSuccess.jsp").forward(req, resp);
                }
                else{
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
                        PrintWriter out = resp.getWriter();
                        out.println("<font color=red>Either user name or password is wrong.</font>");
                        rd.include(req, resp);
                }
        }
}
