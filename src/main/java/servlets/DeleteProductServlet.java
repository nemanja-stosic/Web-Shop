package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class DeleteProductServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (request.getParameter("deleteProductID").isEmpty()) {
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
                out.println("alert(\"Please enter an ID to delete the product!\")");
                out.println("document.getElementById(\"okButton\").onclick = function () {");
                out.println("location.href = \"products.jsp\";");
                out.println("};");
                out.println("</script>");
                out.println("</body>");
                out.println("</html>");
            } else {
                int productId = Integer.parseInt(request.getParameter("deleteProductID"));

                Session session = HibernateUtil.createSessionFactory().openSession();
                Transaction transaction = null;

                try {
                    transaction = session.beginTransaction();

                    Product retrievedProduct = session.get(model.Product.class, productId);
                    if (retrievedProduct != null) {
                        session.delete(retrievedProduct);
                        transaction.commit();

                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>UpdateDataServlet</title>");
                        out.println("<link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\">");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<div>");
                        out.println("<button id=\"continueButton\" style=\"height:100px;width:200px;\">Continue</button>");
                        out.println("</div>");
                        out.println("<script src=\"javascript/ProcessDeleteProduct.js\"></script>");
                        out.println("</body>");
                        out.println("</html>");
                    } else {
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
                        out.println("alert(\"ID does not exist!\")");
                        out.println("document.getElementById(\"okButton\").onclick = function () {");
                        out.println("location.href = \"products.jsp\";");
                        out.println("};");
                        out.println("</script>");
                        out.println("</body>");
                        out.println("</html>");
                        transaction.rollback();
                    }

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
    }

    @Override
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
