package ie.gmit.sw.ai.maze;

//Interface MazeGenerator, contains abstract methods and an Enum
public interface MazeGenerator {
	public enum GeneratorAlgorithm {BinaryTree, HuntAndKill};
	public abstract Maze[][] getMaze();
	public abstract void generateMaze();
}