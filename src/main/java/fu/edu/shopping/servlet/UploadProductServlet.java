package fu.edu.shopping.servlet;

import fu.edu.shopping.connection.DbCon;
import fu.edu.shopping.dao.ProductDao;
import fu.edu.shopping.model.Product;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
@WebServlet(name = "UploadProductServlet", urlPatterns = {"/upload-product"})
public class UploadProductServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "uploads";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            String category = req.getParameter("category");
            Double price = Double.parseDouble(req.getParameter("price"));
            Part filePart = req.getPart("image");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            filePart.write(uploadPath + File.separator + fileName);
            String imagePath = UPLOAD_DIRECTORY + File.separator + fileName;

            Product product = new Product(id, name, category, price, imagePath);
            ProductDao productDao = new ProductDao(DbCon.getConnection());
            if (productDao.uploadProduct(product)) {
                req.setAttribute("message", "Product uploaded successfully!");
            } else {
                req.setAttribute("message", "Product upload failed!");
            }
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UploadProductServlet.class.getName()).log(Level.SEVERE, null, ex);
            req.setAttribute("message", "An error occurred while uploading the product.");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }
}
