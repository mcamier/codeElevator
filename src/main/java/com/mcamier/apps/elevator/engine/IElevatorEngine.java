/**
 * 
 */
package com.mcamier.apps.elevator.engine;

import com.mcamier.apps.elevator.request.CallRequest;
import com.mcamier.apps.elevator.request.DestinationRequest;
import com.mcamier.apps.elevator.utils.Command;


/**
 * @author fufuuu
 *
 */
public interface IElevatorEngine {

	/** Call the elevator from a specific floor to a requested direction
	 * @param call
	 */
	public void call(CallRequest call);
	
	
	/** Register a floor requested by an entered user
	 * @param floor
	 */
	public void haveToGoTo(DestinationRequest dest);
	
	
	/**
	 * @return the next command that the lift should execute
	 */
	public Command getNextCommand();
	
	
	/** Hard reset the state of the elevator.
	 * Used to reset all internal properties
	 */
	public void reset();
}
