package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Product;
import org.apache.commons.io.IOUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100)   // 100 MB))
public class UpdateProductServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            int productId = 0;
            String productName = null;
            String productColor = null;
            double productPrice = 0;
            Part productImage = request.getPart("updateProductImage");

            //checking if file is uploaded
            boolean uploadedFile = request.getParts().parallelStream().reduce(false, (status, p) -> {
                return status;
            }, Boolean::logicalOr);

            //if ID is entered continue
            if (!request.getParameter("updateProductID").isEmpty()) {
                productId = Integer.parseInt(request.getParameter("updateProductID"));

                if (!request.getParameter("updateProductName").isEmpty()) {
                    productName = request.getParameter("updateProductName");
                }
                if (!request.getParameter("updateProductColor").isEmpty()) {
                    productColor = request.getParameter("updateProductColor");
                }

                if (!request.getParameter("updateProductPrice").isEmpty()) {
                    try {
                        productPrice = Double.parseDouble(request.getParameter("updateProductPrice"));
                    } catch (NumberFormatException ex) {
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
                        out.println("alert(\"Please enter a number for a price!\")");
                        out.println("document.getElementById(\"okButton\").onclick = function () {");
                        out.println("location.href = \"products.jsp\";");
                        out.println("};");
                        out.println("</script>");
                        out.println("</body>");
                        out.println("</html>");
                        return;
                    }
                }

                // at least one file upoload or input text must not be null or empty to execute update, just notify the user in that case
                if (productName == null && productColor == null && productPrice == 0 && !uploadedFile) {
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
                    out.println("location.href = \"products.jsp\";");
                    out.println("};");
                    out.println("</script>");
                    out.println("</body>");
                    out.println("</html>");
                } else {

                    Session session = HibernateUtil.createSessionFactory().openSession();
                    Transaction transaction = null;

                    try {
                        transaction = session.beginTransaction();

                        Product product = session.get(model.Product.class, productId);

                        if (product != null) {
                            if (productName != null) {
                                product.setName(productName);
                            }
                            if (productColor != null) {
                                product.setColor(productColor);
                            }
                            if (productPrice != 0) {
                                product.setPrice(productPrice);
                            }
                            if (productImage != null) {
                                product.setImage(IOUtils.toByteArray(productImage.getInputStream()));
                            }

                            //checking if user entered any data to update in databse
                            if (productName != null || productColor != null || productPrice != 0) {
                                session.update(product);
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
                                out.println("<script src=\"javascript/ProcessUpdateProduct.js\"></script>");
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
                out.println("location.href = \"products.jsp\";");
                out.println("};");
                out.println("</script>");
                out.println("</body>");
                out.println("</html>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

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
