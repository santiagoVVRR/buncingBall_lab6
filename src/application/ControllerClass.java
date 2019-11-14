package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Ball;
import model.Game;

import threads.*;

public class ControllerClass {

	/**
	    * The main stage handled by the controller.
	    */
	private Stage stage;
	
	/**
	 * The gridPane that shows the players top scores.
	 */
	private GridPane scoreGrid;
	
	/**
	 * The association between the controller and the Game that is being displayed.
	 */
	private Game mainGame;
	
	 /**
     * A list of BallThread which is used to deactivate them all in the end.
     */
	private List<BallThread> threads;
	
	
	//FXML ATRIBBUTES
	
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuBar menuOptions;

    @FXML
    private Label bounceCounter;

    @FXML
    private Label levelLabel;

    @FXML
    private Pane gameBackGround;

    
    /**
     * This function is triggered when the user clicks the level one menu option. It loads the game's level one.
     * @param event The event that triggered this function to be called.
     * @throws IOException When the file containing the level is not found.
     */
    @FXML
    void levelOne(ActionEvent event) throws IOException {
    	bounceCounter.setText("Bounces: 0");
    	gameBackGround.getChildren().clear();
    	mainGame.getSprites().clear();
    	mainGame.loadGame("data/Level1.txt");
    	levelLabel.setText("Level " + mainGame.getLevel());
    	startGame();
    }

    
    /**
     * This function is triggered when the user clicks the level two menu option. It loads the game's level two.
     * @param event The event that triggered this function to be called.
     * @throws IOException When the file containing the level is not found.
     */
    @FXML
    void levelTwo(ActionEvent event) throws IOException {
    	bounceCounter.setText("Bounces: 0");
    	gameBackGround.getChildren().clear();
    	mainGame.getSprites().clear();
    	mainGame.loadGame("data/Level2.txt");
    	levelLabel.setText("Level " + mainGame.getLevel());
    	startGame();
    }

    
    /**
     * This function is triggered when the user clicks the level zero menu option. It loads the game's level zero.
     * @param event The event that triggered this function to be called.
     * @throws IOException When the file containing the level is not found.
     */
    @FXML
    void levelZero(ActionEvent event) throws IOException {
    	bounceCounter.setText("Bounces: 0");
    	gameBackGround.getChildren().clear();
    	mainGame.getSprites().clear();
    	mainGame.loadGame("data/Level0.txt");
    	levelLabel.setText("Level " + mainGame.getLevel());
    	startGame();
    }

    
    /**
     * This function is triggered when the user clicks the saved game menu option. It loads the previously saved game or nothing if there is no saved game.
     * @param event The event that triggered this function to be called.
     * @throws IOException When the file containing the level is not found.
     */
    @FXML
    void savedGame(ActionEvent event) throws IOException {
    	bounceCounter.setText("Bounces: 0");
    	gameBackGround.getChildren().clear();
    	mainGame.getSprites().clear();
    	mainGame.loadGame("Data/savedGame.txt");
    	levelLabel.setText("Level " + mainGame.getLevel());
    	startGame();
    }
    
    
    /**
     * This function deactivates all the threads currently running. It's called when the user exits the game or clicks the "X" on the top right corner.
     * @throws InterruptedException if any thread has interrupted the current thread.
     */
    @FXML
    public void onCloseRequest() throws InterruptedException {
    	for(int i = 0; i < threads.size(); i++) {
    		threads.get(i).deactivate();
    		threads.get(i).join();
    	}
    	System.out.println("threads out");
    }

    @FXML
    /**
     * This function initializes the Game and adds event handlers for the different menu options.
     */
    void initialize() {
    	scoreGrid = null;
    	threads = new ArrayList<BallThread>();
    
    	try {
			mainGame = new Game();
		} catch (ClassNotFoundException | IOException e2) {
			e2.printStackTrace();
		}
    	int counter = 1;
    	for(int i = 0; i < menuOptions.getMenus().size(); i++) {
    		for(int j = 0 + counter; j < menuOptions.getMenus().get(i).getItems().size();j++) {
    			String a = menuOptions.getMenus().get(i).getItems().get(j).getText();
    			menuOptions.getMenus().get(i).getItems().get(j).setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent e) {
						if(a.equals("Exit game")) {
							try {
								onCloseRequest();
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
							System.exit(0);
						}
						else if(a.equals("Save game")) {
							try {
								mainGame.saveGame("Data/savedGame.txt");
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						else if(a.equals("Hall of fame")) {
							showHallOfFame();
						}
						else if(a.equals("About the game")) {
							aboutTheGame();
						}
					}
    				
    			});
    		}
    		counter--;	
    	}

    }
    
