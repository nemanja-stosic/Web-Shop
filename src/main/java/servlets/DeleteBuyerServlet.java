package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Buyer;
import model.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class DeleteBuyerServlet extends HttpServlet {

protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (request.getParameter("deleteBuyerID").isEmpty()) {
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
                out.println("alert(\"Please enter an ID to delete the buyer!\")");
                out.println("document.getElementById(\"okButton\").onclick = function () {");
                out.println("location.href = \"buyers.jsp\";");
                out.println("};");
                out.println("</script>");
                out.println("</body>");
                out.println("</html>");
            } else {
                int buyerId = Integer.parseInt(request.getParameter("deleteBuyerID"));

                Session session = HibernateUtil.createSessionFactory().openSession();
                Transaction transaction = null;

                try {
                    transaction = session.beginTransaction();

                    Buyer retrievedBuyer = session.get(model.Buyer.class, buyerId);
                    if (retrievedBuyer != null) {
                        session.delete(retrievedBuyer);
                        transaction.commit();

                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>DeleteDataServlet</title>");
                        out.println("<link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\">");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<div>");
                        out.println("<button id=\"continueButton\" style=\"height:100px;width:200px;\">Continue</button>");
                        out.println("</div>");
                        out.println("<script src=\"javascript/ProcessDeleteBuyer.js\"></script>");
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
                        out.println("location.href = \"buyers.jsp\";");
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
