
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sales</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,400;0,700;1,200;1,300;1,900&display=swap">
        <link href="css/style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <div id="header">
            Web Shop
        </div>
        <br />
        <div id="navigation">
            <form action="ProductListServlet" method="POST">
                <ul>
                    <li>
                        <input type="submit" onclick="this.form.action = 'index.jsp'" value="GO BACK" style="height:50px; width:200px;margin-right: 10px;" class="submitLink">
                    </li>
                    <li>
                        <input type="submit" value="Product list" style="height:50px; width:200px;" class="submitLink">
                    </li>
                </ul> 
            </form>
        </div>
    </body>
</body>
</html>
