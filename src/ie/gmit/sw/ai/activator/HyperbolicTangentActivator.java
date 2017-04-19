package ie.gmit.sw.ai.activator;

public class HyperbolicTangentActivator implements Activator{
	//f(x) = (e^x - e^-x)/(e^x + e^-x) 
	public double activate(double value) {
		return Math.tanh(value);
	}
	
	//Derivative dy/dx = f(x)' = 1 − f(x)^2
	public double derivative(double value) {
		return (1 - Math.pow(value, 2));
	}
}
