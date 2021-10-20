package servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
public class AddProductServlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            // if some of the fields are empty
            boolean uploadedFile = request.getParts().parallelStream().reduce(false, (status, p) -> {
                return status;
            }, Boolean::logicalOr);
            
            if (request.getParameter("newProduct").isEmpty() || request.getParameter("productColor").isEmpty() || request.getParameter("productPrice").isEmpty() || uploadedFile) {
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
                out.println("alert(\"Please enter all data requered for new product!\")");
                out.println("document.getElementById(\"okButton\").onclick = function () {");
                out.println("location.href = \"products.jsp\";");
                out.println("};");
                out.println("</script>");
                out.println("</body>");
                out.println("</html>");
            } else {
                String productName = request.getParameter("newProduct");
                String productColor = request.getParameter("productColor");
                double productPrice = 0;
                Part imagePart = request.getPart("productImage");
                
                try {
                    productPrice = Double.parseDouble(request.getParameter("productPrice"));
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
                
                Session session = HibernateUtil.createSessionFactory().openSession();
                Transaction transaction = null;
                
                try {
                    transaction = session.beginTransaction();
                    
                    Product product = new Product();
                    product.setName(productName);
                    product.setColor(productColor);
                    product.setPrice(productPrice);
                    product.setImage(IOUtils.toByteArray(imagePart.getInputStream()));
                    
                    session.persist(product);
                    
                    transaction.commit();
                    
                } catch (HibernateException e) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    System.out.println(e.getMessage());
                } finally {
                    HibernateUtil.close();
                }
                
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>ProcessDataServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div>");
                out.println("<button id=\"continueButton\" style=\"height:100px;width:200px;marigin 0;position absolute;top: 50%;left: 50%transform: translate(-50%, -50%);\">Continue</button>");
                out.println("</div>");
                out.println("<script src=\"javascript/ProcessAddProduct.js\"></script>");
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

    private void saveImage(HttpServletResponse response, String imageName, Part imagePart) throws IOException {
        OutputStream out = null;
        InputStream filecontent = null;
        PrintWriter writer = null;
        
        writer = response.getWriter();
        
        try {
            out = new FileOutputStream(new File("C:\\WebShopResources\\" + imageName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        filecontent = imagePart.getInputStream();
        
        int read = 0;
        final byte[] bytes = new byte[1024];
        
        while ((read = filecontent.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        
        if (out != null) {
            out.close();
        }
        if (filecontent != null) {
            filecontent.close();
        }
    }
    
    private void createFolder() {
        //trying to write in C, if not allowed, then write in program files
        try {
            File folder = new File("C:\\WebShopResources");
            if (!folder.exists()) {
                folder.mkdir();
            }
        } catch (Exception e) {
            try {
                File folder = new File("C:\\Program Files\\WebShopResources");
                if (!folder.exists()) {
                    folder.mkdir();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
    }
}
