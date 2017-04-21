package ie.gmit.sw.ai.maze;

import ie.gmit.sw.ai.maze.MazeGenerator.*;
public class MazeGeneratorFactory {
	private static MazeGeneratorFactory mgf = new MazeGeneratorFactory();

	private MazeGeneratorFactory(){		
	}

	public static MazeGeneratorFactory getInstance(){
		return mgf;
	}

	public MazeGenerator getMazeGenerator(MazeGenerator.GeneratorAlgorithm algorithm, int rows, int cols){
		if (algorithm == GeneratorAlgorithm.HuntAndKill){
			return new HuntAndKillMazeGenerator(rows, cols);
		}else{
			return new BinaryTreeMazeGenerator(rows, cols);			
		}		
	}	
}