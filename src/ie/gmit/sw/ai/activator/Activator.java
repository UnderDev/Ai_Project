package ie.gmit.sw.ai.activator;

/*
 * ------------------------------------------------------------------------
 * B.Sc. (Hons) in Software Development - Artificial Intelligence
 * ------------------------------------------------------------------------
 * 
 * The Activator interface represents an abstraction of a neural network
 * activation function. Every activation function f(x) has a derivative 
 * f(x)' that represents the 
 */
public interface Activator {
	public enum ActivationFunction{
		Sigmoid, HyperbolicTangent;
	}
	
	
	/* The activation function for a neuron can be any continuous function. A
	 * multi-layer back-propagation network uses sigmoidal or hyperbolic
	 * tangent activation functions.  
	 */
	public double activate(double value);
	
	
	/* The derivative of a function enables us to compute the slope at a 
	 * single point. It allows us to compute the slope of the gradient descent 
	 * during back-propagation training:
	 * 
	 *    e(p) = (Yd(P) - Y(p)) * slope
	 *
	 * A steep slope implies a large error. The derivative function allows us 
	 * to adjust the error proportionally and avoid over-shooting or 
	 * under-shooting the correct value for a weight.
	 */
	public double derivative(double value);			
}