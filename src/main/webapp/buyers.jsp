<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Buyers</title>
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
            <h1>Add New Buyer</h1>
        </div>
        <div id="updateProductDiv">
            <h1>Update Buyer</h1>
        </div>
        <div id="deleteBuyerDiv">
            <h1>Delete Buyer</h1>
        </div>
    </div>
    <div id="wrapper">
        <div id="addProduct">
            <form action="AddBuyerServlet" method="POST">

                <label for="newBuyer">Enter buyer name</label>
                <input type="text" name="newBuyer" id="newBuyer">
                <br />

                <label for="balance">Enter balance</label>
                <input type="text" name="balance" id="balance">
                <br />

                <input type="submit" value="Add" size="200">
            </form>
        </div> <!--end of addProduct-->
        <div id="updateProduct">
            <form action="UpdateBuyerServlet" method="POST">

                <label for="buyerId">Enter buyer ID</label>
                <input type="text" name="buyerId" id="buyerId">
                <br />

                <label for="newBuyer">Enter buyer name</label>
                <input type="text" name="newBuyer" id="newBuyer">
                <br />

                <label for="balance">Enter balance</label>
                <input type="text" name="balance" id="balance">
                <br />

                <input type="submit" value="Update" size="200">
            </form>
            </form>
        </div> <!--end of updateProduct-->
        <div id="deleteProduct">
            <form action="DeleteBuyerServlet" method="POST">

                <label for="deleteBuyerID">Enter buyer ID</label>
                <input type="text" name="deleteBuyerID" class="productID" id="deleteBuyerID">

                <br />

                <input type="submit" value="Delete" size="200">
            </form>
        </div> <!--end of updateProduct-->
    </div>
    <a href="index.jsp">GO BACK</a>
</body>
</html>
