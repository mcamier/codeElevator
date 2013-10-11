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
	public void haveToGoTo(int floor);
	public Command getNextCommand();
	public void reset();
}
