/**
 * 
 */
package com.mcamier.apps.elevator.request;

import com.mcamier.apps.elevator.utils.Direction;

/**
 * @author fufuuu
 *
 */
public interface IRequest {

	/**
	 * @return the floor requested
	 */
	public int getFloor();

	/**
	 * @param floor
	 * @return the direction to reach from floor to the request's floor
	 */
	public Direction directionFrom(int floor);
	
	/**
	 * @param direction
	 * @return
	 */
	public boolean isReachableWithDirectionFromPosition(Direction direction, int position);
}
