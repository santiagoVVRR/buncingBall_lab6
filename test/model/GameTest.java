package model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class GameTest {

	/**
	 * The association between the game and it's test.
	 */
	private Game myGame;
	
	/**
	 * This function initializes a scenery.
	 */
	private void setupScenery1() {}
	
	/**
	 * This function initializes a scenery.
	 */
	private void setupScenery2() {
		try {
			myGame = new Game();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This function tests whether or not the PacManSprite constructor functions correctly.
	 */
	@Test
	void gameTest() {
		setupScenery1();
		Game gameTest = null;
		try {
			gameTest = new Game();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertNotNull("The list is null", gameTest.getSprites()!=null);
		assertNotNull("The list is null", gameTest.getHallOfFame()!=null);
	}

	/**
	 * This function tests whether or not the game is being loaded correctly.
	 */
	@Test
	void loadGameTest() {
		setupScenery2();
		String camino = "Data/Level1.txt";
		try {
			myGame.loadGame(camino);
		} catch (IOException e) {
			fail("The game wasn't loaded");
		}
		assertTrue("The list doesn't have a size greater than 0", myGame.getSprites().size()>0);
	}
	
	/**
	 * This function tests whether or not the game is being saved correctly.
	 */
	@Test
	void saveGameTest() {
		setupScenery2();
		String camino = "Data/savedGame.txt";
		try {
			myGame.loadGame(camino);
		} catch (IOException e) {
			fail("The game wasn't loaded");
		}
		File fl = new File(camino);
		assertTrue("The file exists", fl.exists());
	}
	
	/**
	 * This function tests whether or not an exception is thrown when it's supposed to
	 */
	@Test
	void exceptionTest1() {
		setupScenery2();
		String camino = "Dataa/MiCaminito.txt";
		try {
			myGame.loadGame(camino);
			fail("The exception wasn't thrown");
		} catch (IOException e) {
			assertTrue(true);
		}
	}
	
	/**
	 * This function tests whether or not an exception is thrown when it's supposed to
	 */
	@Test
	void exceptionTest2() {
		setupScenery2();
		String camino = "Data/Level1.txt";
		try {
			myGame.loadGame(camino);
			assertTrue(true);
		} catch (IOException e) {
			fail("The exception was thrown");
		}
	}
	
	/**
	 * This function tests whether or not an exception is thrown when it's supposed to
	 */
	@Test
	void exceptionTest3() {
		setupScenery2();
		String camino = "Dataa/MiCaminito.txt";
		try {
			myGame.saveGame(camino);
			fail("The exception wasn't thrown");
		} catch (IOException e) {
			assertTrue(true);
		}
	}
	
	/**
	 * This function tests whether or not an exception is thrown when it's supposed to
	 */
	@Test
	void exceptionTest4() {
		setupScenery2();
		String camino = "Data/savedGame.txt";
		try {
			myGame.saveGame(camino);
			assertTrue(true);
		} catch (IOException e) {
			fail("The exception was thrown");
		}
	}
	
	/**
	 * This function tests whether or not a score is added correctly.
	 */
	@Test
	void addScoreTest() {
		setupScenery2();
		String name = "kiko";
		int score = 9;
		try {
			myGame.addScore(name, score);
		} catch (IOException e) {
			fail("An exception was thrown");
		}
		
		assertTrue("The list doesn't have size 1", myGame.getHallOfFame()[0][0].equals(name) && myGame.getHallOfFame()[0][1].equals(score + ""));
	}
	

}
