package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Buyer;
import model.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

public class ProcessSaleServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            if (request.getParameter("buyerId").isEmpty()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Alert</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div>");
                out.println("<button id=\"okButton\" style=\"height:100px;width:200px;\">OK</button>");
                out.println("</div>");
                out.println("<script>");
                out.println("alert(\"Please select product again and enter your ID to buy a product!\")");
                out.println("document.getElementById(\"okButton\").onclick = function () {");
                out.println("location.href = \"index.jsp\";");
                out.println("};");
                out.println("</script>");
                out.println("</body>");
                out.println("</html>");
            } else {
                Buyer buyer = null;
                Product product = null;

                for (String id : request.getParameterValues("id")) {
                    String productName = request.getParameter("productNameValue" + id);
                    String productColor = request.getParameter("productColorValue" + id);
                    double productPrice = Double.parseDouble(request.getParameter("productPriceValue" + id));
                    boolean productAddedToCart = false;

                    if (request.getParameter("cartItem" + id) != null) {
                        productAddedToCart = true;
                    }

                    int buyerId = Integer.parseInt(request.getParameter("buyerId"));

                    //checking what product is checked to be bought
                    if (productAddedToCart) {
                        Session session = HibernateUtil.createSessionFactory().openSession();
                        Transaction transaction = null;

                        try {
                            transaction = session.beginTransaction();

                            buyer = session.get(model.Buyer.class, buyerId);
                            product = session.get(model.Product.class, Integer.parseInt(id));

                            // doing sale
                            int sumBalance = (int) ((buyer.getBalance()) - (product.getPrice()));
                            buyer.setBalance(sumBalance);
                            Set<Product> buyerProducts = new HashSet<>();
                            buyerProducts.add(product);
                            buyer.setProducts(buyerProducts);

                            product.setBuyer(buyer);

                            session.update(buyer);

                            transaction.commit();

                            out.println("<!DOCTYPE html>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>Servlet ProcessSaleServlet</title>");
                            out.println("<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,400;0,700;1,200;1,300;1,900&display=swap\">");
                            out.println("<link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\">");
                            out.println("</head>");
                            out.println("<body>");
                            out.println("<div id=\"orderMessageDiv\">");
                            out.println("<div>");
                            out.println("<h2>Order Info:</h2>");
                            out.println("<p>Product: " + product.getName() + "</p>");
                            out.println("<p>Color: " + product.getColor() + "</p>");
                            out.println("<p>Price: " + product.getPrice() + "</p><br/>");

                        } catch (HibernateException e) {
                            if (transaction != null) {
                                transaction.rollback();
                            }
                            System.out.println(e.getMessage());
                        } finally {
                            HibernateUtil.close();
                        }

                    }

                }

                out.println("<p>Balance remaining: " + buyer.getBalance() + "</p>");
                out.println("<h1>Thank you for your order " + buyer.getName() + "!</h1><br/>");
                out.println("</div>");
                out.println("</div>");
                out.println("<div>");
                out.println("<button id=\"okButton\" style=\"height:100px;width:200px;margin-left: 40%;\">OK</button>");
                out.println("</div>");
                out.println("<script>");
                out.println("document.getElementById(\"okButton\").onclick = function () {");
                out.println("location.href = \"index.jsp\";");
                out.println("};");
                out.println("</script>");
                out.println("</body>");
                out.println("</html>");

            }

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