    /**
     * This function is called when all the balls have been caught and it is responsible of interacting with the user in order to save scores and continue to the next level if there is one.
     */
    public void mainGame() {
    	gameBackGround.getChildren().clear();
    	TextField nameTF = new TextField();
		nameTF.setPromptText("Enter your name");
		nameTF.setLayoutX(300);
		nameTF.setLayoutY(300);
		Button submit = new Button("Submit score");
		submit.setLayoutX(300);
		submit.setLayoutY(350);
		submit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				int bounces = Integer.parseInt(bounceCounter.getText().split(" ")[1]);
				try {
					mainGame.addScore(nameTF.getText(), bounces);
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				gameBackGround.getChildren().remove(nameTF);
				gameBackGround.getChildren().remove(submit);
				showHallOfFame();
				Button continuee = new Button("Continue");
				continuee.setMaxWidth(500);
				continuee.setMaxHeight(500);
				continuee.setLayoutX(900);
				continuee.setLayoutY(450);
				continuee.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						if(mainGame.getLevel() == 0) {
							try {
								levelOne(null);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						else if(mainGame.getLevel() == 1) {
							try {
								levelTwo(null);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						else {
							
						}
					}
					
				});
				gameBackGround.getChildren().add(continuee);
			}
			
		});
		gameBackGround.getChildren().add(nameTF);
		gameBackGround.getChildren().add(submit);
    }
    
    /**
	 * This function is responsible of moving the ball depending on it's direction. It handles the moment when the ball bounces with the walls or with other balls.
	 * @param circle The arc representing the PacMan.
	 * @param b The Ball that is associated with this specific PacMan.
	 */
    public void moveBall(Circle circle, Ball b) {
    	if(b.getDirection().equals(Ball.LEFT)) {
    		circle.setCenterX(circle.getCenterX()-5);
    		b.setPosX(circle.getCenterX());
    		if(circle.getCenterX() - circle.getRadius() <= 0|| ballColission(circle)) {
    			circle.setRotate(circle.getRotate() + 180);
    			int times = Integer.parseInt(bounceCounter.getText().split(" ")[1]) + 1;
    			bounceCounter.setText("Bounces: " + times);
    			b.setBounces(b.getBounces() + 1);
    			b.setDirection(Ball.RIGHT);
    			circle.setCenterX(circle.getCenterX() + 5);
    		}
    	}else if(b.getDirection().equals(Ball.RIGHT)) {
    		circle.setCenterX(circle.getCenterX()+5);
    		b.setPosX(circle.getCenterX());
    		if(circle.getCenterX() >= (stage.getScene().getWidth() - circle.getRadius()) || ballColission(circle)) {
    			circle.setRotate(circle.getRotate() -180);
    			int times = Integer.parseInt(bounceCounter.getText().split(" ")[1]) + 1;
    			bounceCounter.setText("Bounces: " + times);
    			b.setBounces(b.getBounces() + 1);
    			b.setDirection(Ball.LEFT);
    			circle.setCenterX(circle.getCenterX() - 5);
    		}
    	}else if(b.getDirection().equals(Ball.DOWN)) {
    		circle.setCenterY(circle.getCenterY() + 5);
    		b.setPosY(circle.getCenterY());
    		if(circle.getCenterY() >= (stage.getScene().getHeight() - (circle.getRadius()*2)) || ballColission(circle)) {
    			circle.setRotate(circle.getRotate() +180);
    			int times = Integer.parseInt(bounceCounter.getText().split(" ")[1]) + 1;
    			bounceCounter.setText("Bounces: " + times);
    			b.setBounces(b.getBounces()+1);
    			b.setDirection(Ball.UP);
    			circle.setCenterY(circle.getCenterY() - 5);
    		}
    	}else {
    		circle.setCenterY(circle.getCenterY() -5);
    		b.setPosY(circle.getCenterY());
    		if(circle.getCenterY() - circle.getRadius() <= 0 || ballColission(circle)) {
    			circle.setRotate(circle.getRotate()-180);
    			int times = Integer.parseInt(bounceCounter.getText().split(" ")[1]) + 1;
    			bounceCounter.setText("Bounces: " + times);
    			b.setBounces(b.getBounces()+1);
    			b.setDirection(Ball.DOWN);
    			circle.setCenterY(circle.getCenterY() + 5);
    		}
    	}
    	
    }
    
    
    /**
	 * This function tells whether or not this ball is touching another one anywhere on the screen. If it is, it changes the direction of the other ball that is touching and returns true.
	 * @param c The arc representing the ball.
	 * @return A boolean representing whether or not this ball is touching another ball.
	 */
    public boolean ballColission(Circle c) {
    	boolean exit = false;
    	for(int i = 0; i < threads.size(); i++) {
    		Circle ball = threads.get(i).getCircle();
    		double cx1 = ball.getCenterX();
    		double cy1 = ball.getCenterY();
    		double r1 = ball.getRadius();
    		double cx2 = c.getCenterX();
    		double cy2 = c.getCenterY();
    		double r2 = c.getRadius();
    		double distance = Math.sqrt((cx1 - cx2) * (cx1 - cx2) + (cy1 - cy2) * (cy1 - cy2));
    		if(distance <= r1+r2 && threads.get(i).isActive() && !ball.equals(c)) {
    			if(threads.get(i).getBall().getDirection().equals(Ball.LEFT)) {
    				ball.setRotate(ball.getRotate() + 180);
    				threads.get(i).getBall().setDirection(Ball.RIGHT);
    				ball.setCenterX(ball.getCenterX() + 5);
    			}else if(threads.get(i).getBall().getDirection().equals(Ball.RIGHT)) {
    				ball.setRotate(ball.getRotate() -180);
    				threads.get(i).getBall().setDirection(Ball.LEFT);
    				ball.setCenterX(ball.getCenterX() - 5);
    			}else if(threads.get(i).getBall().getDirection().equals(Ball.DOWN)) {
    				ball.setRotate(ball.getRotate() + 180);
    				threads.get(i).getBall().setDirection(Ball.UP);
    				ball.setCenterY(ball.getCenterY() - 5);
    			}else if(threads.get(i).getBall().getDirection().equals(Ball.UP)) {
    				ball.setRotate(ball.getRotate() - 180);
    				threads.get(i).getBall().setDirection(Ball.DOWN);
    				ball.setCenterY(ball.getCenterY() + 5);
    			}
    			exit = true;
    		}
    	}
    	return exit;
    }
   
    /**
     * This function is called after the game has been loaded and it is responsible of creating the graphical user interface components to show the ball on screen.
     * It also creates a BallThread for each of the balls.
     */
    public void startGame() {
    	for(int i = 0;i < mainGame.getSprites().size(); i++) {
    		Ball b = mainGame.getSprites().get(i);
    		if(!b.isStop()) {
    			Circle ball = new Circle(b.getPosX(), b.getPosY(), b.getRadius());
    			ball.setFill(Color.BROWN);
    			if(b.getDirection().equals(Ball.DOWN)) {
    				ball.setRotate(-270);
    			}else if(b.getDirection().equals(Ball.UP)) {
    				ball.setRotate(-90);
    			}else if(b.getDirection().equals(Ball.LEFT)) {
    				ball.setRotate(-180);
    			}
    			gameBackGround.getChildren().add(ball);
    			ball.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						gameBackGround.getChildren().remove(ball);
						
					}
    				
    			});
    			BallThread bt = new BallThread(this, ball, b);
    			threads.add(bt);
    			bt.start();
    			
    		}
    		int times = Integer.parseInt(bounceCounter.getText().split(" ")[1]) + b.getBounces();
    		bounceCounter.setText("Bounces: " + times);
    	}
    }
    
    
    /**
     * This function fills the score grid and shows it, represents the top scores in the game.
     */
    public void showHallOfFame() {
    	GridPane grid = new GridPane();
    	String[][] hall = mainGame.getHallOfFame();
    	for(int i = 0; i < hall.length; i++) {
    		for(int j = 0; j < hall.length; j++) {
    			Label l = new Label(hall[i][j]);
    			grid.add(l, j+1, i);
    			GridPane.setHalignment(l, HPos.CENTER);
    		}
    	}
    	for(int j = 0; j < 10; j++) {
    		Label l = new Label(String.valueOf(j+1));
    		grid.add(l, 0, j);
    		grid.setHalignment(l, HPos.CENTER);
    	}
    	ColumnConstraints column = new ColumnConstraints();
    	column.setPercentWidth(30);
    	grid.getColumnConstraints().add(column);
    	
    	column = new ColumnConstraints();
    	column.setPercentWidth(30);
    	grid.getColumnConstraints().add(column);
    	grid.getColumnConstraints().add(column);
    	
    	grid.setMaxHeight(500);
    	grid.setPrefWidth(500);
    	grid.setAlignment(Pos.CENTER);
    	grid.setGridLinesVisible(true);
    	grid.setLayoutX(700);
    	grid.setLayoutY(175);
    	grid.getStyleClass().add("mygridStyle");
    	Button hide = new Button("Hide");
    	hide.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				gameBackGround.getChildren().remove(grid);
				
			}
    		
    	});
    	grid.add(hide, 1, 10);
    	GridPane.setHalignment(hide, HPos.CENTER);
    	if(scoreGrid != null) {
    		gameBackGround.getChildren().remove(scoreGrid);
    	}
    	gameBackGround.getChildren().add(grid);
    	scoreGrid = grid;
    }
    
    private void aboutTheGame() {
    	Alert info = new Alert(AlertType.INFORMATION);
		info.setTitle("ABOUT THE GAME");
		info.setHeaderText(null);
		info.initStyle(StageStyle.UTILITY);
		info.setContentText("el objetivo del juego es atrapar todos los circulos "
				+ "utilizando el Mouse");
		info.show();
    }
    
    
    /**
     * This function obtains the main game background.
	 * @return The main game background.
	 */
    public Pane getGameBackGround() {
		return gameBackGround;
	}
    
    
    /**
	 * This function obtains the main game that is currently being played.
	 * @return The main game that is currently being played.
	 */
    public Game getGame() {
		return mainGame;
	}
    
    
    /**
	 * This function modifies the controller's stage.
	 * @param stage The new stage.
	 */
    public void setStage(Stage stage) {
		this.stage = stage;
	}
}
