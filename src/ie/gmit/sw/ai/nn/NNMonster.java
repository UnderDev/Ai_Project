package ie.gmit.sw.ai.nn;

import java.util.Random;
import ie.gmit.sw.ai.activator.*;
public class NNMonster {

    private double[][] data = { //Health, Damage, Enemies
		{ 2, 0, 0}, { 2, 0, 0}, { 2, 0, 1}, { 2, 0, 1}, { 2, 1, 0},
		{ 2, 1, 0}, { 1, 0, 0}, { 1, 0, 0}, { 1, 0, 1}, { 1, 0, 1}, 
		{ 1, 1, 0}, { 1, 1, 0}, { 0, 0, 0}, { 0, 0, 0}, { 0, 0, 1}, 
		{ 0, 0, 1}, { 0, 1, 0}, { 0, 1, 0} };

    private double[][] expected = { //Attack, Hide, Run
		{ 0.0, 0.0, 1.0}, { 0.0, 0.0, 1.0}, { 1.0, 0.0, 0.0}, { 1.0, 0.0, 0.0}, 
		{ 1.0, 0.0, 0.0}, { 0.0, 0.0, 1.0}, { 0.0, 0.0, 0.0}, 
		{ 1.0, 0.0, 0.0}, 
		{ 0.0, 0.0, 1.0}, { 0.0, 1.0, 0.0}, 
		{ 0.0, 1.0, 0.0}};

    public void panic(){
        System.out.println("Panic");
    }

    public void attack (){
        System.out.println("Attack");
    }

     public void hide (){
        System.out.println("Hide");
    }

     public void runAway (){
        System.out.println("Run");
    }

     public void action(double health, double sword, double gun, double enemies) throws Exception{

         double[] params = {health, sword, gun, enemies};
         // (Activator , input nodes , hidden nodes , output nodes;
         NeuralNetwork nn = new NeuralNetwork(Activator.ActivationFunction.Sigmoid, 3, 3, 3);
         Trainator trainer = new BackpropagationTrainer(nn);
         trainer.train(data, expected, 0.2, 10000);

         Random r = new Random();
 	    int rand = r.nextInt((15 - 0) + 1) + 0;

         int testIndex = rand;
         double[] result = nn.process(data[testIndex]);
         //double[] result = nn.process(params);
         // for (int i = 0; i < expected[testIndex].length; i++){
         //     System.out.print(expected[testIndex][i] + ",");
         // }

         for(double val : result){
             System.out.println(val);
         }
         System.out.println("==>" + (Utils.getMaxIndex(result) + 1));
         int choice = (Utils.getMaxIndex(result) + 1);

         switch(choice){
             case 1:
                 panic();
                 break;
             case 2:
                 attack();
                 break;
             case 3:
                 hide();
                 break;
             default:
                 runAway();
         }
     }

     
     
     public void NNfight (){
    	double health = 0;
        double sword = 0;
        double gun = 0;
        double enemies = 0;
    	try {
			action(health, sword, gun, enemies);
		} catch (Exception e) {
		}
     }
	
}
