/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.ds.mobilecloud.performance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author madis
 */
@WebServlet(name = "RequestTestTimesServlet", urlPatterns = {"/cloudimageprocessing-server/RequestTestTimesServlet"})
public class RequestTestTimesServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String testType = request.getParameter("testType");
        String testID = request.getParameter("testID");
        String timesJson = request.getParameter("clientTimes");
        String responseJson = null;
        
        Gson gson = new GsonBuilder().create();
        if (testType != null && testType.equals("SyncTests")) {
            System.out.println("Getting sync test times from client");
            SyncTestTimes clientTimes = gson.fromJson(timesJson, SyncTestTimes.class);
            SyncTestTimes serverTimes = SyncTestTimesManager.sharedManager().getTimesForTestID(testID);
            serverTimes.updateWith(clientTimes);
            responseJson = gson.toJson(serverTimes);
            
        } else if (testType != null && testType.equals("AsyncTests")) {
            //AsyncTestTimes times = gson.fromJson(timesJson, AsyncTestTimes.class);
            AsyncTestTimes clientTimes = gson.fromJson(timesJson, AsyncTestTimes.class);
			AsyncTestTimes serverTimes = (AsyncTestTimes)TestTimesManager.sharedManager().getTimesForTestID(clientTimes.getTestID());
			serverTimes.updateWith(clientTimes);
			responseJson = gson.toJson(serverTimes);
        } else {
            StringBuilder builder = new StringBuilder();
//            for (SyncTestTimes t : SyncTestTimesManager.sharedManager().getAllTimes()) {
//                builder.append(gson.toJson(t));
//                System.out.println("Added time to response : " + gson.toJson(t));
//            }
			for (Object time : TestTimesManager.sharedManager().getAllTimes()) {
				builder.append(gson.toJson(time));
				builder.append("\n<BR>");
//				if (time instanceof SyncTestTimes) {
//					builder.append(((SyncTestTimes)time).toString());
//				} else if (time instanceof AsyncTestTimes) {
//					builder.append(((AsyncTestTimes)time).toString());
//				} else {
//					builder.append("Times was neither Async nor Sync");
//				}
				
			}
            responseJson = builder.toString();
        }
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.write(responseJson);
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
