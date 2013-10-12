/**
 * 
 */
package com.mcamier.apps.elevator.request;

import com.mcamier.apps.elevator.utils.Direction;

/**
 * @author fufuuu
 *
 */
public class CallRequest extends Request{
	private Direction direction;
	
	/** An object describing an elevator's request made by user
	 * @param fromFloor
	 * @param direction
	 */
	public CallRequest(final int fromFloor, final Direction direction) {
		this.setFloor(fromFloor);
		this.setDirection(direction);
	}
	
	/**
	 * @return the direction intended of the elevator's request
	 */
	public final Direction getDirection() {
		return direction;
	}
	
	/**
	 * @param aDirection
	 */
	protected final void setDirection(final Direction aDirection) {
		this.direction = aDirection;
	}
}
