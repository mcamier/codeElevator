/**
 * 
 */
package com.camier.apps.elevator;


/**
 * @author fufuuu
 *
 */
public interface IElevatorEngine {

	public void call(Call call);
	public void goTo(int floor);
	public Command getNextCommand();
	public void reset();
}
