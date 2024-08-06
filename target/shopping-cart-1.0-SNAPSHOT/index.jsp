<%@page import="fu.edu.shopping.model.Cart"%>
<%@page import="fu.edu.shopping.model.Product"%>
<%@page import="fu.edu.shopping.dao.ProductDao"%>
<%@page import="fu.edu.shopping.connection.DbCon"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="fu.edu.shopping.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
User auth = (User) request.getSession().getAttribute("auth");
if (auth != null) {
    request.setAttribute("person", auth);
}
ProductDao pd = new ProductDao(DbCon.getConnection());
List<Product> products = pd.getAllProducts();
ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
if (cart_list != null) {
    request.setAttribute("cart_list", cart_list);
}
%>

<!DOCTYPE html>
<html>
<head>
<%@include file="/includes/head.jsp"%>
<title>Shopping Cart</title>
</head>
<body>
    <%@include file="/includes/navbar.jsp"%>

    <div class="container">
        <div class="card-header my-3 d-flex justify-content-between align-items-center">
            <h5 class="mb-0">All Products</h5>
            <div>
                <form class="form-inline my-2 my-lg-0 d-inline" action="search" method="get">
                    <input class="form-control mr-sm-2" type="search" name="query" placeholder="Search for products" aria-label="Search">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
                </form>
                <% if (auth != null) { %>
                <button class="btn btn-outline-secondary my-2 my-sm-0 ml-2" type="button" data-toggle="modal" data-target="#updateModal">Update</button>
                <% } %>
            </div>
        </div>
        <div class="row">
            <%
            List<Product> searchResults = (List<Product>) request.getAttribute("searchResults");
            if (searchResults != null) {
                if (!searchResults.isEmpty()) {
                    for (Product p : searchResults) {
            %>
            <div class="col-md-3 my-3">
                <div class="card w-100">
                    <img class="card-img-top" src="product-image/<%=p.getImage() %>" alt="Card image cap">
                    <div class="card-body">
                        <h5 class="card-title"><%=p.getName() %></h5>
                        <h6 class="price">Price: $<%=p.getPrice() %></h6>
                        <h6 class="category">Category: <%=p.getCategory() %></h6>
                        <div class="mt-3 d-flex justify-content-between">
                            <a class="btn btn-dark" href="add-to-cart?id=<%=p.getId()%>">Add to Cart</a>
                            <a class="btn btn-primary" href="order-now?quantity=1&id=<%=p.getId()%>">Buy Now</a>
                        </div>
                    </div>
                </div>
            </div>
            <%
                    }
                } else {
            %>
            <div class="col-md-12">
                <p>No products found for your search query: <%= request.getAttribute("query") %></p>
            </div>
            <%
                }
            } else {
                if (!products.isEmpty()) {
                    for (Product p : products) {
            %>
            <div class="col-md-3 my-3">
                <div class="card w-100">
                    <img class="card-img-top" src="product-image/<%=p.getImage() %>" alt="Card image cap">
                    <div class="card-body">
                        <h5 class="card-title"><%=p.getName() %></h5>
                        <h6 class="price">Price: $<%=p.getPrice() %></h6>
                        <h6 class="category">Category: <%=p.getCategory() %></h6>
                        <div class="mt-3 d-flex justify-content-between">
                            <a class="btn btn-dark" href="add-to-cart?id=<%=p.getId()%>">Add to Cart</a>
                            <a class="btn btn-primary" href="order-now?quantity=1&id=<%=p.getId()%>">Buy Now</a>
                        </div>
                    </div>
                </div>
            </div>
            <%
                    }
                } else {
            %>
            <div class="col-md-12">
                <p>There are no products available.</p>
            </div>
            <%
                }
            }
            %>
        </div>
    </div>

    <%@include file="/includes/footer.jsp"%>


    <div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="updateModalLabel">Update Products</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    
                    <form action="upload-product" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="productId">Product ID</label>
                            <input type="number" class="form-control" id="productId" name="id" required>
                        </div>
                        <div class="form-group">
                            <label for="productName">Product Name</label>
                            <input type="text" class="form-control" id="productName" name="name" required>
                        </div>
                        <div class="form-group">
                            <label for="productPrice">Product Price</label>
                            <input type="number" class="form-control" id="productPrice" name="price" required>
                        </div>
                        <div class="form-group">
                            <label for="productCategory">Product Category</label>
                            <input type="text" class="form-control" id="productCategory" name="category" required>
                        </div>
                        <div class="form-group">
                            <label for="productImage">Product Image</label>
                            <input type="file" class="form-control" id="productImage" name="image" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Upload</button>
                    </form>

                    <hr>

                    
                    <form action="delete-product" method="post">
                        <div class="form-group">
                            <label for="productId">Product ID</label>
                            <input type="number" class="form-control" id="productId" name="id" required>
                        </div>
                        <button type="submit" class="btn btn-danger">Delete</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
