/**
 * 
 */
package com.camier.apps.elevator;

import java.util.ArrayList;

/**
 * @author fufuuu
 *
 */
public class ClassicElevatorEngine 
	implements IElevatorEngine {

	private ArrayList<Call> pendingCalls;
	private ArrayList<Call> processingCalls;
	private int currentFloor;
	private boolean isDoorOpened;
	private int[] floorsIntended;
	private final int totalFloors;
	
	private Direction lastDirection;
	
	
	public ClassicElevatorEngine(final int _totalFloors) {
		totalFloors = _totalFloors;
		reset();
	}
	
	public final int getCurrentFloor() {
		return currentFloor;
	}
	
	public final void setCurrentFloor(final int currentPosition) {
		this.currentFloor = currentPosition;
	}
	
	@Override
	public void call(Call call) {
		
		if(lastDirection != null) {
			if (lastDirection == Direction.UP) {
				if(call.getFromFloor() >= currentFloor) {
					processingCalls.add(call);
				}
				else {
					pendingCalls.add(call);
				}
			} else {
				if(call.getFromFloor() <= currentFloor) {
					processingCalls.add(call);
				}
				else {
					pendingCalls.add(call);
				}
			}
		} else {
			processingCalls.add(call);
		}
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
		pendingCalls = new ArrayList<Call>();
		processingCalls = new ArrayList<Call>();
		lastDirection = null;
		setCurrentFloor(0);
		closeDoor();
		floorsIntended = new int[totalFloors];
		for(int i = 0 ; i < totalFloors ; i++) {
			floorsIntended[i] = 0;
		}
	}
	
	@Override
	public Command getNextCommand() {
		if(isDoorOpened()) {
			closeDoor(); return Command.CLOSE;
		}			
		
		if(floorsIntended[currentFloor] > 0) {
			floorsIntended[currentFloor] = 0;
			openDoor(); return Command.OPEN;
		}
		
		if(processingCalls.size() <= 0 && pendingCalls.size() > 0) {
			// write logic to get new processingCalls from pendingCalls
			processingCalls = pendingCalls;
			pendingCalls = new ArrayList<Call>();
		}
		
		if(processingCalls.size() <= 0) {
			return Command.NOTHING;
		}
		else {
			if(processingCalls.get(0).getFromFloor() > currentFloor) {
				currentFloor++;
				lastDirection = Direction.UP; return Command.UP;
			} else if(processingCalls.get(0).getFromFloor() < currentFloor) {
				currentFloor--;
				lastDirection = Direction.DOWN; return Command.DOWN;
			} else {
				processingCalls.remove(processingCalls.size()-1);
				openDoor(); return Command.OPEN;	
			}
		}
	}

	@Override
	public void goTo(int floor) {
		++(floorsIntended[floor]);
	}
}
