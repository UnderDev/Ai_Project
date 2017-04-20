package ie.gmit.sw.ai.activator;

public class SigmoidActivator implements Activator{
	//f(x) = 1 / (1 + exp( − x))
	public double activate(double value) {
		return (1.0d / (1.0d + Math.exp(-value)));
	}
	
	//Derivative dy/dx = f(x)' = f(x) * (1 - f(x))
	public double derivative(double value) {
		return (value * (1.0d - value));
	}
}