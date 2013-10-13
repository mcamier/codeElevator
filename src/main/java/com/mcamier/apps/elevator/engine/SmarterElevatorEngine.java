/**
 * 
 */
package com.mcamier.apps.elevator.engine;

import java.util.Iterator;

import com.mcamier.apps.elevator.request.CallRequest;
import com.mcamier.apps.elevator.request.IRequest;
import com.mcamier.apps.elevator.utils.Command;
import com.mcamier.apps.elevator.utils.Direction;

/**
 * @author fufuuu
 *
 */
public class SmarterElevatorEngine extends ScanElevatorEngine {

	public SmarterElevatorEngine(int _totalFloors) {
		super(_totalFloors);
	}

	@Override
	public Command computeNextCommand() {
		if( !callPool.isEmpty() ) {
			if (callPool.isRequestAt(currentFloor) ) {
				callPool.removeRequestsAt(currentFloor);
				return openDoor();
			}
			
			Direction directionTargeted = getPriority();
		
			if( directionTargeted == Direction.UP) {
				return goUp();
			} else {
				return goDown();
			}
		}
		
		return Command.NOTHING;
	}
	
	public Direction getPriority() {
		int priorityUp = 0, priorityDown = 0, priorityTemp = 0;
		
		for (Iterator<IRequest> iter = callPool.iterator(); iter.hasNext();) {
			IRequest request = iter.next();

			priorityTemp = totalFloors - Math.abs(request.getFloor() - currentFloor);
			
			if(request instanceof CallRequest) {
				priorityTemp *= 1;
			} else {
				priorityTemp *= 2;
			}
			
			if (request.directionFrom(currentFloor) == Direction.UP) {
				priorityUp += priorityTemp;
			} else {			
				priorityDown += priorityTemp;
			}
			
			if(priorityUp > priorityDown) {
				return Direction.UP;
			}
			return Direction.DOWN;
		}
		
		return null;
	}
}
