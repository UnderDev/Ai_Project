package ie.gmit.sw.ai.maze;

import java.util.*;

import ie.gmit.sw.ai.Monster;

public class BinaryTreeMazeGenerator extends AbstractMazeGenerator {

	private Maze [][] maze;
	private static List<Monster> monsters = new ArrayList<Monster>();
	private Monster m;
	Thread t;
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

		featureNumber = (int)((rows * cols) * 0.0001);
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
				maze[row][col].setMapItem('0'); //Index 0 is a hedge
			}
		}
	}

	private void addFeature(char feature, char replace, int number){
		int counter = 0;
		Random r = new Random();
		while (counter < feature){
			int row = (int) (maze.length * Math.random());
			int col = (int) (maze[0].length * Math.random());

			if (maze[row][col].getMapItem() == replace){
				maze[row][col].setMapItem(feature);

				switch(feature)
				{
				case '6':
					m = new Monster(r.nextDouble()*10, r.nextDouble()*100, feature, row, col);
					t = new Thread(m);
					m.setMaze(maze);
					t.start();
					break;
				case '7':
					m = new Monster(r.nextDouble()*10, r.nextDouble()*100, feature, row, col);
					t = new Thread(m);
					m.setMaze(maze);
					t.start();
					break;
				case '8':
					m = new Monster(r.nextDouble()*10, r.nextDouble()*100, feature, row, col);
					t = new Thread(m);
					m.setMaze(maze);
					t.start();
					break;
				case '9':
					m = new Monster(r.nextDouble()*10, r.nextDouble()*100, feature, row, col);
					t = new Thread(m);
					m.setMaze(maze);
					t.start();
					break;
				case ':':
					m = new Monster(r.nextDouble()*10, r.nextDouble()*100, feature, row, col);
					t = new Thread(m);
					m.setMaze(maze);
					t.start();
					break;
				case ';':
					m = new Monster(r.nextDouble()*10, r.nextDouble()*100, feature, row, col);
					t = new Thread(m);
					m.setMaze(maze);
					t.start();
					break;
				case '<':
					m = new Monster(r.nextDouble()*10, r.nextDouble()*100, feature, row, col);
					t = new Thread(m);
					m.setMaze(maze);
					t.start();
					break;
				case '=':
					m = new Monster(r.nextDouble()*10, r.nextDouble()*100, feature, row, col);
					t = new Thread(m);
					m.setMaze(maze);
					t.start();
					break;
				default:
					break;
				}			
				counter++;
			}
		}
	}

	public void generateMaze(){ 
		this.maze = super.getMaze();
		for (int row = 1; row < maze.length - 1; row++){
			for (int col = 1; col < maze[row].length - 1; col++){
				int num = (int) (Math.random() * 10);
				if (num > 5 && col + 1 < maze[row].length - 1)
					maze[row][col + 1].setMapItem('\u0020'); //\u0020 = 0x20 = 32 (base 10) = SPACE
				else{
					if (row + 1 < maze.length - 1) 
						maze[row + 1][col].setMapItem('\u0020');
					maze[row][col].addPath(Maze.Direction.West);
				}
			}
		}		
	}

	public Maze[][] getMaze(){
		return this.maze;
	}
}