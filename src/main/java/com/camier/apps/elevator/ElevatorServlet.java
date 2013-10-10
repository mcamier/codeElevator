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

	private final static IElevatorEngine engine = new ClassicElevatorEngine();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String path = req.getRequestURI().substring(req.getContextPath().length() + 1);
		
		switch (path) {
		case "reset":
			engine.reset();			
			break;
		
		case "go":
			engine.goTo(Integer.parseInt(req.getParameter("floorToGo")));
			break;
		
		case "call":
			int atFloor = Integer.parseInt(req.getParameter("atFloor"));
			Direction toGo = (req.getParameter("to") == "UP") ? Direction.UP : Direction.DOWN; 
			engine.call(new Call(atFloor, toGo));
			break;
		
		case "userHasEntered":
			break;

		case "userHasExited":
			
			break;
		
		case "nextCommand":
			break;
		
		default:
			resp.setStatus(404);
			break;
		}
		
		resp.setContentType("charset=UTF-8");
		resp.setCharacterEncoding( "UTF-8" );
		resp.setStatus(200);
		PrintWriter out = resp.getWriter();
		out.println(engine.getNextCommand());
	}
	
}
