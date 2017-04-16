package ie.gmit.sw.ai.traversers;

import ie.gmit.sw.ai.maze.*;
public class SimulatedAnnealingTraversator implements Traversator{
	private Node goal;
	
	public SimulatedAnnealingTraversator(Node goal){
		this.goal = goal;
	}
	
	public void traverse(Node[][] maze, Node current) {
        long time = System.currentTimeMillis();
    	int visitCount = 0;
		double temperature = 10E8; //Initial temperature
		double alpha = 0.0003; //Cooling rate
		
		Node best = current;
		Node next = null;
		

        // Loop until system has cooled
		while (temperature > 0.001d) {
        	current.setVisited(true);
        	visitCount++;	
			
        	Node[] children = current.children(maze);
        	//int index = (int)(children.length * Math.random());
        	
        	int index = new java.util.Random().nextInt(children.length);
        	next = children[index];

			if (next.isGoalNode()){
		        time = System.currentTimeMillis() - time; //Stop the clock
		        TraversatorStats.printStats(goal, time, visitCount);
				break;
			}
			
			try { //Simulate processing each expanded node
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	
			/*Annealing
			At a high temperature, a probability computed from the function:			
						P = e ^ (delta / Temperature);
			is likely to be > 5 allowing the algorithm to choose a worse heuristic
			option than the current node. As the temperature falls, this P value
			decreases towards 0. The net effect of simulated annealing is to allow
			the algorithm to avoid getting stuck at local optima early in a search.		
					
			
			Note that the value of e ^ (delta / Temperature) is treated like a 
			probability (0..1). A value of 1 means we will except a worse 
			choice than the current node.
			 
			----------------------------------------------------------------
			Variable					Value of e ^ (delta / Temperature)
			----------------------------------------------------------------
			Large delta value			Close to zero
			Delta close to 0			Close to 1
			Temperature is large		Close to 1
			Temperature is small		Close to 0
			
			*/
			double delta = next.getHeuristic(goal) - current.getHeuristic(goal);			
			if (delta < 0 ){
				current = next;			
			}else{
				double p = Math.exp((current.getHeuristic(goal) -  next.getHeuristic(goal)) / temperature);
				if (p > Math.random()){
					current = next;
				}				
			}
			
			if (current.getHeuristic(goal) < best.getHeuristic(goal)) best = current;
            temperature *= (1 - alpha); //Cool system
        }
	}
}