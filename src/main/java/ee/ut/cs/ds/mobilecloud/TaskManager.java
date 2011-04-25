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
import org.apache.commons.fileupload.MultipartStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author madis
 */
@WebServlet(name = "TaskManager", urlPatterns = {"/TaskManager"})
public class TaskManager extends HttpServlet {

    static String CLASS_QUALIFIER = "ee.ut.cs.ds.mobilecloud.";
    static final String TEMP_FOLDER_FOR_IMAGES = "/tmp/uploads";
	// TODO: there seems to be a bug when no data field is present (ie making a 
	// request from browser, the servlet just crashes (on line 70
    AbstractTask processMultipartTaskRequest(HttpServletRequest request) {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<File> files = new ArrayList<File>();
        AbstractTask task = null;

        List items;
        try {
			items = upload.parseRequest(request);
            Iterator it = items.iterator();
            String taskClass = null;
            String taskDescriptionJson = null;

            while (it.hasNext()) {
                FileItem item = (FileItem) it.next();
                if (!item.isFormField()) {
                    // TODO: add file in multipart to list of files
                    files.add(storeAndReturnFileItemContents(item));
                } else {
                    if (item.getFieldName().equals("taskDescription")) {
                        taskDescriptionJson = item.getString();
                    }
                    if (item.getFieldName().equals("taskClass")) {
                        taskClass = item.getString();
                    }
                }
            }
			task = taskWithClassAndDescription(taskClass, taskDescriptionJson);
        } catch (Exception ex) {
            Logger.getLogger(TaskManager.class.getName()).log(Level.SEVERE, null, ex);
        }
		task.data = files;

		
        return task;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AbstractTask requestedTask = null;
		if (ServletFileUpload.isMultipartContent(request)) {
			requestedTask = processMultipartTaskRequest(request);
		} else {
			System.out.println("I got non-multipart request");
		}
		TaskStatusDataSourceImpl.getInstance().getStatus(requestedTask.getTaskID());
        Thread taskThread = new Thread(requestedTask);
        taskThread.start();

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TaskManager</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TaskManager at " + request.getContextPath() + "</h1>");
			out.println("Request was : " + request.getQueryString());
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

    private File storeAndReturnFileItemContents(FileItem item) {
        File path = new File(TEMP_FOLDER_FOR_IMAGES);
        String fileName = item.getName();
        if (!path.exists()) {
            boolean status = path.mkdirs();
        }
        File uploadedFile = new File(path + "/" + fileName);
        try {
            item.write(uploadedFile);
        } catch (Exception ex) {
            Logger.getLogger(TaskManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return uploadedFile;
    }

    private AbstractTask taskWithClassAndDescription(String taskClassName, String taskDescriptionJson) {
        System.out.println("jsonRequest : " + taskDescriptionJson);
        System.out.println("taskClassName == " + taskClassName);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Class taskClass = null;

        try {
            taskClass = Class.forName(CLASS_QUALIFIER + taskClassName);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TaskManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (AbstractTask) gson.fromJson(taskDescriptionJson, taskClass);
    }
}
