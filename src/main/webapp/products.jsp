
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,400;0,700;1,200;1,300;1,900&display=swap">
        <link href="css/style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <div id="header">
            Web Shop
        </div>
    </body>
    <div id="headerWrapper">
        <div>
            <h1>Add New Product</h1>
        </div>
        <div id="updateProductDiv">
            <h1>Update Product</h1>
        </div>
        <div id="deleteProductDiv">
            <h1>Delete Product</h1>
        </div>
    </div>
    <div id="wrapper">
        <div id="addProduct">
            <form action="AddProductServlet" method="POST" enctype="multipart/form-data">

                <label for="newProduct">Enter product name</label>
                <input type="text" name="newProduct" id="newProduct">
                <br />

                <label for="productColor">Enter product color</label>
                <input type="text" name="productColor" id="productColor">
                <br />

                <label for="productPrice">Enter product price</label>
                <input type="text" name="productPrice" id="productPrice">
                <br />

                <label for="productImage">Chose product image:</label>
                <input type="file" name="productImage" accept="image/png" id="productImage">
                <br />

                <input type="submit" value="Add" size="200">
            </form>
        </div> <!--end of addProduct-->
        <div id="updateProduct">
            <form action="UpdateProductServlet" method="POST" enctype="multipart/form-data">

                <label for="updateProductID">Enter product ID</label>
                <input type="text" name="updateProductID" class="productID" id="updateProductID">

                <br />

                <label for="updateProductName">Enter product name</label>
                <input type="text" name="updateProductName" id="updateProductName">
                <br />

                <label for="updateProductColor">Enter product color</label>
                <input type="text" name="updateProductColor" id="updateProductColor">
                <br />

                <label for="updateProductPrice">Enter product price</label>
                <input type="text" name="updateProductPrice" id="updateProductPrice">
                <br />

                <label for="updateProductImage">Chose product image:</label>
                <input type="file" name="updateProductImage" accept="image/png" id="updateProductImage">
                <br />
                *Optional
                <br/>

                <input type="submit" value="Update" size="200">
            </form>
        </div> <!--end of updateProduct-->
        <div id="deleteProduct">
            <form action="DeleteProductServlet" method="POST">

                <label for="deleteProductID">Enter product ID</label>
                <input type="text" name="deleteProductID" class="productID" id="deleteProductID">

                <br />

                <input type="submit" value="Delete" size="200">
            </form>
        </div> <!--end of updateProduct-->
    </div>
    <a href="index.jsp">GO BACK</a>
</body>
</html>
