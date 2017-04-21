package ie.gmit.sw.ai;

import net.sourceforge.jFuzzyLogic.*;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class FuzzyFight {
	
	public double getFuzzy(double angerLevel, double weapon){
		
	  String fileName = "fcl/fight.fcl";
      FIS fis = FIS.load(fileName,true);
      
      // Error while loading?
      if( fis == null ) { 
          System.err.println("Can't load file: '" + fileName + "'");
          return 0;
      }
      FunctionBlock functionBlock = fis.getFunctionBlock("fight");

      // Show 
      //JFuzzyChart.get().chart(functionBlock);

      // Set inputs
      fis.setVariable("angerLevel", angerLevel); //Apply a value to a variable
      fis.setVariable("weapon", weapon);// Evaluate
      // Evaluate
      fis.evaluate();
      
      Variable damage = functionBlock.getVariable("damage");
      //JFuzzyChart.get().chart(damage, damage.getDefuzzifier(), true);
      //System.out.println(damage);
		
		
		return Math.round(damage.getValue());
		
	}

}
