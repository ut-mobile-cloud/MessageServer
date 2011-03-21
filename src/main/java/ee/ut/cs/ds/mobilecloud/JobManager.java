/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.ut.cs.ds.mobilecloud;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author madis
 */
@WebServlet(name="JobManager", urlPatterns={"/JobManager"})
public class JobManager extends HttpServlet {
	static String CLASS_QUALIFIER = "ee.ut.cs.ds.mobilecloud.";
	
	static AbstractJob fetchJobFromDescription(HttpServletRequest request) {
		
		String jobDescriptionJson = request.getParameter("jobDescription");
		System.out.println("jsonRequest : " + jobDescriptionJson);
		String jobClassName = request.getParameter("jobClass");
                System.out.println("jobClassName == " + jobClassName);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		Class jobClass = null;
		
		try {
			jobClass = Class.forName(CLASS_QUALIFIER + jobClassName);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(JobManager.class.getName()).log(Level.SEVERE, null, ex);
		}	
		return (AbstractJob)gson.fromJson(jobDescriptionJson, jobClass);
	}
	
	static List<File> getFilesFromRequest(HttpServletRequest request) {
		List <File> files = new ArrayList<File>();
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
							files.add(uploadedFile);
						} catch (Exception ex) {
							Logger.getLogger(UploadTest.class.getName()).log(Level.SEVERE, null, ex);
						}
					} else {
                            System.out.println("item : " + item.getName() + " fieldName : " + item.getFieldName() + " string " + item.getString());

                                            if(item.getFieldName().equalsIgnoreCase("jobDescription")) {
                                                
                                            } else if(item.getFieldName().equalsIgnoreCase("jobClass")) {
                                                
                                            }
                                        }
					
				}
			} catch (FileUploadException ex) {
				Logger.getLogger(UploadTest.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return files;
	}
	
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
                System.out.println("Will begin processing HttpServletRequest");
                List<File> files = getFilesFromRequest(request);
		AbstractJob job = fetchJobFromDescription(request);
		
		job.data = files;
		
		Thread jobThread = new Thread(job);
		jobThread.start();
		
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet JobManager</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet JobManager at " + request.getContextPath () + "</h1>");
//			out.println("file name : " + imageFileName + "file size : " + imageFileSize);
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
