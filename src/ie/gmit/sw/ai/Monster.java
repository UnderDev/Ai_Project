
package ie.gmit.sw.ai;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import ie.gmit.sw.ai.maze.Maze;
import ie.gmit.sw.ai.traversers.AStarTraversator;
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

	//Create a deep copy of the Maze
	public static Object copy(Object orig) {
		Object obj = null;
		try {
			// Write the object out to a byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(orig);
			out.flush();
			out.close();

			// Make an input stream from the byte array and read
			// a copy of the object back in.
			ObjectInputStream in = new ObjectInputStream(
					new ByteArrayInputStream(bos.toByteArray()));
			obj = in.readObject();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		return obj;
	}

	public void run() {	

		while(this.isAlive()){
			try { //Simulate processing each expanded node
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			getAlgorithm().traverse((Maze[][])copy(mainMaze), (Maze)copy(mainMaze[x][y]), this);

			Collections.reverse(path);
			if(!path.isEmpty())
				path.remove(0);// Takes out the position it currently is in

			for (Maze node :path) {
				if(mainMaze[node.getRow()][node.getCol()].getMapItem() == ' '){
					mainMaze[node.getRow()][node.getCol()].setMapItem(this.ch);
					mainMaze[x][y].setMapItem(' ');
					this.setPos(node.getRow(),node.getCol());
					try { //Slow Down the spiders movement Speed
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else if(mainMaze[node.getRow()][node.getCol()].getMapItem() == '5'){
					fight(this.angerLevel, this.player.getWeapon());
					this.player.takeHeath(5);;
					if(!this.isAlive())
					{
						System.out.println(this.isAlive());
						mainMaze[this.x][this.y].setMapItem(' ');
						return;
					}
				}
			}
		}
	}

	private Traversator getAlgorithm() {
		Traversator t;
		switch(algo){
		case "bfs":
			return t = new BFS();
		case "aStar":
			return t = new AStarTraversator(player.getPlayerNode());
		case "brut":
			return t = new BruteForceTraversator(false);
		case "rDfs":
			return t = new RecursiveDFSTraversator();
		default:
			return t = new RecursiveDFSTraversator();
		}
	}
}


