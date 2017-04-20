
package ie.gmit.sw.ai;

import java.util.ArrayList;
import java.util.Collections;

import ie.gmit.sw.ai.maze.Maze;
import ie.gmit.sw.ai.traversers.BFS;
import ie.gmit.sw.ai.traversers.Traversator;

public class Monster implements Interact, Runnable{

	private double health;
	private FuzzyFight ffight;
	private double result;
	private double angerLevel;
	private boolean found = false;

	public void setFound(boolean found) {
		this.found = found;
	}

	Traversator t;
	Maze[][] m;
	ArrayList<Maze> path = new ArrayList<Maze>();

	public void setPath(ArrayList<Maze> path) {
		this.path = path;
	}

	private char ch;
	private int x;
	private int y;

	public Monster(double health, double angerLevel, char ch, int x, int y){
		this.health=health;
		this.angerLevel=angerLevel;
		this.ch=ch;
		this.x=x;
		this.y=y;
	}

	public void setHealth(double health)
	{
		this.health=health;
	}

	public double getHealth()
	{
		return health;
	}

	public void setAngerLevel(double angerLevel)
	{
		this.angerLevel=angerLevel;
	}

	public double getAngerLevel()
	{
		return angerLevel;
	}

	public boolean isAlive()
	{
		if(health > 0)return true;
		else return false;

	}

	public double fight(double angerLevel, double weapon) {

		ffight = new FuzzyFight();
		result = Math.round(ffight.getFuzzy(angerLevel, weapon));
		adjustHealth(result);
		return result;
	}

	public void adjustHealth(double damage)
	{
		this.health=health-damage;
	}

	public char getChar()
	{
		return ch;
	}

	public void setPos(int x, int y)
	{
		this.x=x;
		this.y=y;
	}

	public void setMaze(Maze[][] m)
	{
		this.m=m;
	}

	public void run() {		
		t = new BFS();
		//t = new BruteForceTraversator(true);
		//t = new RecursiveDFSTraversator();
		t.traverse(m, m[x][y], this);

		//while (found){
		if(!path.isEmpty() && path != null){		
			Collections.reverse(path);
			path.remove(0);// Takes out the position it currently is in
			for (Maze node :path) {

				try { //Simulate processing each expanded node
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				m[node.getRow()][node.getCol()].setMapItem(this.ch);
				m[x][y].setMapItem(' ');
				this.setPos(node.getRow(),node.getCol());
			}
		}
		//	}
	}
}


