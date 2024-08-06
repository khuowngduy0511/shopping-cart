/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package fu.edu.shopping.servlet;

import fu.edu.shopping.connection.DbCon;
import fu.edu.shopping.dao.ProductDao;
import fu.edu.shopping.model.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Khuong Duy
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String query = request.getParameter("query");
    System.out.println("Search query: " + query);

    try {
        ProductDao productDao = new ProductDao(DbCon.getConnection());
        List<Product> searchResults = productDao.searchProducts(query);
        System.out.println("Search results size: " + searchResults.size());
        
        request.setAttribute("searchResults", searchResults);
        request.setAttribute("query", query);
        
        request.getRequestDispatcher("index.jsp").forward(request, response);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}

