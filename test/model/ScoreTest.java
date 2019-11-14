package model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ScoreTest {

	
	private Score myScore;
	
	private void setupScenery1() {}
	
	private void setupScenery2() {
		myScore = new Score("Yuji", 10);
	}
	
	
	@Test
	void scoreTest() {
		setupScenery1();
		String name = "Cesarin";
		int score = 19;
		Score testScore = new Score(name, score);
		
		assertTrue("The name is not assigned correctly", name.equals(testScore.getName()));
		assertTrue("The size is not assigned correctly", score == testScore.getScore());
	}
	
	
	@Test
	void compareToTest() {
		setupScenery2();
		String name = "Cesarin";
		int score = 19;
		Score testScore = new Score(name, score);
		assertTrue("The sorting is not being done correctly", myScore.compareTo(testScore) < 0);
	}
	
	

}
