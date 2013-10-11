/**
 * 
 */
package com.camier.apps.elevator;

/**
 * @author fufuuu
 *
 */
public class Call {
	private int fromFloor;
	private Direction direction;
	
	/** An object describing an elevator's request made by user
	 * @param fromFloor
	 * @param direction
	 */
	public Call(int fromFloor, Direction direction) {
		this.fromFloor = fromFloor;
		this.direction = direction;
	}
	
	/**
	 * @return the origin of the elevator's request
	 */
	public final int getFromFloor() {
		return fromFloor;
	}
	
	/**
	 * @return the direction intended of the elevator's request
	 */
	public final Direction getDirection() {
		return direction;
	}
}
