/**
 * 
 */
package com.camier.apps.elevator;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fufuuu
 *
 */
public class ElevatorServlet 
	extends HttpServlet {

	private final static ElevatorEngine engine = new ElevatorEngine();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String path = req.getRequestURI().substring(req.getContextPath().length() + 1);

		resp.setContentType("charset/UTF-8");
		resp.setCharacterEncoding( "UTF-8" );
		PrintWriter out = resp.getWriter();
		out.println("NOTHING");
		
		switch (path) {
		case "reset":
			resp.setStatus(200);
			break;
		
		case "go":
			resp.setStatus(200);
			break;
		
		case "call":
			int atFloor = Integer.parseInt(req.getParameter("atFloor"));
			Direction toGo = (req.getParameter("to") == "UP") ? Direction.UP : Direction.DOWN; 
			engine.callElevator(atFloor, toGo);
			resp.setStatus(200);
			break;
		
		case "userHasEntered":
			resp.setStatus(200);
			break;

		case "userHasExited":
			resp.setStatus(200);
			break;
		
		case "nextCommand":
			resp.setStatus(200);
			break;
		
		default:
			resp.setStatus(404);
//			String msg = "Method not allowed";
//			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
			break;
		}
	}
}
