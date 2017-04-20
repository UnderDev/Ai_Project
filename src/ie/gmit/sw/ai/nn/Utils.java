package ie.gmit.sw.ai.nn;

/*
 * ------------------------------------------------------------------------
 * B.Sc. (Hons) in Software Development - Artificial Intelligence
 * ------------------------------------------------------------------------
 * 
 * A set of useful utility methods for use with the neural network
 */
import java.util.Arrays;
public class Utils {
    //Normalize the input variables to values within the range [lower..upper]
	public static double[][] normalize(double[][] matrix, double lower, double upper) {
    	double[][] normalized = new double[matrix.length][];
    	for (int row = 0; row < matrix.length; row++){
    		//Feed each row to the overloaded method
    		normalized[row] = normalize(matrix[row], lower, upper);
    	}
    	return normalized;
    }
	
	//Normalize the input variables to values within the range [lower..upper]
    public static double[] normalize(double[] vector, double lower, double upper) {
        double[] normalized = new double[vector.length];
        double max = Arrays.stream(vector).max().getAsDouble();
        double min = Arrays.stream(vector).min().getAsDouble();
        
        for(int i=0; i<normalized.length; i++) {
            normalized[i] = (vector[i] - min)*(upper - lower)/(max - min) + lower;
        }
        return normalized;
    }	

    //Returns the index of the output layer with the highest value
    public static int getMaxIndex(double[] vector){
		double max = Double.MIN_VALUE;
		int maxIndex = -1;
		for (int i = 0; i < vector.length;i++){
			if (vector[i] > max){
				max = vector[i];
				maxIndex = i;
			}
		}
		return maxIndex; //Corresponds to the classification
    }
}