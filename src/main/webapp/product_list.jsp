
<%@page import="java.util.Base64"%>
<%@page import="model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sales_product_list</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,400;0,700;1,200;1,300;1,900&display=swap">
        <link href="css/style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <div id="header">
            Web Shop
        </div>
        <br />
        <div id="navigation">
            <ul>
                <li><a href="index.jsp">GO BACK</a></li>
            </ul>
        </div>

        <div>
            <form action="ProcessSaleServlet" method="POST">
                <div id="buyerInfoDiv" style="background: rgba(119, 119, 119, 0.7); margin-top: 20px;">
                    <h1> Buyer Info</h1>
                    <label for="buyerId">Enter your ID</label>
                    <input type="text" name="buyerId" id="buyerId">

                </div>

                <div id="salesWrapper" style="display: flex;padding: 10px;">

                    <c:forEach begin="0" end="${productListSize}" items="${productsList}" var="product" varStatus="loop">
                        <div class="sales" style="padding: 10px; background: rgba(85, 85, 82, 0.7);margin-top: 10px;margin-left: 10px;">


                            <label>Product: ${product.name}</label>
                            <br />

                            <img src="data:image/png;base64,${product.base64Image}" alt="product-image" style="max-width: 200px;max-height: 200px; display: block;">

                            <br />
                            <label>Color: ${product.color}</label>
                            <br />
                            <label>Price: ${product.price} din.</label>
                            <br />

                            <input hidden name="id" value="${product.id}" />

                            <label for="cartCheckbox">Add to cart?</label>
                            <input type="checkbox" name="cartItem${product.id}" id="cartCheckbox">

                            <!-- hidden inputs to pass data-->
                            <input hidden type="text" name="productNameValue${product.id}" value="${product.name}">
                            <input hidden type="text" name="productColorValue${product.id}" value="${product.color}">
                            <input hidden type="text" name="productPriceValue${product.id}" value="${product.price}">

                            <br />

                        </div><!--end of sales class div-->
                    </c:forEach>
                </div><!--end of salesWrapper-->
                <br />
                <input type="submit" value="Buy" style="height:50px; width:200px;">
            </form>
        </div>

    </body>
</html>
