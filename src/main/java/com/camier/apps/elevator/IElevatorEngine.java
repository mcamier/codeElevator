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
	public boolean isDoorOpened();
	public void closeDoor();
	public void openDoor();
	public Command getNextCommand();
	public int getCurrentFloor();
	public void setCurrentFloor(int newFloorPosition);
	public void reset();
}
