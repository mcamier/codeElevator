package com.camier.apps.elevator.engine;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import com.mcamier.apps.elevator.engine.SmarterElevatorEngine;
import com.mcamier.apps.elevator.request.CallRequest;
import com.mcamier.apps.elevator.request.DestinationRequest;
import com.mcamier.apps.elevator.utils.Command;
import com.mcamier.apps.elevator.utils.Direction;

public class SmarterElevatorEngineTest {

	private SmarterElevatorEngine engine;
	
	public SmarterElevatorEngineTest() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Test
	public void computeSimplePriorityTest() {
		engine = new SmarterElevatorEngine(6);
		engine.setCurrentFloor(1);
		engine.call(new CallRequest(0, Direction.UP));
		engine.haveToGoTo(new DestinationRequest(5));
		
		assertThat(engine.getNextCommand()).isEqualTo(Command.DOWN);
	}
	
	
	@Test
	public void SimpleTravelTest() {
		engine = new SmarterElevatorEngine(6);
		engine.setCurrentFloor(0);
		engine.call(new CallRequest(2, Direction.UP));
		assertThat(engine.getNextCommand()).isEqualTo(Command.UP);
		assertThat(engine.getNextCommand()).isEqualTo(Command.UP);
		assertThat(engine.getNextCommand()).isEqualTo(Command.OPEN);
		assertThat(engine.getNextCommand()).isEqualTo(Command.CLOSE);
		engine.haveToGoTo(new DestinationRequest(1));
		assertThat(engine.getNextCommand()).isEqualTo(Command.DOWN);
		assertThat(engine.getNextCommand()).isEqualTo(Command.OPEN);
		assertThat(engine.getNextCommand()).isEqualTo(Command.CLOSE);
	}
	
	
	@Test
	public void computeHarderPriorityTest() {
		engine = new SmarterElevatorEngine(6);
		engine.setCurrentFloor(2);
		engine.call(new CallRequest(1, Direction.UP));
		engine.call(new CallRequest(4, Direction.DOWN));
		engine.haveToGoTo(new DestinationRequest(4));
		engine.haveToGoTo(new DestinationRequest(5));
		
		assertThat(engine.getNextCommand()).isEqualTo(Command.DOWN);
	}
}
