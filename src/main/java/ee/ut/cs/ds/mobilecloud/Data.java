/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.ds.mobilecloud;

/**
 *
 * @author root
 */
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class Data {

    public Data() {
    }
    private static final String dbUrl = "jdbc:mysql://localhost/EC2";
    private static final String dbClass = "com.mysql.jdbc.Driver";
    protected static final String TBL_CONFERENCES = "tblConference";
    protected static final String TBL_DEVICES = "Devices";
    protected static final String TBL_PUBLICATIONS = "tblPublications";
    protected static final String TBL_USERS = "Dump";
    protected static final String TBL_CONFERENCE_PLAY_LIST = "tblConferencePlayList";
    protected static final String EMPTY = "";
    protected static final String USER = "root";
    protected static final String PASSWORD = "200412569";
    protected static final String PARAM_CONFERENCE_ID = "conferenceID";
    protected static final String PARAM_CURRENT_LINKmysq = "currentLink";
    protected static final String PARAM_NEXT_LINK = "nextLink";

    public void SaveMessage(String msg) {
        String query = "insert into msg(message) values('" + msg + "');";
        executeUpdateInsert(query);
    }

    protected String generateGenericSelect(String table) {
        String query = EMPTY;
        query = query.concat("SELECT * FROM ").concat(table).concat("';");
        return query;
    }

    protected String getToken(String emailAddress) throws SQLException {
        String query = EMPTY;
        query = query.concat("SELECT token FROM Dump where emailAddress='").concat(emailAddress).concat("';");
        ResultSet set = executeSelect(query);
        if (set.next()) {
            return set.getString(1);
        }
        return query;
    }

    protected String generateSelect(String values[], String table) {
        String query = EMPTY;
        query = query.concat("SELECT ");
        for (String s : values) {
            query = query.concat(s).concat(", ");
        }
        query = query.substring(0, query.length() - 2);
        query = query.concat("FROM ").concat(table).concat(";");
        return query;
    }

    protected String generateQueryInsertUsers(String values[], String table) {
        String ret = "";
        ret = ret.concat("INSERT INTO ").concat(table).concat("(emailAddress,token) VALUES(");
        for (String s : values) {
            ret = ret.concat(s).concat(", ");
        }
        ret = ret.substring(0, ret.length() - 2);
        ret = ret.concat(");");
        return ret;
    }

    protected void closeConnection(Connection con) throws SQLException {
        con.close();
    }

    protected Connection getConnection() {
        try {
            Class.forName(dbClass);
            Connection con = DriverManager.getConnection(dbUrl, USER, PASSWORD);
            return con;
        } catch (Exception e) {
            return null;
        }
    }

    protected int executeUpdateInsert(String query) {
        try {
            Class.forName(dbClass);
            Connection con = DriverManager.getConnection(dbUrl, USER, PASSWORD);
            Statement stmt = con.createStatement();
            // ResultSet rs = stmt.executeQuery(query);
            return stmt.executeUpdate(query);
        } catch (Exception e) {
            String out = e.toString();
            return -1;
        }
    }

    protected ResultSet executeSelect(String query) {
        try {
            Class.forName(dbClass);
            Connection con = DriverManager.getConnection(dbUrl, USER, PASSWORD);
            Statement stmt = con.createStatement();
            return stmt.executeQuery(query);
        } catch (Exception e) {
            String out = e.toString();
            return null;
        }
    }
}
