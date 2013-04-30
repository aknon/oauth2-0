package com.goraksh.rest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author niteshk
 *
 */
public class HelloWorld extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String location = "http://restful-fundamentals.blogspot.in/2013/04/oauth-20-authorisation-grant-different.html#access_token=aaga";
		//response.setHeader("Location",   "http://restful-fundamentals.blogspot.in/2013/04/oauth-20-authorisation-grant-different.html#http");
		//response.setHeader("Location",   "/restful/hellojsp#access_token=aagaga");
		response.setHeader("Location",   "http://restful-fundamentals.blogspot.in/2013/04/oauth-20-authorisation-grant-different.html#access_token=aaga");
		
		response.setStatus(302);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Hello World!</title>");
		
		out.println("<script type=\"text/JavaScript\">");
		//out.println( "document.write(\"heeehehheh\");");
		
		out.println( "var urlStr = location.href;" );
		out.println( "alert(urlStr);" );
		
		out.println("</script>");
		
		out.println("</head>");

		out.println("<body>");
		
			out.println("</body>");

		out.println("</html>");
		out.close();
	}
}
