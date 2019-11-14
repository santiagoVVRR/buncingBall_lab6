package model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BallTest {
	
	private void setupScenery1() {}
	
	@Test
	void ballTest() {
		setupScenery1();
		double radius = 30.0;
		double posX = 90.0;
		double posY = 180.0;
		int waitT = 20;
		String direction = Ball.LEFT;
		int bounces = 3;
		boolean stop = false;
		Ball spriteTest = new Ball(radius, posX, posY, waitT, direction, bounces, stop);
		
		assertTrue("The radius is not assigned correctly", radius == spriteTest.getRadius());
		assertTrue("The posX is not assigned correctly", posX == spriteTest.getPosX());
		assertTrue("The posY is not assigned correctly", posY == spriteTest.getPosY());
		assertTrue("The wait time is not assigned correctly", waitT == spriteTest.getWaiT());
		assertTrue("The direction is not assigned correctly", direction.equals(spriteTest.getDirection()));
		assertTrue("The bounces are not assigned correctly", bounces == spriteTest.getBounces());
		assertTrue("The state is not assigned correctly", stop == spriteTest.isStop());
		
	}
	
	

}
