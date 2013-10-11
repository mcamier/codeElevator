package com.camier.apps.elevator;

import junit.framework.Assert;

import org.junit.Test;

public class ScanElevatorEngineTest {
	
	public ScanElevatorEngineTest() {
	// TODO Auto-generated constructor stub
	}
	
	@Test
	public void mustGoUpAfterUniqueCallingTest() {
		ScanElevatorEngine elevator = new ScanElevatorEngine(6);
		elevator.setCurrentFloor(0);
		elevator.call(new Call(5, Direction.DOWN));
		
		Assert.assertEquals(Command.UP, elevator.getNextCommand());
	}
	
	@Test
	public void mustDownUpUniqueCallingTest() {
		ScanElevatorEngine elevator = new ScanElevatorEngine(6);
		elevator.setCurrentFloor(5);
		elevator.call(new Call(1, Direction.UP));
		
		Assert.assertEquals(Command.DOWN, elevator.getNextCommand());
	}
	
	@Test
	public void returnsCommandOpenWhenOpenOrderTest() {
		ScanElevatorEngine elevator = new ScanElevatorEngine(6);
		
		Command result = elevator.openDoor();
		Assert.assertEquals(Command.OPEN, result);
		Assert.assertEquals(elevator.isDoorOpened(), true);
	}
	
	@Test
	public void returnsCommandCloseWhenCloseOrderTest() {
		ScanElevatorEngine elevator = new ScanElevatorEngine(6);
		
		Command result = elevator.closeDoor();
		Assert.assertEquals(Command.CLOSE, result);
		Assert.assertEquals(elevator.isDoorOpened(), false);
	}
	
	@Test
	public void haveToCloseDoorBeforeGoUpTest() {
		ScanElevatorEngine elevator = new ScanElevatorEngine(6);
		elevator.openDoor();
		
		Command result = elevator.goUp();
		Assert.assertEquals(Command.CLOSE, result);
	}
	
	@Test
	public void haveToCloseDoorBeforeGoDownTest() {
		ScanElevatorEngine elevator = new ScanElevatorEngine(6);
		elevator.openDoor();
		
		Command result = elevator.goDown();
		Assert.assertEquals(Command.CLOSE, result);
	}
}
