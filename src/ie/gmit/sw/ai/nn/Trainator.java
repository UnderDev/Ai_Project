package ie.gmit.sw.ai.nn;

/*
 * ------------------------------------------------------------------------
 * B.Sc. (Hons) in Software Development - Artificial Intelligence
 * ------------------------------------------------------------------------
 * 
 * A Trainator is an abstraction of a type that is capable of training a 
 * neural network. There are many different training algorithms that can
 * be used, i.e. back propagation is just one from many...
 */
public interface Trainator {
	public void train(double[][] data, double[][] desired, double alpha, int epochs);
}
