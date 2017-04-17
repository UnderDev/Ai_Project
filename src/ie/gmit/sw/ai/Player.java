package ie.gmit.sw.ai;

import ie.gmit.sw.ai.maze.Node;

public class Player extends Sprite implements Interact{

	private Node playerNode;
	
	public Player(String name, String... images) throws Exception {
		super(name, images);
		// TODO Auto-generated constructor stub
	}

	public double fight(double weapon, double angerLevel) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Node getPlayerNode()
	{
		return playerNode;
	}
	
	public void setPlayerNode(Node playerNode)
	{
		this.playerNode = playerNode;
	}

}
