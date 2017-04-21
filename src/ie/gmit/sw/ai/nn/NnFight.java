package ie.gmit.sw.ai.nn;

import java.util.Random;
import ie.gmit.sw.ai.activator.*;
public class NnFight {
	
	private double health;
	private double weapon;
	private double angerLevel;
	
    private double[][] data = { //Health, Damage, Enemies
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
         // (Activator , input nodes , hidden nodes , output nodes;
         NeuralNetwork nn;
         if(health > 5){
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

         Random r = new Random();
 	     int rand = r.nextInt((15 - 0) + 1) + 0;

         int testIndex = rand;
         double[] result = nn.process(data[testIndex]);

         for(double val : result){
             System.out.println(val);
         }
         //System.out.println("==>" + (Utils.getMaxIndex(result) + 1));
         int choice = (Utils.getMaxIndex(result) + 1);

         switch(choice){
             case 1:
                 angerLevel=panic(angerLevel);
                 System.out.println("panic");
                 break;
             case 2:
                 weapon=attack(weapon);
                 System.out.println("attack");
                 break;
             case 3:
                 health=hide(health);
                 System.out.println("hide");
                 break;
             default:
                 health=runAway(health);
                 System.out.println("run forest");
         }
         
         double[] outcome = {health, weapon, angerLevel};
         
         return outcome;
     }
	
}
