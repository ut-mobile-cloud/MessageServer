/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.ds.mobilecloud;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author madis
 */
public class TryingOutStuff extends HttpServlet {

	private String processRegularRequest(HttpServletRequest request) {
		StringBuilder builder = new StringBuilder();
		Map<String, String[]> parameterMap = request.getParameterMap();
		for(String key : parameterMap.keySet()) {
			builder.append("key : " + key + " value : " + parameterMap.get(key).toString() + "\n");
		}
		
		return builder.toString();
	}
	private String processMultipartRequest(HttpServletRequest request) {
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		diskFileItemFactory.setSizeThreshold(40960); /* the unit is bytes */
		File repositoryPath = new File("/tmp/uploads");
		diskFileItemFactory.setRepository(repositoryPath);
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		servletFileUpload.setSizeMax(81920); /* the unit is bytes */
		StringBuilder builder = new StringBuilder();
		try {
			List fileItemsList = servletFileUpload.parseRequest(request);
			
			Iterator it = fileItemsList.iterator();
			while (it.hasNext()) {
				FileItem fileItem = (FileItem)it.next();
				if (fileItem.isFormField()){
					/* The file item contains a simple name-value pair of a form field */
					builder.append("I'm having a form field");
					String name = fileItem.getFieldName();
					String value = fileItem.getString();
					builder.append("name : " + name + " value : " + value);
				} else {
					
					builder.append("Got a file");
				}
}
		} catch (FileUploadException ex) {
			Logger.getLogger(TryingOutStuff.class.getName()).log(Level.SEVERE, null, ex);
		}
		return builder.toString();


	}
	/** 
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		NotificationCentre.getProviderForDevice("iphone")
				.withDeviceData("5324075b4c07afa9e2dbe15b74dbda2227a74d5537f0d4af24cc0aecda697f1c")
				.withKeyValuePairs("good morning", "dolly")
				.withMessage("wazzup")
				.send();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet TryingOutStuff</title>");  
			out.println("</head>");
			out.println("<body>");
			//out.println("<h1>Servlet TryingOutStuff at " + request.getContextPath () + "</h1>");
			out.println("<form method=\"POST\" action=\"/TryingOutStuff\" enctype=\"multipart/form-data\">");
			out.println("<input type=\"text\" name=\"nimi\"/>");
			out.println("<input type=\"file\" name=\"fail\"/>");
			out.println("<input type=\"submit\" name=\"submit\" value=\"Submit!\"/>");
			out.println("</form>");
			if(ServletFileUpload.isMultipartContent(request)) {
				out.println(processMultipartRequest(request));
			} else {
				out.println(processRegularRequest(request));
			}
			
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
