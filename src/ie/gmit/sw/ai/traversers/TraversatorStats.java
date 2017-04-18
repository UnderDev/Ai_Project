package ie.gmit.sw.ai.traversers;

import java.awt.Color;
<<<<<<< HEAD

import ie.gmit.sw.ai.*;
//import ie.gmit.sw.ai.audio.*;
public class TraversatorStats {
	public static void printStats(Maze node, long time, int visitCount){
=======
import ie.gmit.sw.ai.maze.*;
public class TraversatorStats {
	public static void printStats(Node node, long time, int visitCount){
>>>>>>> origin/FuzzyLogic
		double depth = 0;
		
		while (node != null){			
			node = node.getParent();
			if (node != null) node.setColor(Color.YELLOW);
			depth++;			
		}
		
        System.out.println("Visited " + visitCount + " nodes in " + time + "ms.");
        System.out.println("Found goal at a depth of " + String.format("%.0f", depth));
        System.out.println("EBF = B* = k^(1/d) = " + String.format("%.2f", Math.pow((double) visitCount, (1.00d / depth))));           
<<<<<<< HEAD
        //SoundEffects.ALARM.play();
=======
>>>>>>> origin/FuzzyLogic
	}
}