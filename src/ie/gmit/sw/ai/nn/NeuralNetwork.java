package ie.gmit.sw.ai.nn;


/*
 * ------------------------------------------------------------------------
 * B.Sc. (Hons) in Software Development - Artificial Intelligence
 * ------------------------------------------------------------------------
 * 
 * This 3-layer neural network is nothing more than a set of three arrays to 
 * represent the values of the input, hidden and output layers. This design 
 * could easily be extended to allow an n-layer neural network to be created. 
 * Moreover, the speed of the feed-forward and back-propagation operations 
 * can be significantly increased by exploiting matrix multiplication.  
 */

import java.util.*;

import ie.gmit.sw.ai.activator.Activator;
import ie.gmit.sw.ai.activator.ActivatorFactory;
public class NeuralNetwork{
	private Activator activator;
	private double[] inputs; //Stores inputs X1, X2,...,Xn 
	private double[] hidden; //Stores activated inputs
	private double[] outputs; //Stores Y
	
	private double[][] ihW; //Matrix of weights for input->hidden layer
	private double[][] hoW; //Matrix of weights for hidden->output layer

	public NeuralNetwork(Activator.ActivationFunction function, int num_input_nodes, int num_hidden_nodes, int num_output_nodes) {
		this.activator = ActivatorFactory.getInstance().getActivator(function);
		this.inputs = new double[num_input_nodes];
		this.hidden = new double[num_hidden_nodes];
		this.outputs = new double[num_output_nodes];
		
		this.ihW = new double[num_input_nodes + 1][num_hidden_nodes]; //An extra row for the bias(theta)
		this.hoW = new double[num_hidden_nodes + 1][num_output_nodes]; //An extra row for the bias(theta)
		
		this.initialiseWeights(ihW);
		this.initialiseWeights(hoW);
	}
	
	public double[] process(double[] data_inputs) throws Exception{
		//Check for consistent input
		if (data_inputs.length != inputs.length){
			throw new Exception("Invalid input for a " + inputs.length + "x" + hidden.length + "x" + outputs.length + " neural network.");
		}
		
		//Initialise the input layer
		for (int i = 0; i < inputs.length; i++){
			inputs[i] = data_inputs[i];
		}
		
		//Feed the inputs forward to the next layer
		this.feedForward();
		
		//Return the out layer
		return outputs;
	}

	private void initialiseWeights(double[][] matrix){
		//Initialize weights to random numbers in range -0.5 - +0.5
		Random rand = new Random();  
		for (int row = 0; row < matrix.length; row++){
			for (int col = 0; col < matrix[row].length; col++){
				matrix[row][col] = rand.nextDouble() - 0.5;
			}
		}
	}
	
	public void feedForward(){ 
		//Feed the inputs forward through the network as a weighted sum
		double sum = 0.0d;
		
		//Compute Input->Hidden Layer
		for (int hid = 0; hid < hidden.length; hid++){
			sum = 0.0d;
			for (int in = 0; in < inputs.length; in++) sum += inputs[in] * ihW[in][hid];
			sum+= ihW[inputs.length][hid];
			hidden[hid] = activator.activate(sum); //Apply activation function
		}
	
		//Compute Hidden->Output Layer
		for (int out = 0; out < outputs.length; out++){
			sum = 0.0d;
			for (int hid = 0; hid < hidden.length; hid++)	sum += hidden[hid] * hoW[hid][out];		
			sum+= hoW[hidden.length][out];
			outputs[out] = activator.activate(sum); //Apply activation function
		}
	}
	
	public Activator getActivator() {
		return activator;
	}
	
	public double[] getInputLayer() {
		return inputs;
	}

	public void setInputs(double[] inputs) {
		this.inputs = inputs;
	}

	public double[] getHiddenLayer() {
		return hidden;
	}

	public double[] getOutputLayer() {
		return outputs;
	}

	public double[][] getHiddenWeights() {
		return ihW;
	}

	public double[][] getOutputWeights() {
		return hoW;
	}
}