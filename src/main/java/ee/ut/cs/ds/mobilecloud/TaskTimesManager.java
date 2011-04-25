/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.ut.cs.ds.mobilecloud;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ee.ut.cs.ds.mobilecloud.TaskTimes;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author madis
 */
@WebServlet(name="TaskTimesManager", urlPatterns={"/TaskTimesManager"})
public class TaskTimesManager extends HttpServlet {
	static TaskTimesDataSource dataSource = TaskTimesDataSourceImpl.getInstance();
    
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action == null) {
			action = "getAllTimes";
		}
		String responseString = null;
		Gson gson = new GsonBuilder().create();
		
		if(action.equalsIgnoreCase("UpdateTimes")) {
			String newTimesJson = request.getParameter("taskTimes");
			String taskID = request.getParameter("taskID");
			TaskTimes times = gson.fromJson(newTimesJson, TaskTimes.class);
			dataSource.updateTimesForTaskID(taskID, times);
			TaskTimes updatedTimes = dataSource.getTimesForTaskID(taskID);
			
			responseString = gson.toJson(updatedTimes);
		} else if(action.equalsIgnoreCase("getTimes")) {
			String taskID = request.getParameter("taskID");
			TaskTimes requestedTimes = dataSource.getTimesForTaskID(taskID);
			responseString = gson.toJson(requestedTimes);
		} else if(action.equalsIgnoreCase("getAllTimes")) {
			String taskID = request.getParameter("taskID");
			List<TaskTimes> allTimes = dataSource.getAllTimes();
			responseString = gson.toJson(allTimes);
		} else if(action.equalsIgnoreCase("clearRecords")) {
			dataSource.clearRecords();
			responseString = "Sucessfully cleared all TaskTime records";
		}
		
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println(responseString);
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
