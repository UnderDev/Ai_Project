package ie.gmit.sw.ai.maze;



public class BinaryTreeMazeGenerator extends AbstractMazeGenerator {

	private Maze [][] maze;
	public BinaryTreeMazeGenerator(int rows, int cols) 
	{
		super(rows, cols);
		ini();
		generateMaze();

		int featureNumber = (int)((rows * cols) * 0.01);
		addFeature('\u0031', '0', featureNumber); //1 is a sword, 0 is a hedge
		addFeature('\u0032', '0', featureNumber); //2 is help, 0 is a hedge
		addFeature('\u0033', '0', featureNumber); //3 is a bomb, 0 is a hedge
		addFeature('\u0034', '0', featureNumber); //4 is a hydrogen bomb, 0 is a hedge

		featureNumber = (int)((rows * cols) * 0.01);
		addFeature('\u0036', '0', featureNumber); //6 is a Black Spider, 0 is a hedge
		addFeature('\u0037', '0', featureNumber); //7 is a Blue Spider, 0 is a hedge
		addFeature('\u0038', '0', featureNumber); //8 is a Brown Spider, 0 is a hedge
		addFeature('\u0039', '0', featureNumber); //9 is a Green Spider, 0 is a hedge
		addFeature('\u003A', '0', featureNumber); //: is a Grey Spider, 0 is a hedge
		addFeature('\u003B', '0', featureNumber); //; is a Orange Spider, 0 is a hedge
		addFeature('\u003C', '0', featureNumber); //< is a Red Spider, 0 is a hedge
		addFeature('\u003D', '0', featureNumber); //= is a Yellow Spider, 0 is a hedge
	}

	private void ini(){
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				maze[row][col].setCharType('0'); //Index 0 is a hedge...
			}
		}
	}

	private void addFeature(char feature, char replace, int number){
		int counter = 0;
		while (counter < feature){
			int row = (int) (maze.length * Math.random());
			int col = (int) (maze[0].length * Math.random());

			if (maze[row][col].getCharType() == replace){
				maze[row][col].setCharType(feature);
				counter++;
			}
		}
	}

	public void generateMaze(){ 
		this.maze = super.getMaze();
		for (int row = 1; row < maze.length - 1; row++){
			for (int col = 1; col < maze[row].length - 1; col++){
				int num = (int) (Math.random() * 10);
				if (num > 5 && col + 1 < maze[row].length - 1){
					maze[row][col + 1].setCharType('\u0020'); //\u0020 = 0x20 = 32 (base 10) = SPACE
				}else{
					if (row + 1 < maze.length - 1)maze[row + 1][col].setCharType('\u0020');
				}
			}
		}		
	}

	public Maze[][] getMaze(){
		return this.maze;
	}
}