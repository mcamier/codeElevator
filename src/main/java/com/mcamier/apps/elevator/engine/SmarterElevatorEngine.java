/**
 * 
 */
package com.mcamier.apps.elevator.engine;

import java.util.Iterator;

import com.mcamier.apps.elevator.request.IRequest;
import com.mcamier.apps.elevator.utils.Command;
import com.mcamier.apps.elevator.utils.Direction;

/**
 * @author fufuuu
 *
 */
public class SmarterElevatorEngine extends ScanElevatorEngine {

	public SmarterElevatorEngine(int _totalFloors, int aCapacity) {
		super(_totalFloors, aCapacity);
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
			} else if( directionTargeted == Direction.DOWN) {
				return goDown();
			}
		}
		
		return Command.NOTHING;
	}
	
	
	public Direction getPriority() {
		// TODO Add granularity to deal with limited lift's capacity
		int priorityUp = 0, priorityDown = 0, priorityTemp = 0;
		
		for (Iterator<IRequest> iter = callPool.iterator(); iter.hasNext();) {
			IRequest request = iter.next();

			priorityTemp = request.getRawPriority() * (totalFloors - Math.abs(request.getFloor() - currentFloor));
			
			if (request.directionFrom(currentFloor) == Direction.UP) {
				priorityUp += priorityTemp;
			} else {			
				priorityDown += priorityTemp;
			}
		}
		
		if(priorityDown > 0) {
			priorityDown /= callPool.getAmountOfRequestsBelow(currentFloor);
		}
		if(priorityUp > 0) {
			priorityUp /= callPool.getAmountOfRequestsAbove(currentFloor);
		}
		
		if(priorityUp < priorityDown) {
			return Direction.DOWN;
		}
		return Direction.UP;
	}
}
