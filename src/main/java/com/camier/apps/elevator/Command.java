/**
 * 
 */
package com.camier.apps.elevator;

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