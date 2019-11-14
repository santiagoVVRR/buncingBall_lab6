package model;

import java.io.Serializable;

public class Score implements Comparable<Score>, Serializable{

	private String name;
	private int score;
	
	public Score(String name, int score) {
		
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}



	@Override
	public int compareTo(Score arg0) {
		return ( this.score - arg0.getScore());
	}
	
	

}
