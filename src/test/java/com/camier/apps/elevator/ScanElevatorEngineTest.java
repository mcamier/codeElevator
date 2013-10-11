package com.camier.apps.elevator;

import junit.framework.Assert;

import org.junit.Test;

public class ScanElevatorEngineTest {
	
	public ScanElevatorEngineTest() {
	// TODO Auto-generated constructor stub
	}
	
	@Test
	public void elevatorMustGoUpAfterUniqueCallingTest() {
		ScanElevatorEngine elevator = new ScanElevatorEngine(6);
		elevator.setCurrentFloor(0);
		elevator.call(new Call(5, Direction.DOWN));
		
		Assert.assertEquals(Command.UP, elevator.getNextCommand());
	}
	
	@Test
	public void elevatorMustDownUpUniqueCallingTest() {
		ScanElevatorEngine elevator = new ScanElevatorEngine(6);
		elevator.setCurrentFloor(5);
		elevator.call(new Call(1, Direction.UP));
		
		Assert.assertEquals(Command.DOWN, elevator.getNextCommand());
	}
	
	@Test
	public void elevatorEngineReturnsOpenWhenOpen() {
		ScanElevatorEngine elevator = new ScanElevatorEngine(6);
		
		Command result = elevator.openDoor();
		Assert.assertEquals(Command.OPEN, result);
		Assert.assertEquals(elevator.isDoorOpened(), true);
	}
}
