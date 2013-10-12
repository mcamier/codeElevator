/**
 * 
 */
package com.mcamier.apps.elevator.request;

/**
 * @author fufuuu
 *
 */
public abstract class Request implements IRequest {

	/**
	 * 
	 */
	private int atFloor;
	
	/* (non-Javadoc)
	 * @see com.mcamier.apps.elevator.request.IRequest#getFloor()
	 */
	@Override
	public final int getFloor() {
		return atFloor;
	}
	
	protected final void setFloor(final int aFloor) {
		this.atFloor = aFloor;
	}
}