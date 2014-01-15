package org.proffart.pan;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class User
 */
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger LOG = Logger.getLogger(User.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
	    out.println("Barev Axper Jan !");
	    LOG.info("Test log4j");
	    try{
		    DbMAnager db = DbMAnager.getInstance();
		    String sql = "SELECT * FROM `user` WHERE `user_name` = 'admin'";
		    HashMap<String, String> res = db.getRow(sql);
		    out.println("Barev "+res.get("first_name")+" Jan !");
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
