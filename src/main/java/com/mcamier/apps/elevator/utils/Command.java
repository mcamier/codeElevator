/**
 * 
 */
package com.mcamier.apps.elevator.utils;

/**
 * @author fufuuu
 *
 */
public enum Command {
	NOTHING, UP, DOWN, OPEN, CLOSE;

	public String toString() {
		return name().toString();
	}
}