package ie.gmit.sw.ai.traversers;

import java.util.*;

import ie.gmit.sw.ai.Monster;
import ie.gmit.sw.ai.maze.Maze;

public class BFS implements Traversator{
	private LinkedList<Maze> queue = new LinkedList<Maze>();
	private ArrayList <Maze> path = new ArrayList<Maze>();
	private Maze[][] maze;
	private boolean keepRunning = true;
	private int visitCount = 0;
	private long time = System.currentTimeMillis();
	private Monster monster;
	private Maze tempNode;


	public void traverse(Maze[][] maze, Maze node, Monster monster) {
		this.maze = maze;
		this.monster = monster;
		this.tempNode = node;
		queue.addLast(node);
		search(node);
	}

	public void search(Maze node){
		System.out.println(Thread.currentThread().getName() +" Current Location : "+node.toString());
		
		while(!queue.isEmpty()){

			if (!keepRunning) return;

			//node.setVisited(true);
			visitCount++;

			try { //Simulate processing each expanded node
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}


			if (node.isGoal()){
				System.out.println(Thread.currentThread().getName()+" Found you at " + node.toString());
				//path.add(node);			
				//time = System.currentTimeMillis() - time; //Stop the clock
				//TraversatorStats.printStats(node, time, visitCount);
				while (node != null){			
					node = node.getParent();
					if (node != null){ 						
						path.add(node);
					}			
				}								
				monster.setPath(path);
				monster.setFound(true);

				keepRunning = false;
				return;
				//System.exit(0);
			}else{
				Maze[] children = node.children(maze);
				queue.removeFirst();//Remove the starting Node
				for (int i = 0; i < children.length; i++) {
					//for (int i = children.length - 1; i >= 0; i--) {			
					//if (children[i] != null && !children[i].isVisited()){
					if ((children[i].getRow() <= maze.length - 1) && (children[i].getCol() <= maze[children[i].getRow()].length - 1)
							&& ((children[i].getMapItem() == ' ') || (children[i].getMapItem() == '5'))){
						children[i].setParent(node);
						queue.addLast(children[i]);	
						//if(Thread.currentThread().getName() =="Spider 2")
						//children[i].setMapItem('\u0038');
						if(Thread.currentThread().getName() =="Spider 1")
							children[i].setMapItem('\u0036');//spider Char
						else if (Thread.currentThread().getName() =="Spider 2")
							children[i].setMapItem('\u0037');//spider Char
						//}
					}
				}
			}

			try {
				node = queue.getFirst();
			} catch (Exception e) {
				System.out.println("Something went Wrong!");
				unvisit();
				queue.addLast(tempNode);
				search(tempNode);
			}
		}
		/*if (!monster.isFound()){
			//unvisit();
			System.out.println(Thread.currentThread().getName() + " Searching Again from Node: " +tempNode.toString());
			queue.addLast(tempNode);
			search(tempNode);
		}*/
	}
	private void unvisit(){
		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < maze[i].length; j++){
				maze[i][j].setParent(null);
			}
		}
	}
}
