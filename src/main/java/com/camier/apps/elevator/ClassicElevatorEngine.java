/**
 * 
 */
package com.camier.apps.elevator;

import java.util.LinkedList;

/**
 * @author fufuuu
 *
 */
public class ClassicElevatorEngine 
	implements IElevatorEngine {

	private LinkedList<Call> callPool;
	private int currentFloor;
	private boolean isDoorOpened;
	private int[] floorsIntended;

	public ClassicElevatorEngine() {
		reset();
	}
	
	
	public final LinkedList<Call> getCallPool() {
		return callPool;
	}
	
	private final void setCallPool(final LinkedList<Call> callPool) {
		this.callPool = callPool;
	}
	
	@Override
	public final int getCurrentFloor() {
		return currentFloor;
	}
	
	@Override
	public final void setCurrentFloor(final int currentPosition) {
		this.currentFloor = currentPosition;
	}
	
	@Override
	public void call(Call call) {
		this.getCallPool().addLast(call);
	}
	
	@Override
	public boolean isDoorOpened() {
		return isDoorOpened;
	}
	
	@Override
	public void closeDoor() {
		this.isDoorOpened = false;
	}
	
	@Override
	public void openDoor() {
		this.isDoorOpened = true;
	}


	@Override
	public void reset() {
		setCallPool(new LinkedList<Call>());
		setCurrentFloor(1);
		closeDoor();
		floorsIntended = new int[5];
		floorsIntended[0] =
		floorsIntended[1] =
		floorsIntended[2] =
		floorsIntended[3] =
		floorsIntended[4] = 0;
	}

	@Override
	public Command getNextCommand() {
		if(isDoorOpened()) {
			closeDoor();
			return Command.CLOSE;
		} else {
			if( callPool.size() > 0 ) {
				if( callPool.getFirst().getFromFloor() > getCurrentFloor() ) {
					setCurrentFloor(getCurrentFloor() + 1); 
					return Command.UP;
				} else if( callPool.getFirst().getFromFloor() < getCurrentFloor() ) {
					setCurrentFloor(getCurrentFloor() - 1); 
					return Command.DOWN;
				} else {
					callPool.pollFirst();
					openDoor();
					return Command.OPEN;
				}
			}
			else {
				return Command.NOTHING;
			}
		}
	}


	@Override
	public void goTo(int floor) {
		++(floorsIntended[floor-1]);
	}
}