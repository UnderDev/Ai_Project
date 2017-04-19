package ie.gmit.sw.ai;

import ie.gmit.sw.ai.traversers.Traversator;

public class Monster extends Sprite implements Interact{
	
	private double health;
	private FuzzyFight ffight;
	private double result;
	private double angerLevel;
	private char ch;

	public Monster(String name, String... images) throws Exception {
		super(name, images);
	}
	
	public Monster(char ch, String name, String... images) throws Exception {
		super(name, images);
		this.ch=ch;
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
	
	public boolean isAlive(double health)
	{
		if(health > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
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



}



