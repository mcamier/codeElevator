package com.camier.apps.elevator.engine;


import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import com.mcamier.apps.elevator.engine.ScanElevatorEngine;
import com.mcamier.apps.elevator.request.CallRequest;
import com.mcamier.apps.elevator.utils.Command;
import com.mcamier.apps.elevator.utils.Direction;

public class ScanElevatorEngineTest {
	
	public ScanElevatorEngineTest() {
	// TODO Auto-generated constructor stub
	}
	
	@Test
	public void mustGoUpAfterUniqueCallingTest() {
		ScanElevatorEngine elevator = new ScanElevatorEngine(6,999);
		elevator.setCurrentFloor(0);
		elevator.closeDoor();
		elevator.call(new CallRequest(5, Direction.DOWN));
		
		assertThat(elevator.getNextCommand()).isEqualTo(Command.UP);
	}
	
	@Test
	public void mustDownUpUniqueCallingTest() {
		ScanElevatorEngine elevator = new ScanElevatorEngine(6,999);
		elevator.setCurrentFloor(5);
		elevator.closeDoor();
		elevator.call(new CallRequest(1, Direction.UP));
		
		assertThat(elevator.getNextCommand()).isEqualTo(Command.DOWN);
	}
	
	@Test
	public void returnsCommandOpenWhenOpenOrderTest() {
		ScanElevatorEngine elevator = new ScanElevatorEngine(6, 999);
		
		assertThat(elevator.openDoor()).isEqualTo(Command.OPEN);
		assertThat(elevator.isDoorOpened()).isTrue();
	}
	
	@Test
	public void returnsCommandCloseWhenCloseOrderTest() {
		ScanElevatorEngine elevator = new ScanElevatorEngine(6, 999);
		elevator.openDoor();
		
		Command result = elevator.closeDoor();
		assertThat(elevator.closeDoor()).isEqualTo(Command.CLOSE);
		assertThat(elevator.isDoorOpened()).isFalse();
	}
	
	@Test
	public void haveToCloseDoorBeforeGoUpTest() {
		ScanElevatorEngine elevator = new ScanElevatorEngine(6, 999);
		elevator.openDoor();
		
		assertThat(elevator.goUp()).isEqualTo(Command.CLOSE);
	}
	
	@Test
	public void haveToCloseDoorBeforeGoDownTest() {
		ScanElevatorEngine elevator = new ScanElevatorEngine(6, 999);
		elevator.openDoor();
		
		assertThat(elevator.goDown()).isEqualTo(Command.CLOSE);
	}
}
