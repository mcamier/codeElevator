/**
 * 
 */
package com.camier.apps.elevator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author fufuuu
 *
 */
public class ScanElevatorEngine 
	implements IElevatorEngine {

	private Set<Call> pendingCalls;
	private Set<Call> processingCalls;
	private int currentFloor;
	private boolean isDoorOpened;
	private int[] floorsIntended;
	private final int totalFloors;
	private int farthestDestination;
	
	private Direction lastDirection;
	
	
	public ScanElevatorEngine(final int _totalFloors) {
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
			return true;
		}
	}

	@Override
	public void call(Call call) {
		if(isCallOnMyWay(call)) {
			farthestDestination = (farthestDestination <= call.getFromFloor()) ? call.getFromFloor() : farthestDestination;
			processingCalls.add(call);
		} else {
			pendingCalls.add(call);
		}
	}

	private boolean isCurrentFloorATarget() {
		return (floorsIntended[currentFloor] > 0) ? true : false;
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
		farthestDestination = -1;
	}
	
	private void swapDirection() {
		lastDirection = (lastDirection == Direction.DOWN ) ? Direction.UP : Direction.DOWN; 
	}
	
	@Override
	public Command getNextCommand() {
		if (isDoorOpened()) {
			return closeDoor();
		}
		
		if(isCurrentFloorATarget()) {
			floorsIntended[currentFloor] = 0;
			return openDoor();
		}

		// this part below freaks me out, i gonna see it later because its doesnt works as well
		if(processingCalls.isEmpty()) {
			if(!(pendingCalls.isEmpty())) {
				processingCalls = pendingCalls;
				pendingCalls = new HashSet<Call>();
				swapDirection();
				farthestDestination = findFarthestDestination(processingCalls);
			} 
			else {
				int remains = 0;
				for(int i = (totalFloors-1) ; i > -1 ; i-- ) {
					remains += floorsIntended[i];
				}
				if(remains > 0) {
					for(int i = (totalFloors-1) ; i > -1 ; i-- ) {
						farthestDestination = (floorsIntended[i] > 0) ? i : farthestDestination;
					}
					lastDirection = (farthestDestination > currentFloor) ? Direction.UP : Direction.DOWN; 
				}
			}
		}
		
		if(processingCalls.size() <= 0) {
			return Command.NOTHING;
		} else {
			if(farthestDestination > currentFloor) {
				return goUp();
			} else if(farthestDestination < currentFloor) {
				return goDown();
			} else {
				processingCalls.remove(processingCalls.size()-1);
				return openDoor();	
			}
		}
	}

	private int findFarthestDestination(final Set<Call> calls) {
		int farthest = currentFloor;
		for(Iterator<Call> callIter = calls.iterator() ; callIter.hasNext();) {
			Call c = callIter.next();
			if(isCallOnMyWay(c)) {
				if(lastDirection == Direction.UP) {
					farthest = (c.getFromFloor() > farthest) ? c.getFromFloor() : farthest;
				} else {
					farthest = (c.getFromFloor() < farthest) ? c.getFromFloor() : farthest;
				}
			}
		}
		return farthest;
	}

	@Override
	public void haveToGoTo(final int floor) {
		++(floorsIntended[floor]);
	}
}
