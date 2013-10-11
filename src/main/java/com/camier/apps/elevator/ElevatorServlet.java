/**
 * 
 */
package com.camier.apps.elevator;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fufuuu
 *
 */
@SuppressWarnings("serial")
public class ElevatorServlet 
	extends HttpServlet {

	private final static IElevatorEngine engine = new ScanElevatorEngine(6);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String path = req.getRequestURI().substring(req.getContextPath().length() + 1);
		
		resp.setContentType("charset=UTF-8");
		resp.setCharacterEncoding( "UTF-8" );
		resp.setStatus(200);
		
		switch (path) {
		case "reset":
			System.out.println("<==== event reset ");
			engine.reset();			
			break;
		
		case "go":
			System.out.println("<==== event go to " + req.getParameter("floorToGo"));
			engine.haveToGoTo(Integer.parseInt(req.getParameter("floorToGo")));
			break;
		
		case "call":
			System.out.println("<==== event call from " + req.getParameter("atFloor") + " to " + req.getParameter("to"));
			int atFloor = Integer.parseInt(req.getParameter("atFloor"));
			Direction toGo = (req.getParameter("to") == "UP") ? Direction.UP : Direction.DOWN; 
			engine.call(new Call(atFloor, toGo));
			break;
			
		case "userHasEntered":
			System.out.println("<==== event userHasEntered");
			break;

		case "userHasExited":
			System.out.println("<==== event userHasExited");
			break;
		
		case "nextCommand":
			Command command = engine.getNextCommand();
			System.out.println("====> " + command);
			PrintWriter out = resp.getWriter();
			out.println(command);
			break;
		
		default:
			resp.setStatus(404);
			break;
		}
		
		
		
	}
	
}
