/**
 * 
 */
package com.mcamier.apps.elevator.request;

/**
 * @author fufuuu
 *
 */
public class DestinationRequest extends Request {

	private final int rawPriority = 3;

	
	/**
	 * @param atFloor
	 */
	public DestinationRequest(int atFloor) {
		super(atFloor);
	}
	
	
	/* (non-Javadoc)
	 * @see com.mcamier.apps.elevator.request.IRequest#getRawPriority()
	 */
	public int getRawPriority() {
		return this.rawPriority;
	}
}
