package ie.gmit.sw.ai.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ie.gmit.sw.ai.Monster;
import ie.gmit.sw.ai.maze.Maze.Direction;

public class HuntAndKillMazeGenerator extends AbstractMazeGenerator {
	
	private Maze [][] maze;
	private Monster m;
	private Thread t;
	
	public HuntAndKillMazeGenerator(int rows, int cols) {
		super(rows, cols);
		ini();
		generateMaze();

		int featureNumber = (int)((rows * cols) * 0.01);
		addFeature('\u0031', '0', featureNumber); //1 is a sword, 0 is a hedge
		addFeature('\u0032', '0', featureNumber); //2 is help, 0 is a hedge
		addFeature('\u0033', '0', featureNumber); //3 is a bomb, 0 is a hedge
		addFeature('\u0034', '0', featureNumber); //4 is a hydrogen bomb, 0 is a hedge

		featureNumber = (int)((rows * cols) * 0.001);
		addFeature('\u0036', '0', 1); //6 is a Black Spider, 0 is a hedge
		//addFeature('\u0037', '0', featureNumber); //7 is a Blue Spider, 0 is a hedge
		//addFeature('\u0038', '0', featureNumber); //8 is a Brown Spider, 0 is a hedge
		//addFeature('\u0039', '0', featureNumber); //9 is a Green Spider, 0 is a hedge
		//addFeature('\u003A', '0', featureNumber); //: is a Grey Spider, 0 is a hedge
		//addFeature('\u003B', '0', featureNumber); //; is a Orange Spider, 0 is a hedge
		//addFeature('\u003C', '0', featureNumber); //< is a Red Spider, 0 is a hedge
		//addFeature('\u003D', '0', featureNumber); //= is a Yellow Spider, 0 is a hedge
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

		while (counter < number){
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
					/*case '7':
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
					break;*/
				default:
					break;
				}			
				counter++;
			}
		}
	}

	//No stack or storage enables Hunt and Kill to create very large mazes.  tends to make Mazes with a high "river" factor, but not as high as the recursive backtracker. 
	//You can make this generate Mazes with a lower river factor by choosing to enter "hunt" mode more often.
	public void generateMaze(){
		this.maze = super.getMaze();

		Random generator = new Random();
		int randRow = generator.nextInt(maze.length);
		int randCol = generator.nextInt(maze[0].length);
		Maze node = maze[randRow][randCol];
		
		//Start random walk
		while (node != null){			
			//node.setVisited(true);
			Maze[] adjacents = node.adjacentNodes(maze);
			super.shuffle(adjacents);

			boolean look = true;
			for (int i = 0; i < adjacents.length && look; i++) {
				if (!adjacents[i].isVisited()){
					Direction dir = getDirection(node, adjacents[i]);
					node.addPath(dir);
					adjacents[i].addPath(opposite(dir));
					

					node = adjacents[i];
				}
			}

			if (look) node = hunt();			
		}
	}

	private Maze hunt(){
		Maze[][] maze = super.getMaze();

		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < maze[i].length; j++){
				if (!maze[i][j].isVisited()) return maze[i][j];
			}
		}
		return null;
	}
}
