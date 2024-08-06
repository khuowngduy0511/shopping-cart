/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package fu.edu.shopping.servlet;

import fu.edu.shopping.connection.DbCon;
import fu.edu.shopping.dao.UserDao;
import fu.edu.shopping.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Khuong Duy
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/user-login"})
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                    String email = request.getParameter("login-email");
                    String password = request.getParameter("login-password");

                    UserDao udao = new UserDao(DbCon.getConnection());
                    User user = udao.userLogin(email, password);
                    if (user != null) {
                            request.getSession().setAttribute("auth", user);
//				System.out.print("user logged in");
                            response.sendRedirect("index.jsp");
                    } else {
                            out.println("there is no user");
                    }

            } catch (ClassNotFoundException|SQLException e) {
                    e.printStackTrace();
            } 

    }
}
