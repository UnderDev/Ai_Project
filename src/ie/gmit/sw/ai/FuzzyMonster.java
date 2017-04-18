package ie.gmit.sw.ai;

public class FuzzyMonster extends Sprite implements Runnable{
	
	private static double Fullhealth = 20;
	private FuzzyFight ffight;
	private double result;

	public FuzzyMonster(String name, String... images) throws Exception {
		super(name, images);
	}

	public double fight(double angerLevel, double weapon) {
		
		ffight = new FuzzyFight();
		result = ffight.getFuzzy(angerLevel, weapon);
		return result;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	


	}



