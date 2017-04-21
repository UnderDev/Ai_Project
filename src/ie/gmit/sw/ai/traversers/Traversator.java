package ie.gmit.sw.ai.traversers;

import ie.gmit.sw.ai.Monster;
import ie.gmit.sw.ai.maze.Maze;
public interface Traversator {
	public void traverse(Maze[][] maze, Maze maze2, Monster monster);
}
