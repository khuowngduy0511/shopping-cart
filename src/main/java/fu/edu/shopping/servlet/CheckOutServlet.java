/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package fu.edu.shopping.servlet;

import fu.edu.shopping.connection.DbCon;
import fu.edu.shopping.dao.OrderDao;
import fu.edu.shopping.model.Cart;
import fu.edu.shopping.model.Order;
import fu.edu.shopping.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Khuong Duy
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/cart-check-out"})
public class CheckOutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try(PrintWriter out = response.getWriter()){
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
                    User auth = (User) request.getSession().getAttribute("auth");
                    if(cart_list != null && auth!=null) {
                            for(Cart c:cart_list) {
                                    Order order = new Order();
                                    order.setId(c.getId());
                                    order.setUid(auth.getId());
                                    order.setQunatity(c.getQuantity());
                                    order.setDate(formatter.format(date));

                                    OrderDao oDao = new OrderDao(DbCon.getConnection());
                                    boolean result = oDao.insertOrder(order);
                                    if(!result) break;
                            }
                            cart_list.clear();
                            response.sendRedirect("orders.jsp");
                    }else {
                            if(auth==null) {
                                    response.sendRedirect("login.jsp");
                            }
                    }
            } catch (ClassNotFoundException|SQLException e) {

                    e.printStackTrace();
            }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request, response);
    }

}