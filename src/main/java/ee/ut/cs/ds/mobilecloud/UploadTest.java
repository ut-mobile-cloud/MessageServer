/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.ut.cs.ds.mobilecloud;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author madis
 */
@WebServlet(name="UploadTest", urlPatterns={"/UploadTest"})
public class UploadTest extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		if (ServletFileUpload.isMultipartContent(request)) {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			List items;
			try {
				items = upload.parseRequest(request);
				Iterator it = items.iterator();
				while(it.hasNext()) {
					FileItem item = (FileItem)it.next();
					if(!item.isFormField()) {
						//String root = getServletContext().getRealPath("/");
						File path = new File("/tmp/uploads");
						String fileName = item.getName();
						if(!path.exists()) {
							boolean status = path.mkdirs();
						}
						File uploadedFile = new File(path + "/" + fileName);
						try {
							item.write(uploadedFile);
						} catch (Exception ex) {
							Logger.getLogger(UploadTest.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
					
				}
			} catch (FileUploadException ex) {
				Logger.getLogger(UploadTest.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UploadTest</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UploadTest at " + request.getContextPath () + "</h1>");
			out.println("<form action=\"/UploadTest\" method=\"POST\" enctype=\"multipart/form-data\">"
					+ "<input type=\"file\" name=\"mptest\" />"
					+ "<input type=\"submit\" value=\"upload\" /> "
			+ "</form>");
            out.println("</body>");
            out.println("</html>");
            
        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
