package model;

public class Ball {

	public static final String LEFT = "LEFT";
	public static final String RIGHT = "RIGHT";
	public static final String DOWN = "DOWN";
	public static final String UP = "UP";
	
	private double radius;
	private double posX;
	private double posY;
	private int waiT;
	private String direction;
	private int bounces;
	private boolean stop;
	
	public Ball(double radius, double posX, double posY, int waiT, String direction, int bounces, boolean stop) {
		
		this.radius = radius;
		this.posX = posX;
		this.posY = posY;
		this.waiT = waiT;
		this.direction = direction;
		this.bounces = bounces;
		this.stop = stop;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public int getWaiT() {
		return waiT;
	}

	public void setWaiT(int waiT) {
		this.waiT = waiT;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getBounces() {
		return bounces;
	}

	public void setBounces(int bounces) {
		this.bounces = bounces;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}
	
	
	
	
}
