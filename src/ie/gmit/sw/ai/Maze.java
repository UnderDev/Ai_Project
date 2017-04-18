package ie.gmit.sw.ai;

<<<<<<< HEAD
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;



public class Maze {
	private char[][] maze;

	public enum Direction {North, South, East, West};
	private Maze parent;
	private Color color = Color.BLACK;
	private Direction[] paths = null;
	public boolean visited =  false;
	public boolean goal;

	private int row = -1;
	private int col = -1;
	private int distance;
	private char charType;


	public Maze(int row, int col){
		this.row = row;
		this.col = col;		
	}


	public char getCharType() {
		return charType;
	}

	public void setCharType(char charType) {
		this.charType = charType;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Maze getParent() {
		return parent;
	}

	public void setParent(Maze parent) {
		this.parent = parent;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isGoal() {
		return goal;
	}

	public void setGoal(boolean goal) {
		this.goal = goal;
	}

	public boolean hasDirection(Direction direction){	
		if (paths==null)
			return false;
		
		for (int i = 0; i < paths.length; i++) {
			if (paths[i] == direction) return true;
=======
import ie.gmit.sw.ai.maze.Node;

public class Maze {
	
	private Node[][] maze;
	
	public Maze(int dimension){
		
		maze = new Node[dimension][dimension];
		init();
		buildMaze();
		
		int featureNumber = (int)((dimension * dimension) * 0.01);
		addFeature('\u0031', '0', featureNumber); //1 is a sword, 0 is a hedge
		addFeature('\u0032', '0', featureNumber); //2 is help, 0 is a hedge
		addFeature('\u0033', '0', featureNumber); //3 is a bomb, 0 is a hedge
		addFeature('\u0034', '0', featureNumber); //4 is a hydrogen bomb, 0 is a hedge
		
		featureNumber = (int)((dimension * dimension) * 0.01);
		addFeature('\u0036', '0', featureNumber); //6 is a Black Spider, 0 is a hedge
		addFeature('\u0037', '0', featureNumber); //7 is a Blue Spider, 0 is a hedge
		addFeature('\u0038', '0', featureNumber); //8 is a Brown Spider, 0 is a hedge
		addFeature('\u0039', '0', featureNumber); //9 is a Green Spider, 0 is a hedge
		addFeature('\u003A', '0', featureNumber); //: is a Grey Spider, 0 is a hedge
		addFeature('\u003B', '0', featureNumber); //; is a Orange Spider, 0 is a hedge
		addFeature('\u003C', '0', featureNumber); //< is a Red Spider, 0 is a hedge
		addFeature('\u003D', '0', featureNumber); //= is a Yellow Spider, 0 is a hedge
	}
	
	private void init(){
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				maze[row][col] = new Node(row, col);
				maze[row][col].setFeature('0'); //Index 0 is a hedge...
			}
>>>>>>> origin/FuzzyLogic
		}
		return false;
	}
<<<<<<< HEAD

	public Maze[] children(Maze[][] maze){		
		java.util.List<Maze> children = new java.util.ArrayList<Maze>();

		if (row > 0 && maze[row - 1][col].hasDirection(Direction.South)) children.add(maze[row - 1][col]); //Add North
		if (row < maze.length - 1 && maze[row + 1][col].hasDirection(Direction.North)) children.add(maze[row + 1][col]); //Add South
		if (col > 0 && maze[row][col - 1].hasDirection(Direction.East)) children.add(maze[row][col - 1]); //Add West
		if (col < maze[row].length - 1 && maze[row][col + 1].hasDirection(Direction.West)) children.add(maze[row][col + 1]); //Add East

		return (Maze[]) children.toArray(new Maze[children.size()]);
	}

	public Maze[] adjacentNodes(Maze[][] maze){
		java.util.List<Maze> adjacents = new java.util.ArrayList<Maze>();

		if (row > 0) adjacents.add(maze[row - 1][col]);//Add North
		if (row < maze.length - 1) adjacents.add(maze[row + 1][col]); //Add South
		if (col > 0) adjacents.add(maze[row][col - 1]); //Add West
		if (col < maze[row].length - 1) adjacents.add(maze[row][col + 1]); //Add East

		return (Maze[]) adjacents.toArray(new Maze[adjacents.size()]);
	}

	public List<Maze> getAdjacentNodes(Maze[][] maze) {
		List<Maze> adjacentNodes = new ArrayList<Maze>();

		if(row-1 > 0) adjacentNodes.add(maze[row-1][col]); // Node Above
		if(row+1 < maze.length-1) adjacentNodes.add(maze[row+1][col]); // Node Below
		if(col-1 > 0) adjacentNodes.add(maze[row][col-1]); // Node to left
		if(col+1 < maze[0].length-1) adjacentNodes.add(maze[row][col+1]); // Node to right
		return adjacentNodes; 
	}

	public Direction[] getPaths() {
		return paths;
	}

	public void addPath(Direction direction) {
		int index = 0;
		if (paths == null){
			paths = new Direction[index + 1];		
		}else{
			index = paths.length;
			Direction[] temp = new Direction[index + 1];
			for (int i = 0; i < paths.length; i++) temp[i] = paths[i];
			paths = temp;
=======
	
	private void addFeature(char feature, char replace, int number){
		int counter = 0;
		while (counter < feature){
			int row = (int) (maze.length * Math.random());
			int col = (int) (maze[0].length * Math.random());
			
			if (maze[row][col].getFeature() == replace){
				maze[row][col].setFeature(feature);
				counter++;
			}
		}
	}
	
	private void buildMaze(){ 
		for (int row = 1; row < maze.length - 1; row++){
			for (int col = 1; col < maze[row].length - 1; col++){
				int num = (int) (Math.random() * 10);
				if (num > 5 && col + 1 < maze[row].length - 1){
					maze[row][col + 1].setFeature('\u0020'); //\u0020 = 0x20 = 32 (base 10) = SPACE
				}else{
					if (row + 1 < maze.length - 1)maze[row + 1][col].setFeature('\u0020');
				}
			}
		}		
	}
	
	public Node[][] getMaze(){
		return this.maze;
	}
	
	public Node get(int row, int col){
		return this.maze[row][col];
	}
	
	public void set(int row, int col, char c){
		this.maze[row][col].setFeature(c);
	}
	
	public int size(){
		return this.maze.length;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				sb.append(maze[row][col]);
				if (col < maze[row].length - 1) sb.append(",");
			}
			sb.append("\n");
>>>>>>> origin/FuzzyLogic
		}

		paths[index] = direction;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.color = Color.BLUE;
		this.visited = visited;
	}


	public int getHeuristic(Maze goal){
		double x1 = this.col;
		double y1 = this.row;
		double x2 = goal.getCol();
		double y2 = goal.getRow();
		double d = 1;
		return (int)(d * Math.abs(x1 - x2) - Math.abs(y1-y2));
	}

	public int getPathCost() {
		return distance;
	}

	public void setPathCost(int distance) {
		this.distance = distance;
	}


	public String toString() {
		return "[" + row + "/" + col + "]";
	}

}