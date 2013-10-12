/**
 * 
 */
package com.mcamier.apps.elevator.engine;

import com.mcamier.apps.elevator.request.Call;
import com.mcamier.apps.elevator.utils.Command;


/**
 * @author fufuuu
 *
 */
public interface IElevatorEngine {

	/** Call the elevator from a specific floor to a requested direction
	 * @param call
	 */
	public void call(Call call);
	
	/** Register a floor requested by an entered user
	 * @param floor
	 */
	public void haveToGoTo(int floor);
	
	/**
	 * @return the next command that the lift should execute
	 */
	public Command getNextCommand();
	
	/** Hard reset the state of the elevator.
	 * Used to reset all internal properties
	 */
	public void reset();
}
