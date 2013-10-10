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
	
	public Call(int fromFloor, Direction direction) {
		this.fromFloor = fromFloor;
		this.direction = direction;
	}
	
	public final int getFromFloor() {
		return fromFloor;
	}
	
	public final Direction getDirection() {
		return direction;
	}
}
