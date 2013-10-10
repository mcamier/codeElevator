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
	private final int totalFloors;
	
	private int i;
	
	public ClassicElevatorEngine(final int _totalFloors) {
		reset();
		totalFloors = _totalFloors;
	}
	
	public final LinkedList<Call> getCallPool() {
		return callPool;
	}
	
	private final void setCallPool(final LinkedList<Call> callPool) {
		this.callPool = callPool;
	}
	
	public final int getCurrentFloor() {
		return currentFloor;
	}
	
	public final void setCurrentFloor(final int currentPosition) {
		this.currentFloor = currentPosition;
	}
	
	@Override
	public void call(Call call) {
		this.getCallPool().addLast(call);
	}
	
	public boolean isDoorOpened() {
		return isDoorOpened;
	}
	
	public void closeDoor() {
		this.isDoorOpened = false;
	}
	
	public void openDoor() {
		this.isDoorOpened = true;
	}


	@Override
	public void reset() {
		setCallPool(new LinkedList<Call>());
		setCurrentFloor(1);
		closeDoor();
		floorsIntended = new int[totalFloors];
		for(int i = 0 ; i < totalFloors ; i++) {
			floorsIntended[i] = 0;
		}
		i = 1;
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
		++(floorsIntended[floor]);
	}
}
