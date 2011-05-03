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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author madis
 */
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
        if (testType.equals("SyncTests")) {
            
            SyncTestTimes clientTimes = gson.fromJson(timesJson, SyncTestTimes.class);
            SyncTestTimes serverTimes = SyncTestTimesManager.sharedManager().getTimesForTestID(testID);
            serverTimes.updateWith(clientTimes);
            responseJson = gson.toJson(serverTimes);
            
        } else if (testType.equals("AsyncTests")) {
            AsyncTestTimes times = gson.fromJson(timesJson, AsyncTestTimes.class);
            // TODO: same as befora only for Asynchronous tests
        } else {
            StringBuilder builder = new StringBuilder();
            for (SyncTestTimes t : SyncTestTimesManager.sharedManager().getAllTimes()) {
                builder.append(gson.toJson(t));
                System.out.println("Added time to response : " + gson.toJson(t));
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
