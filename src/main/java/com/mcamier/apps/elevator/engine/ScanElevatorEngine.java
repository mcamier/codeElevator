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

	protected final int totalFloors;
	protected int capacity;
	protected int currentFloor;
	private boolean isDoorOpened;
	protected int amountUser;
	
	private Direction lastDirection;
	
	protected CallPool callPool;
	
	
	/** Initialize the elevator for a specific amount of floors
	 * @param _totalFloors
	 */
	public ScanElevatorEngine(final int _totalFloors, int aCapacity) {
		totalFloors = _totalFloors;
		capacity = aCapacity;
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
	 * @see com.mcamier.apps.elevator.engine.IElevatorEngine#userHasEntered()
	 */
	@Override
	public void userHasEntered() {
		amountUser++;
	}
	
	
	/* (non-Javadoc)
	 * @see com.mcamier.apps.elevator.engine.IElevatorEngine#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		if(amountUser > 0) {
			return false;
		}
		return true;
	}
	
	
	/* (non-Javadoc)
	 * @see com.mcamier.apps.elevator.engine.IElevatorEngine#userHasExited()
	 */
	@Override
	public void userHasExited() {
		amountUser--;
	}
	
	
	/* (non-Javadoc)
	 * @see com.mcamier.apps.elevator.IElevatorEngine#reset()
	 */
	@Override
	public void reset() {
		lastDirection = Direction.NONE;
		callPool.clear();
		amountUser = 0;
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
	public Command computeNextCommand() {
		if( !callPool.isEmpty() ) {
			// TODO check the capacity before open the door to pick up people in case of CallRequest
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
	public IRequest getATarget() {
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
