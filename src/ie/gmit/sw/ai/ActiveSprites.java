package ie.gmit.sw.ai;

public abstract class ActiveSprites extends Sprite implements Interact{
	
	protected double health;
	
	public ActiveSprites(String name, String... images) throws Exception {
		super(name, images);

	}

	@Override
	public double fight(double weapon, double angerLevel) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
