
package ie.gmit.sw.ai;

import java.util.ArrayList;
import java.util.Collections;

import ie.gmit.sw.ai.maze.Maze;
import ie.gmit.sw.ai.traversers.BFS;
import ie.gmit.sw.ai.traversers.BruteForceTraversator;
import ie.gmit.sw.ai.traversers.RecursiveDFSTraversator;
import ie.gmit.sw.ai.traversers.Traversator;

public class Monster implements Interact, Runnable{

	private double health;
	private FuzzyFight ffight;
	private double result;
	private double angerLevel;
	private boolean found = false;
	private int damage;
	private Traversator t;
	private Maze[][] mazeCopy;
	private Maze[][] mainMaze;
	private ArrayList<Maze> path = new ArrayList<Maze>();
	private char ch;
	private int x;
	private int y;

	public Monster(double health, double angerLevel, char ch, int x, int y, Maze[][] maze){
		this.health=health;
		this.angerLevel=angerLevel;
		this.ch=ch;
		this.x=x;
		this.y=y;
		this.mainMaze = maze;
	}


	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void setFound(boolean found) {
		this.found = found;
	}

	public boolean isFound() {
		return found;
	}

	public void setPath(ArrayList<Maze> path) {
		this.path = path;
	}

	public ArrayList<Maze> getPath() {
		Collections.reverse(path);
		path.remove(0);// Takes out the position it currently is in
		return path;
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

	public double fight(double angerLevel, double damage) {

		ffight = new FuzzyFight();
		result = Math.round(ffight.getFuzzy(angerLevel, damage));
		adjustHealth(result);
		System.out.println("Monster: "+Thread.currentThread().getName()+" Health Remaining: " + this.health + " Damage: "+ damage);
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
		this.mazeCopy=m;
	}

	public void run() {	
		System.out.println("Starting: "+ Thread.currentThread().getName());
		//if(Thread.currentThread().getName() == "Spider 1")
		t = new BFS();		
		//t = new BruteForceTraversator(true);
		//else
		//t = new RecursiveDFSTraversator();

		t.traverse(mazeCopy, mazeCopy[x][y], this);

		if (found){	
			Collections.reverse(path);
			path.remove(0);// Takes out the position it currently is in
			for (Maze node :path) {

				try { //Simulate processing each expanded node
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				mainMaze[node.getRow()][node.getCol()].setMapItem(this.ch);
				mainMaze[x][y].setMapItem(' ');
				this.setPos(node.getRow(),node.getCol());
			}
			fight(this.angerLevel, 5);
		}
	}
}


