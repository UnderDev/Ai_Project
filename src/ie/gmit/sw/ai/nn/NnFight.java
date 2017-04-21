package ie.gmit.sw.ai.nn;

import ie.gmit.sw.ai.activator.*;

//Class contains the training data, expected output and actions 
public class NnFight {

	private double health;
	private double weapon;
	private double angerLevel;

	private double[][] data = { //Health, Weapon, Anger level
			{ 2, 0, 0 }, { 2, 0, 0 }, { 2, 0, 1 }, { 2, 0, 1 }, { 2, 1, 0 },
			{ 2, 1, 0 }, { 1, 0, 0 }, { 1, 0, 0 }, { 1, 0, 1 }, { 1, 0, 1 }, 
			{ 1, 1, 0 }, { 1, 1, 0 }, { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 1 }, 
			{ 0, 0, 1 }, { 0, 1, 0 }, { 0, 1, 0 } };

	private double[][] expected = { //Panic, Attack, Hide
			{ 0.0, 0.0, 1.0}, { 0.0, 0.0, 1.0 }, { 1.0, 0.0, 0.0 }, { 1.0, 0.0, 0.0 }, 
			{ 0.0, 0.0, 0.0}, { 1.0, 0.0, 0.0}, { 0.0, 0.0, 1.0 }, { 0.0, 0.0, 0.0 }, 
			{ 1.0, 0.0, 0.0}, { 0.0, 0.0, 0.0}, { 0.0, 0.0, 0.0}, { 0.0, 0.0, 0.0 }, 
			{ 0.0, 0.0, 1.0}, { 0.0, 0.0, 0.0}, { 0.0, 0.0, 0.0}, { 0.0, 1.0, 0.0 }, 
			{ 0.0, 1.0, 0.0 }, { 0.0, 0.0, 0.0} };

	public double panic(double angerLevel){
		return angerLevel=angerLevel-10;

	}

	public double attack (double weapon){
		return weapon=weapon-10;
	}

	public double hide (double health){
		return health=health-10;
	}

	public double runAway (double health){
		return health=health-30;
	}

	public double[] action(double health, double weapon, double angerLevel) throws Exception{

		double[] params = {health, weapon, angerLevel};
		NeuralNetwork nn;
		if(health > 5){
			// (Activator , input nodes , hidden nodes , output nodes;
			nn = new NeuralNetwork(Activator.ActivationFunction.Sigmoid, 3, 3, 3);
			System.out.println("Sigmoid");
		}
		else
		{
			nn = new NeuralNetwork(Activator.ActivationFunction.HyperbolicTangent, 3, 3, 3);
			System.out.println("HyperbolicTangent");
		}

		Trainator trainer = new BackpropagationTrainer(nn);
		trainer.train(data, expected, 0.2, 10000);

		double[] result = nn.process(params);

		int choice = (Utils.getMaxIndex(result) + 1);

		switch(choice){
		case 1:
			angerLevel=panic(angerLevel);
			System.out.println("Panic");
			break;
		case 2:
			weapon=attack(weapon);
			System.out.println("Attack");
			break;
		case 3:
			health=hide(health);
			System.out.println("Hide");
			break;
		default:
			health=runAway(health);
			System.out.println("Run Forest!! RUN....");
		}

		double[] outcome = {health, weapon, angerLevel};

		return outcome;
	}

}
