package threads;

import model.Ball;
import application.ControllerClass;
import javafx.application.Platform;
import javafx.scene.shape.Circle;

public class BallThread extends Thread{

	private Ball ball;
	private ControllerClass cs;
	private Circle circle;
	private boolean active;
	
	public BallThread(ControllerClass cs, Circle circle,  Ball ball) {
		
		this.ball = ball;
		this.cs = cs;
		this.circle = circle;
		active = !ball.isStop();
	}
	
	public void deactivate() {
		active = false;
	}

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}
	
	public boolean isActive() {
		return active;
	}

	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}
	
	@Override
	public void run() {
		while(active) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					cs.moveBall(circle, ball);
				}
			});
			
			try {
				sleep(ball.getWaiT());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(!cs.getGameBackGround().getChildren().contains(circle)) {
				ball.setStop(true);
				deactivate();
			}
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					if(cs.getGame().allStop())
						cs.mainGame();
				}
			});
			
		}
	}
	
}
