/**
 * 
 */
package com.camier.apps.elevator;

import java.util.HashSet;
import java.util.Set;

/**
 * @author fufuuu
 *
 */
public class ClassicElevatorEngine 
	implements IElevatorEngine {

	private Set<Call> pendingCalls;
	private Set<Call> processingCalls;
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
	
	public boolean isDoorOpened() {
		return isDoorOpened;
	}
	
	private Command closeDoor() {
		this.isDoorOpened = false;
		return Command.CLOSE;
	}
	
	private Command openDoor() {
		this.isDoorOpened = true;
		return Command.OPEN;
	}
	
	private Command goUp() {
		if (isDoorOpened()) {
			return closeDoor();
		}
		
		if(currentFloor < totalFloors) { 
			this.currentFloor++;
			lastDirection = Direction.UP;
			return Command.UP;
		}
		return Command.NOTHING;
	}
	
	private Command goDown() {
		if (isDoorOpened()) {
			return closeDoor();
		}
		
		if(currentFloor > 0) { 
			this.currentFloor--;
			lastDirection = Direction.DOWN;
			return Command.DOWN;
		}
		return Command.NOTHING;
	}
	
	private boolean isCallOnMyWay(Call call) {
		if(lastDirection != null) {
			if( currentFloor < call.getFromFloor() ) {
				return (lastDirection == Direction.UP) ? true : false;
			} else {
				return (lastDirection == Direction.DOWN) ? true : false;
			}
		} else {
			return true; // TODO Check if return true when lastDirection = null is a good practice
		}
	}

	@Override
	public void call(Call call) {
		if(isCallOnMyWay(call)) {
			processingCalls.add(call);
		} else {
			pendingCalls.add(call);
		}
	}

	@Override
	public void reset() {
		pendingCalls = new HashSet<Call>();
		processingCalls = new HashSet<Call>();
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
//		if(isDoorOpened()) {
//			return closeDoor(); 
//		}			
//		
//		if(floorsIntended[currentFloor] > 0) {
//			floorsIntended[currentFloor] = 0;
//			return openDoor();
//		}
//		
//		if(processingCalls.size() <= 0 && pendingCalls.size() > 0) {
//			processingCalls = pendingCalls;
//			pendingCalls = new HashSet<Call>();
//		}
//		
//		if(processingCalls.size() <= 0) {
//			return Command.NOTHING;
//		}
//		else {
//			
//			if(processingCalls.get(0).getFromFloor() > currentFloor) {
//				return goUp();
//			} else if(processingCalls.get(0).getFromFloor() < currentFloor) {
//				return goDown();
//			} else {
//				processingCalls.remove(processingCalls.size()-1);
//				return openDoor();	
//			}
//		}
		return Command.NOTHING;
	}

	@Override
	public void goTo(int floor) {
		++(floorsIntended[floor]);
	}
}
