package ie.gmit.sw.ai;

import ie.gmit.sw.ai.maze.Maze;

public class Player extends Sprite{

	private Maze playerNode;
	private double weapon;
	private double health = 100;

	public Player(String name, String... images) throws Exception {
		super(name, images);
		// TODO Auto-generated constructor stub
	}

	public Maze getPlayerNode()
	{
		return playerNode;
	}

	public void setPlayerNode(Maze playerNode)
	{
		this.playerNode = playerNode;
	}

	public void setWeapon(double weapon)
	{
		this.weapon=weapon;
	}

	public double getWeapon()
	{
		return Math.round(weapon);
	}

	public boolean isAlive(double health)
	{
		if(health > 0) return true;
		else return false;
	}

}
