package ie.gmit.sw.ai;

import ie.gmit.sw.ai.maze.Maze;

public class Player extends Sprite implements Interact{

	private Maze playerNode;
	
	public Player(String name, String... images) throws Exception {
		super(name, images);
		// TODO Auto-generated constructor stub
	}

	public double fight(double weapon, double angerLevel) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Maze getPlayerNode()
	{
		return playerNode;
	}
	
	public void setPlayerNode(Maze playerNode)
	{
		this.playerNode = playerNode;
	}

}
