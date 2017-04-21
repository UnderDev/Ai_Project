package ie.gmit.sw.ai.nn;

/*
 * ------------------------------------------------------------------------
 * B.Sc. (Hons) in Software Development - Artificial Intelligence
 * ------------------------------------------------------------------------
 * 
 * A simple implementation if the back-propagation training algorithm. This
 * class is designed to work with a 3-layer neural network. 
 */
import java.text.DecimalFormat;
public class BackpropagationTrainer implements Trainator{
	private static final double MOMENTUM = 0.95; //Controls the rate of descent
	private NeuralNetwork net;
	private double[] err_out; //Error values in the output layer
	private double[] err_hidden; //Error values in the hidden layer
	
	public BackpropagationTrainer(NeuralNetwork network){
		this.net = network;
		err_out = new double[net.getOutputLayer().length];
		err_hidden = new double[net.getHiddenLayer().length];
	}
	
	public void train(double[][] trainingData, double[][] desired, double alpha, int epochs) {
		DecimalFormat df = new DecimalFormat("#.#########"); //Formatter for nice output
		long startTime = System.currentTimeMillis(); //Start the clock
		double errTolerance = 0.001d; //Stop training when we reach this error
		double sumOfSquaresError = 0.0d; //Stop training when this is tolerable
		boolean hasError = true;
		int epoch = 0;
		
		for (; epoch < epochs && hasError; epoch++){
			sumOfSquaresError = 0.0d; //Root mean square error for each epoch
			
			for (int index = 0; index < trainingData.length; index++){// loops over rows - to loop over columns trainingData[0].length
				double[] sample = trainingData[index];
				double[] expected = desired[index];
				
				for (int i = 0; i < net.getInputLayer().length; i++) net.getInputLayer()[i] = sample[i];
				for (int i = 0; i < net.getOutputLayer().length; i++) net.getOutputLayer()[i] = expected[i];
				
				net.feedForward();
				backpropagate(expected, alpha);
			}
			
			for (int i = 0; i < err_out.length; i++) sumOfSquaresError += Math.pow(err_out[i], 2);
			
			//if (epoch % 1000 == 0) System.out.println(epoch + "," + sumOfSquaresError);
			if (Math.abs(sumOfSquaresError) <= errTolerance) hasError = false; //Bail out of training
		}
//		System.out.println("[INFO] Training completed in " + ((System.currentTimeMillis() - startTime)/1000) + " seconds.");
//		System.out.println("[INFO] Epochs: " + epoch);
//		System.out.println("[INFO] Sum of Squares Error: " + df.format(sumOfSquaresError));
	}
	
	
	//The comments use the same notation as used in the lecture notes 
	private void backpropagate(double[] expected, double alpha){ //
		//Compute the error gradient in the output layer
		for (int out = 0; out < net.getOutputLayer().length; out++){
			//delta_k(p) = y_k(p) x (1 - y_k(p)) * e_k(p)
			err_out[out] =  net.getActivator().derivative(net.getOutputLayer()[out]) * (expected[out] - net.getOutputLayer()[out]);
		}
		
		//Compute the error gradient in the hidden layer
		for (int hid = 0; hid < net.getHiddenLayer().length; hid++){
			err_hidden[hid] = 0.0d;
			//delta_j(p) = y_j(p) * (1 - y_j(p)) * Sum(delta_k(p) * w_jk(p))
			for (int out = 0; out < net.getOutputLayer().length; out++){
				err_hidden[hid] += err_out[out] * net.getOutputWeights()[hid][out];
			}
			err_hidden[hid] *= net.getActivator().derivative(net.getHiddenLayer()[hid]);
		}
		
		
		//Update the weights in the output layer
		for (int out = 0; out < net.getOutputLayer().length; out++){
			for (int hid = 0; hid < net.getHiddenLayer().length; hid++){
				//delta_w_jk(p) = alpha * y_j(p) * delta_k(p)
				net.getOutputWeights()[hid][out] += alpha * net.getHiddenLayer()[hid] * err_out[out];
			}
			net.getOutputWeights()[net.getHiddenLayer().length][out] += (MOMENTUM * alpha * err_out[out]);
		}
		
		
		//Update the weights in the hidden layer
		for (int hid = 0; hid < net.getHiddenLayer().length; hid++){
			for (int in = 0; in < net.getInputLayer().length; in++){
				//delta_w_ij(p) = alpha * x_i(p) * delta_j(p)
				net.getHiddenWeights()[in][hid] += (alpha * net.getInputLayer()[in] * err_hidden[hid]);
			}
			//w_ij(p + 1) = w_ij(p) + delta_w_ij(p)
			net.getHiddenWeights()[net.getInputLayer().length][hid] += (MOMENTUM * alpha * err_hidden[hid]);
		}
	}
}