/**
 * 
 */
package com.camier.apps.elevator.request;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import com.mcamier.apps.elevator.request.DestinationRequest;
import com.mcamier.apps.elevator.request.IRequest;
import com.mcamier.apps.elevator.utils.Direction;

/**
 * @author fufuuu
 *
 */
public class RequestTest {
	
	IRequest request;
	
	public RequestTest() {
		// TODO Auto-generated constructor stub
	}

	@Test 
	public void getFloorTest() {
		int floor = 4;
		request = new DestinationRequest(floor);
		assertThat(request.getFloor()).isEqualTo(floor);
	}
	
	@Test
	public void directionFromLowestFloorTest()
	{
		request = new DestinationRequest(4);
		assertThat(request.directionFrom(0)).isEqualTo(Direction.UP);
	}

	@Test
	public void directionFromHighestFloorTest()
	{
		request = new DestinationRequest(0);
		assertThat(request.directionFrom(4)).isEqualTo(Direction.DOWN);
	}
	
	@Test
	public void directionFromSameFloorTest()
	{
		request = new DestinationRequest(4);
		assertThat(request.directionFrom(4)).isEqualTo(Direction.NONE);
	}
	
	@Test
	public void isReachableFloorsWithDirectionUpTest()
	{
		request = new DestinationRequest(3);
		boolean expectedTrue = request.isReachableWithDirectionFromPosition(Direction.UP, 0);
		assertThat(expectedTrue).isTrue();
	}
	
	@Test
	public void isReachableFloorsWithDirectionDownTest()
	{
		request = new DestinationRequest(3);
		boolean expectedTrue = request.isReachableWithDirectionFromPosition(Direction.DOWN, 5);
		assertThat(expectedTrue).isTrue();
	}
	
	@Test
	public void isNotReachableFloorsWithDirectionUpTest()
	{
		request = new DestinationRequest(3);
		boolean expectedFalse = request.isReachableWithDirectionFromPosition(Direction.UP, 5);
		assertThat(expectedFalse).isFalse();
	}
	
	@Test
	public void isNotReachableFloorsWithDirectionDownTest()
	{
		request = new DestinationRequest(3);
		boolean expectedFalse = request.isReachableWithDirectionFromPosition(Direction.DOWN, 0);
		assertThat(expectedFalse).isFalse();
	}
}
