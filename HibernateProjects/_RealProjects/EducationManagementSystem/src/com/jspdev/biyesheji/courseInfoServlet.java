package com.jspdev.biyesheji;

import java.io.IOException;
//import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class courseInfoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public courseInfoServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("gb2312");
		response.setContentType("text/html;charset=gb2312");
//		PrintWriter out = response.getWriter();
		String cno=(String) request.getParameter("cno");
		try{
			
			Course course=new Course();
			course.setId(request.getParameter("id"));
			course.setName(request.getParameter("name"));
			course.setCredit(Long.parseLong(request.getParameter("credit")));
			course.setType(request.getParameter("type"));
			course.setTeacher(request.getParameter("teacher"));
			course.setTime(request.getParameter("time"));
			course.setAddress(request.getParameter("address"));
			course.changeCourseInfo(cno,course);
		}
		catch(Exception e){}
		javax.servlet.RequestDispatcher dis=request.getRequestDispatcher("../form/coursesQueryResult.jsp");
		dis.forward(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
