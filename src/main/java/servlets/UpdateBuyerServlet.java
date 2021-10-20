package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Buyer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class UpdateBuyerServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            int buyerId = 0;
            String buyerName = null;
            int buyerBalance = 0;

            //if ID is entered continue
            if (!request.getParameter("buyerId").isEmpty()) {
                buyerId = Integer.parseInt(request.getParameter("buyerId"));

                if (!request.getParameter("newBuyer").isEmpty()) {
                    buyerName = request.getParameter("newBuyer");
                }
                if (!request.getParameter("balance").isEmpty()) {
                    buyerBalance = Integer.parseInt(request.getParameter("balance"));
                }

                // at least one file upoload or input text must not be null or empty to execute update, just notify the user in that case
                if (buyerName == null && buyerBalance == 0) {
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
                    out.println("alert(\"Please enter at least one parameter to update!\")");
                    out.println("document.getElementById(\"okButton\").onclick = function () {");
                    out.println("location.href = \"buyers.jsp\";");
                    out.println("};");
                    out.println("</script>");
                    out.println("</body>");
                    out.println("</html>");
                } else {
                    Session session = HibernateUtil.createSessionFactory().openSession();
                    Transaction transaction = null;

                    try {
                        transaction = session.beginTransaction();

                        Buyer buyer = session.get(model.Buyer.class, buyerId);

                        if (buyer != null) {
                            if (buyerName != null) {
                                buyer.setName(buyerName);
                            }
                            if (buyerBalance != 0) {
                                buyer.setBalance(buyerBalance);
                            }

                            //checking if user entered any data to update in databse
                            if (buyerName != null || buyerBalance != 0) {
                                session.update(buyer);
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
                                out.println("<script src=\"javascript/ProcessUpdateBuyer.js\"></script>");
                                out.println("</body>");
                                out.println("</html>");
                            }
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
                out.println("alert(\"Please enter an ID to update the product!\")");
                out.println("document.getElementById(\"okButton\").onclick = function () {");
                out.println("location.href = \"buyers.jsp\";");
                out.println("};");
                out.println("</script>");
                out.println("</body>");
                out.println("</html>");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
