package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

	private static final String PATH_SCORE = "";
	
	private List<Ball> balls;
	private List<Score> hallFame;
	private int level;
	
	public Game() throws IOException, ClassNotFoundException {
		
		this.balls = new ArrayList<Ball>();
		loadScore();
	}
	
	private void saveScore() throws IOException {
		File f = new File(PATH_SCORE);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
		oos.writeObject(hallFame);
		oos.close();
	}
	
	
	private void loadScore() throws IOException, ClassNotFoundException {
		File f = new File(PATH_SCORE);
		if(f.exists()) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			hallFame = (ArrayList<Score>) ois.readObject();
			ois.close();
		}else {
			hallFame = new ArrayList<Score>();
		}
	}
	
	public void loadGame(String path) throws IOException {
		File fl = new File(path);
		FileReader fr = new FileReader(fl);
		BufferedReader in = new BufferedReader(fr);
		in.readLine();
		level = Integer.parseInt(in.readLine());
		in.readLine();
		String s = in.readLine();
		
		while(s!=null) {
			String[] arr = s.split(" ");
			double radius = Double.parseDouble(arr[0]);
			double posX = Double.parseDouble(arr[1]);
			double posY = Double.parseDouble(arr[2]);
			int waitT = Integer.parseInt(arr[3]);
			String direction = arr[4];
			int bounces = Integer.parseInt(arr[5]);
			boolean stopped = Boolean.parseBoolean(arr[6]);
			Ball ps = new Ball(radius, posX, posY, waitT, direction, bounces, stopped);
			balls.add(ps);
			s = in.readLine();
		}
		fr.close();
		in.close();
	}
	
	public void saveGame(String path) throws IOException {
		PrintWriter pw = new PrintWriter(path);
		pw.print("#Level\n" + level + "\n#radius posX posY wait direction bounces stopped\n");
		for(int i = 0; i < balls.size(); i++) {
			Ball ps = balls.get(i);
			pw.println(ps.getRadius() + " " + ps.getPosX() + " " + ps.getPosY() + " " + ps.getWaiT() + " " + ps.getDirection() + " " + ps.getBounces() + " " + ps.isStop());
		}
		pw.close();
	}
	
	
	public void addScore(String name, int score) throws IOException {
		Score sc = new Score(name, score);
		if(hallFame.size() < 10) {
			hallFame.add(sc);
			Collections.sort(hallFame);
		}
		else {
			hallFame.add(sc);
			Collections.sort(hallFame);
			hallFame.remove(10);
		}
		saveScore();
	}
	
	public boolean allStop() {
		boolean exit = true;
		for(int i = 0; i < balls.size() && exit; i++) {
			if(!balls.get(i).isStop())
				exit = false;
		}
		return exit;
	}
	
	public List<Ball> getSprites() {
		return balls;
	}
	
	public String[][] getHallOfFame() {
		String[][] hall = new String[10][2];
		for(int i = 0; i < 10;i++) {
			hall[i][0] = "Empty";
			hall[i][1] = "Empty";
		}
		for(int i = 0; i < hallFame.size();i++) {
			hall[i][0] = hallFame.get(i).getName();
			hall[i][1] = hallFame.get(i).getScore() + "";
		}
		
		return hall;
	}
	
	
	public int getLevel() {
		return level;
	}
	
}

