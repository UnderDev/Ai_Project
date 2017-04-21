package ie.gmit.sw.ai.maze;

public interface MazeGenerator {
	public enum GeneratorAlgorithm {BinaryTree, HuntAndKill};
	public abstract Maze[][] getMaze();
	public abstract void generateMaze();
}