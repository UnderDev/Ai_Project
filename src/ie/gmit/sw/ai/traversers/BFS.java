package ie.gmit.sw.ai.traversers;

import java.util.*;

import ie.gmit.sw.ai.Monster;
import ie.gmit.sw.ai.maze.Maze;

public class BFS implements Traversator{
	private LinkedList<Maze> queue = new LinkedList<Maze>();
	private ArrayList <Maze> path;
	private Maze[][] maze;
	private boolean keepRunning = true;
	private int visitCount = 0;
	private long time = System.currentTimeMillis();
	private Monster monster;
	private Maze node;


	public void traverse(Maze[][] maze, Maze node, Monster monster) {
		this.maze = maze;
		this.monster = monster;
		this.node = node;
		queue.addLast(node);
		search(node);
	}

	public void search(Maze node){
		//System.out.println(Thread.currentThread().getName() +" Current Location : "+node.toString());
		path = new ArrayList<Maze>();
		while(!queue.isEmpty()){

			if (!keepRunning) return;

			//node.setVisited(true);
			visitCount++;

			if (node.isGoal()){

				while (node != null){			
					node = node.getParent();
					if (node != null){ 						
						path.add(node);
					}			
				}								
				monster.setPath(path);

				keepRunning = false;
				return;
			}else{
				Maze[] children = node.children(maze);
				queue.removeFirst();//Remove the starting Node
				for (int i = 0; i < children.length; i++) {
					//for (int i = children.length - 1; i >= 0; i--) {			
					if ((children[i].getRow() <= maze.length - 1) && (children[i].getCol() <= maze[children[i].getRow()].length - 1)
							&& ((children[i].getMapItem() == ' ') || (children[i].getMapItem() == '5'))){
						children[i].setParent(node);
						queue.addLast(children[i]);	

						if(Thread.currentThread().getName() =="Spider 1")
							children[i].setMapItem('\u0036');//spider Char
						else if (Thread.currentThread().getName() =="Spider 2")
							children[i].setMapItem('\u0037');//spider Char
					}
				}
			}

			try {
				node = queue.getFirst();
			} catch (Exception e) {
				System.out.println(Thread.currentThread().getName()+" Has no Valid Path");
				//unvisit();
				//queue.addLast(tempNode);
				//search(tempNode);
			}
		}
	}

	//Not really Needed
	private void unvisit(){
		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < maze[i].length; j++){
				maze[i][j].setParent(null);
			}
		}
	}
}
