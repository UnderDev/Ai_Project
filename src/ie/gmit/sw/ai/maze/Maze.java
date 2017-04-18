package ie.gmit.sw.ai.maze;

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
	private char mapItem;


	public Maze(int row, int col){
		this.row = row;
		this.col = col;		
	}


	public char getMapItem() {
		return mapItem;
	}

	public void setMapItem(char mapItem) {
		this.mapItem = mapItem;
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
			addPath(direction);
		
		for (int i = 0; i < paths.length; i++) {
			if (paths[i] == direction) return true;
		}
		return false;
	}

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