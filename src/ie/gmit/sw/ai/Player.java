package ie.gmit.sw.ai;

import ie.gmit.sw.ai.maze.Maze;

public class Player{

	private Maze playerNode;
	private double weapon;
	private double health;
	private FuzzyFight ffight;
	private double result;

	public Player() {		
		this.health=100;
		this.weapon=5;
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

	public void betterWeapon(double power)
	{
		this.weapon=weapon+power;
	}

	public boolean isAlive()
	{
		if(health > 0) return true;
		else return false;
	}

	public double fight(double angerLevel, double weapon) {		
		ffight = new FuzzyFight();
		result = Math.round(ffight.getFuzzy(angerLevel, weapon));
		takeHeath(result);
		return result;
	}

	public void giveHealth(double life)
	{
		this.health=health+life;
	}

	public void takeHeath(double damage)
	{
		this.health=health-damage;
	}

	public double getHealth()
	{
		return health;
	}

}
