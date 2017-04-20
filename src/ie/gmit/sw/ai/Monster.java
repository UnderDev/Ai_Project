
package ie.gmit.sw.ai;

import ie.gmit.sw.ai.maze.Maze;
import ie.gmit.sw.ai.traversers.BFS;
import ie.gmit.sw.ai.traversers.RecursiveDFSTraversator;
import ie.gmit.sw.ai.traversers.Traversator;

public class Monster implements Interact, Runnable{

	private double health;
	private FuzzyFight ffight;
	private double result;
	private double angerLevel;
	Traversator t;
	Maze[][] m;
	Maze coords;

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

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
	
	public void setMaze(Maze[][] m)
	{
		this.m=m;
	}

	public void run() {		
		t = new BFS();
		//t = new BruteForceTraversator(true);
		//t = new RecursiveDFSTraversator();
		t.traverse(m, m[x][y]);
		//System.out.println("Traversing: "+ m[x][y].toString());
	}
}


