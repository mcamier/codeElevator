/**
 * 
 */
package com.mcamier.apps.elevator.engine;

import com.mcamier.apps.elevator.request.CallRequest;
import com.mcamier.apps.elevator.request.DestinationRequest;
import com.mcamier.apps.elevator.request.IRequest;
import com.mcamier.apps.elevator.utils.Command;
import com.mcamier.apps.elevator.utils.Direction;

/**
 * @author fufuuu
 *
 */
public class ScanElevatorEngine 
	implements IElevatorEngine {

	private final int totalFloors;
	private int currentFloor;
	private boolean isDoorOpened;
	
	private Direction lastDirection;
	
	private CallPool callPool;
	
	
	/** Initialize the elevator for a specific amount of floors
	 * @param _totalFloors
	 */
	public ScanElevatorEngine(final int _totalFloors) {
		totalFloors = _totalFloors;
		callPool = new CallPool();
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
	

	/* (non-Javadoc)
	 * @see com.mcamier.apps.elevator.IElevatorEngine#call(com.camier.apps.elevator.Call)
	 */
	@Override
	public void call(final CallRequest call) {
		callPool.add(call);
	}
	
	
	/* (non-Javadoc)
	 * @see com.mcamier.apps.elevator.IElevatorEngine#haveToGoTo(int)
	 */
	@Override
	public void haveToGoTo(final DestinationRequest dest) {
		callPool.add(dest);
	}
	
	
	/* (non-Javadoc)
	 * @see com.mcamier.apps.elevator.IElevatorEngine#reset()
	 */
	@Override
	public void reset() {
		lastDirection = Direction.NONE;
		callPool.clear();
		setCurrentFloor(0);
		closeDoor();
	}
	
	
	/* (non-Javadoc)
	 * @see com.mcamier.apps.elevator.engine.IElevatorEngine#getNextCommand()
	 */
	@Override
	public final Command getNextCommand() {
		if(isDoorOpened()) {
			return closeDoor();
		}
		return computeNextCommand();
	}


	/**
	 * @return
	 */
	private Command computeNextCommand() {
		if( !callPool.isEmpty() ) {
			if (callPool.isRequestAt(currentFloor) ) {
				callPool.removeRequestsAt(currentFloor);
				return openDoor();
			}
			
			IRequest targetRequest = getATarget();
		
			if( targetRequest.getFloor() > currentFloor ) {
				return goUp();
			} else if( targetRequest.getFloor() < currentFloor ) {
				return goDown();
			}
		}
		
		return Command.NOTHING;
	}
	
	
	/**
	 * @return
	 */
	private IRequest getATarget() {
		IRequest targetFloor = null;
		
		switch (lastDirection) {
		case NONE:
			targetFloor = callPool.get(0);
			break;

		case UP:
			targetFloor = callPool.getHigherRequestThan(currentFloor);
			if(targetFloor.getFloor() == currentFloor ) {
				targetFloor = callPool.getLowerRequestThan(currentFloor);
			}
			break;

		case DOWN:
			targetFloor = callPool.getLowerRequestThan(currentFloor);
			if(targetFloor.getFloor() == currentFloor ) {
				targetFloor = callPool.getHigherRequestThan(currentFloor);
			}
			break;

		default:
			break;
		}
		return targetFloor;
	}
}
