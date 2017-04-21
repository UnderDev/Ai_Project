
package ie.gmit.sw.ai;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import ie.gmit.sw.ai.maze.Maze;
import ie.gmit.sw.ai.nn.NnFight;
import ie.gmit.sw.ai.traversers.*;

/*
 * Monster Class implements Interact and Runnable.
 * This class is used for each spider in the game.
 * Each monster gets its own Maze and traversal algo running on a separate thread.
 * The thread returns when the spider or player is dead.
 */
public class Monster implements Interact, Runnable{

	private double health;
	private FuzzyFight ffight;
	private NnFight nnfight;
	private double result;
	private double angerLevel;
	private double [] outcome;
	private Maze[][] mainMaze;
	private ArrayList<Maze> path = new ArrayList<Maze>();
	private char ch;
	private int x;
	private int y;
	private String algo;
	private String type;
	Player player = new Player();

	public Monster(double health, double angerLevel, char ch, int x, int y, Maze[][] maze, String algo, Player player, String type){
		this.health=health;
		this.angerLevel=angerLevel;
		this.ch=ch;
		this.x=x;
		this.y=y;
		this.mainMaze = maze;
		this.algo=algo;
		this.player=player;
		this.type=type;
	}

	public void setPath(ArrayList<Maze> path) {
		this.path = path;
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

	public void fight(double angerLevel, double weapon) {
		if(this.type.equals("fuzzy"))
		{
			ffight = new FuzzyFight();
			result = Math.round(ffight.getFuzzy(angerLevel, weapon));
			adjustHealth(result);
		}
		else
		{
			nnfight = new NnFight();
			try {
				outcome = nnfight.action(this.getHealth(), player.getWeapon(), angerLevel);
			} catch (Exception e) {
			}

			this.health=outcome[0];
			player.setWeapon(outcome[1]);
			this.angerLevel = outcome[2];
		}
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

	/* Run method gets called from GameRunner when a new thread is started
	 * This thread runs until the spider/player is dead.
	 */
	public void run() {	
		while(this.isAlive()){
			try { //Slow down the thread to give the spider time to move/think
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			getAlgorithm().traverse((Maze[][])copy(mainMaze), (Maze)copy(mainMaze[x][y]), this);

			//Reverse the path so the spider walks from his start to the player
			Collections.reverse(path);
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
						System.out.println("Spider Defeated!!");
						mainMaze[this.x][this.y].setMapItem(' ');
						return;
					}

					if(!player.isAlive()){
						player.getPlayerNode().setMapItem(' ');
						System.out.println("\n---------------------------------");
						System.out.println("----       You Lose!      -------");
						System.out.println("---------------------------------");

						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
						}

						System.exit(0);
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
		case "rDfs":
			return t = new RecursiveDFSTraversator();
		default:
			return t = new RecursiveDFSTraversator();
		}
	}
}


