/**
 * 
 */
package com.mcamier.apps.elevator.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mcamier.apps.elevator.request.DestinationRequest;
import com.mcamier.apps.elevator.request.IRequest;
import com.mcamier.apps.elevator.utils.Direction;

/**
 * @author fufuuu
 *
 */
public final class CallPool {
	/**
	 * 
	 */
	private List<IRequest> requests;
	
	
	/**
	 * 
	 */
	public CallPool() {
			this.requests = new ArrayList<IRequest>();
	}
	
	
	/** Wrap isEmpty methods of List<IRequest>
	 * @return
	 */
	public final boolean isEmpty() {
		return this.requests.isEmpty();
	}
	
	
	/** Wrap add methods of List<IRequest>
	 * @param request
	 */
	public final void add(IRequest request) {
		this.requests.add(request);
	}
	
	
	/** Wrap remove methods of List<IRequest>
	 * @param request
	 */
	public final void remove(IRequest request) {
		this.requests.remove(request);
	}
	
	
	/** Wrap clear methods of List<IRequest>
	 * 
	 */
	public final void clear() {
		this.requests.clear();
	}
	
	
	/** Wrap get methods of List<IRequest>
	 * @param index
	 * @return
	 */
	public final IRequest get(final int index) {
		return this.requests.get(index);
	}
	
	/**
	 * @return Wrap iterator methods of List<IRequest>
	 */
	public final Iterator<IRequest> iterator() {
		return this.requests.iterator();
	}
	
	
	/**
	 * @param index
	 * @return
	 */
	public final IRequest getHigherRequestThan(final int index) {
		IRequest result = new DestinationRequest(index);
		for(Iterator<IRequest> iter = requests.iterator() ; iter.hasNext();) {
			IRequest request = iter.next();
			result = (request.getFloor() > result.getFloor()) ? request : result ;
		}
		return result;
	}
	
	
	/**
	 * @param index
	 * @return
	 */
	public final IRequest getLowerRequestThan(final int index) {
		IRequest result = new DestinationRequest(index);
		for(Iterator<IRequest> iter = requests.iterator() ; iter.hasNext();) {
			IRequest request = iter.next();
			result = (request.getFloor() < result.getFloor()) ? request : result ;
		}
		return result;
	}
	
	
	/** remove all polled request for a specific floor
	 * @param atFloor
	 */
	public final void removeRequestsAt(int atFloor) {
		for(Iterator<IRequest> iter = requests.iterator() ; iter.hasNext();) {
			IRequest request = iter.next();
			if(request.getFloor() == atFloor) {
				iter.remove();
			}
		}
	}

	
	/** check if a floor has polled request
	 * @param atFloor
	 * @return
	 */
	public final boolean isRequestAt(int atFloor) {
		for(Iterator<IRequest> iter = requests.iterator() ; iter.hasNext();) {
			IRequest request = iter.next();
			if(request.getFloor() == atFloor) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * @return
	 */
	public final int getAmountOfRequestsAbove(int index) {
		return getAmountOfRequestsTo(index, Direction.UP);
	}
	
	
	/**
	 * @return
	 */
	public final int getAmountOfRequestsBelow(int index) {
		return getAmountOfRequestsTo(index, Direction.DOWN);
	}

	
	/**
	 * @param direction
	 * @return
	 */
	private final int getAmountOfRequestsTo(int index, Direction direction) {
		int result = 0;
		for(Iterator<IRequest> iter = requests.iterator() ; iter.hasNext();) {
			IRequest request = iter.next();
			if(Direction.UP == direction) {
				if(request.getFloor() > index) {
					result++;
				}
			} else if(Direction.DOWN == direction) {
				if(request.getFloor() < index) {
					result++;
				}
			}
		}
		return result;
	}
}
