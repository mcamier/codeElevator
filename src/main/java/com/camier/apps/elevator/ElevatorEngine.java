/**
 * 
 */
package com.camier.apps.elevator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fufuuu
 *
 */
public class ElevatorEngine 
	implements IElevatorEngine {

	private List<Call> callPool;
	
	public ElevatorEngine() {
		this.callPool = new ArrayList<Call>();
	}
	
	public void callElevator(int fromFloor, Direction toGo) {
		this.callPool.add(new Call(fromFloor, toGo));
	}
	
	public void callElevator(Call call) {
		this.callPool.add(call);
	}
}
