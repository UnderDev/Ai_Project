
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
	private String algo;
	Player player = new Player();

	public Monster(double health, double angerLevel, char ch, int x, int y, Maze[][] maze, String algo, Player player){
		this.health=health;
		this.angerLevel=angerLevel;
		this.ch=ch;
		this.x=x;
		this.y=y;
		this.mainMaze = maze;
		this.algo=algo;
		this.player=player;
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
		this.mazeCopy=m;
	}

	public void run() {	
		System.out.println("Starting: "+ Thread.currentThread().getName());
		//if(Thread.currentThread().getName() == "Spider 1")
		if(algo.equals("bfs")){
			t = new BFS();	
		}
		else if(algo.equals("dfs")){
			t = new RecursiveDFSTraversator();	
		}
		System.out.println("Pos " + x + " " + y);
		t.traverse(mazeCopy, mazeCopy[x][y], this);


		Collections.reverse(path);
		path.remove(0);// Takes out the position it currently is in
		for (Maze node :path) {

			try { //Simulate processing each expanded node
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(mainMaze[node.getRow()][node.getCol()].getMapItem() == ' '){
				mainMaze[node.getRow()][node.getCol()].setMapItem(this.ch);
				mainMaze[x][y].setMapItem(' ');
				this.setPos(node.getRow(),node.getCol());
			}
		}
		fight(this.angerLevel, this.player.getWeapon());

		if(!this.isAlive())
			mainMaze[this.x][this.y].setMapItem(' ');
	}
}


