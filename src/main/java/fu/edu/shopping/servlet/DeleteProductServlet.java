package fu.edu.shopping.servlet;

import fu.edu.shopping.connection.DbCon;
import fu.edu.shopping.dao.ProductDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteProductServlet", urlPatterns = {"/delete-product"})
public class DeleteProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            ProductDao productDao = new ProductDao(DbCon.getConnection());
            if (productDao.deleteProduct(id)) {
                req.setAttribute("message", "Product deleted successfully!");
            } else {
                req.setAttribute("message", "Product deletion failed!");
            }
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DeleteProductServlet.class.getName()).log(Level.SEVERE, null, ex);
            req.setAttribute("message", "An error occurred while deleting the product.");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }
}
