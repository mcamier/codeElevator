/**
 * 
 */
package com.mcamier.apps.elevator.engine;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.mcamier.apps.elevator.request.Call;
import com.mcamier.apps.elevator.utils.Command;
import com.mcamier.apps.elevator.utils.Direction;

/**
 * @author fufuuu
 *
 */
public class ScanElevatorEngine 
	implements IElevatorEngine {

	private Set<Call> pendingCalls;
	private Set<Call> processingCalls;

	private Direction lastDirection;
	
	private final int totalFloors;
	private int farthestDestination;
	private int currentFloor;
	private int[] floorsIntended;
	
	private boolean isDoorOpened;
	
	
	/** Initialize the elevator for a specific amount of floors
	 * @param _totalFloors
	 */
	public ScanElevatorEngine(final int _totalFloors) {
		totalFloors = _totalFloors;
		reset();
	}
	
	/**
	 * @return
	 */
	public final int getCurrentFloor() {
		return currentFloor;
	}
	
	/**
	 * @param currentPosition
	 */
	public final void setCurrentFloor(final int currentPosition) {
		this.currentFloor = currentPosition;
	}
	
	/**
	 * @return
	 */
	public boolean isDoorOpened() {
		return isDoorOpened;
	}
	
	/**
	 * @return
	 */
	public Command closeDoor() {
		this.isDoorOpened = false;
		return Command.CLOSE;
	}
	
	/**
	 * @return
	 */
	public Command openDoor() {
		this.isDoorOpened = true;
		return Command.OPEN;
	}
	
	/**
	 * @return
	 */
	public Command goUp() {
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
	
	/**
	 * @return
	 */
	public Command goDown() {
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
	
	/**
	 * @param call
	 * @return
	 */
	public boolean isCallOnMyWay(Call call) {
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


	/**
	 * @return
	 */
	public boolean isCurrentFloorATarget() {
		return (floorsIntended[currentFloor] > 0) ? true : false;
	}
	
	public boolean isCurrentFloorHasCall() {
		for(Iterator<Call> callIter = processingCalls.iterator() ; callIter.hasNext();) {
			Call c = callIter.next();
			if(c.getFromFloor() == currentFloor) return true;
		}
		for(Iterator<Call> callIter = pendingCalls.iterator() ; callIter.hasNext();) {
			Call c = callIter.next();
			if(c.getFromFloor() == currentFloor) return true;
		}
		return false;
	}
	
	
	/* (non-Javadoc)
	 * @see com.camier.apps.elevator.IElevatorEngine#call(com.camier.apps.elevator.Call)
	 */
	@Override
	public void call(Call call) {
		if(isCallOnMyWay(call)) {
			farthestDestination = (farthestDestination <= call.getFromFloor()) ? call.getFromFloor() : farthestDestination;
			processingCalls.add(call);
		} else {
			pendingCalls.add(call);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.camier.apps.elevator.IElevatorEngine#reset()
	 */
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
	
	
	/* (non-Javadoc)
	 * @see com.camier.apps.elevator.IElevatorEngine#haveToGoTo(int)
	 */
	@Override
	public void haveToGoTo(final int floor) {
		++(floorsIntended[floor]);
	}
	
	
	/* (non-Javadoc)
	 * @see com.camier.apps.elevator.IElevatorEngine#getNextCommand()
	 */
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
//			else {
//				int remains = 0;
//				for(int i = (totalFloors-1) ; i > -1 ; i-- ) {
//					remains += floorsIntended[i];
//				}
//				if(remains > 0) {
//					for(int i = (totalFloors-1) ; i > -1 ; i-- ) {
//						farthestDestination = (floorsIntended[i] > 0) ? i : farthestDestination;
//					}
//					lastDirection = (farthestDestination > currentFloor) ? Direction.UP : Direction.DOWN; 
//				}
//			}
		}
		
		if(processingCalls.size() <= 0) {
			return Command.NOTHING;
		} else {
			
			if(isCurrentFloorHasCall()) {
				return openDoor();
			}

			//si chercher nouvelle destination
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

	
	/**
	 * @param calls
	 * @return
	 */
	public int findFarthestDestination(final Set<Call> calls) {
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

	
	/**
	 * 
	 */
	public void swapDirection() {
		lastDirection = (lastDirection == Direction.DOWN ) ? Direction.UP : Direction.DOWN; 
	}
}
