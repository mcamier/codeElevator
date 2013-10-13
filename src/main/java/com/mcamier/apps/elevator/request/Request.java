/**
 * 
 */
package com.mcamier.apps.elevator.request;

import com.mcamier.apps.elevator.utils.Direction;

/**
 * @author fufuuu
 *
 */
public abstract class Request implements IRequest {

	/**
	 * 
	 */
	private int atFloor;
	
	public Request(final int atFloor) {
		this.setFloor(atFloor);
	}

	
	/* (non-Javadoc)
	 * @see com.mcamier.apps.elevator.request.IRequest#getFloor()
	 */
	@Override
	public final int getFloor() {
		return atFloor;
	}
	
	
	/**
	 * @param aFloor
	 */
	protected final void setFloor(final int aFloor) {
		this.atFloor = aFloor;
	}

	
	/* (non-Javadoc)
	 * @see com.mcamier.apps.elevator.request.IRequest#directionFrom(int)
	 */
	@Override
	public final Direction directionFrom(int floor) {
		if(floor < getFloor()) {
			return Direction.UP;
		} else if (floor > getFloor()) {
			return Direction.DOWN;
		}
		return Direction.NONE;
	}
	
	
	/* (non-Javadoc)
	 * @see com.mcamier.apps.elevator.request.IRequest#isReachableWithDirection(com.mcamier.apps.elevator.utils.Direction)
	 */
	@Override
	public final boolean isReachableWithDirectionFromPosition(Direction direction, int position) {
		if(Direction.NONE == direction) {
			return true;
		}
		
		if ( this.directionFrom(position) == direction ) { 
			return true;
		}
		
		return false;
	}
}