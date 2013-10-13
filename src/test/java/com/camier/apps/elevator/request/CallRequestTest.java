/**
 * 
 */
package com.camier.apps.elevator.request;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import com.mcamier.apps.elevator.request.CallRequest;
import com.mcamier.apps.elevator.utils.Direction;

/**
 * @author fufuuu
 *
 */
public class CallRequestTest {

	CallRequest request;
	
	public CallRequestTest() {
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void getDirectionTest() {
		request = new CallRequest(5, Direction.DOWN);
		assertThat(request.getDirection()).isEqualTo(Direction.DOWN);
	}
	
	@Test
	public void getRawPriorityTest() {
		request = new CallRequest(5, Direction.DOWN);
		assertThat(request.getRawPriority()).isEqualTo(2);
	}
}
