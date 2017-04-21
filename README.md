# Artificial Intelligence 4th Year Project 2017

## Description
This is a maze game starring you, as the dashing and brave Spartan Warrior stuck in a maze with a gang of intelligent spiders.  These spiders have more than just spidey-sense as they rely on Artifical Intelligence to stalk your player around the maze and engage him in battle.  Fear not brave warrior, throughout the maze is a host of weapons and life boosting items that will help you defeat the spiders.

## Features of the Application

## Maze Generation
Maze is created using one of two types of maze generators - Binary Tree or Hunt and Kill
* Binary Tree adds a bias into the maze by randomly creating passages either north or west but not both
* Hunt  and Kill create mazes with a long passageways ("river factor".  By using the Hunt method, you can create mazes with shorter passageways - Could not quite implement this one

### Map Features
The maze is generated with a number of features included:
* Map items
  * Weapons which when picked up by the Spartan, increase his weapon value making him more deadly to spiders
  * Life givers which add points to the players health
* Spartan Warrior
  * The hero of the story who can be controlled around the maze with up, down, left and right arrow keys 
  * Interacting with any hedge that contains an item will result in the item being collected
  * Hedges can't be passed through, don't get caught in a dead end
  * The hero is the goal node for the spiders to try and find so move him as much as you can
* Spiders
  * The spiders are fully threaded
  * Spiders have certain attributes - health, angerLevel, position in the maze, traversal algorithm and AI type
  * Health and angerLevel are randomly generated
  * Traversal algorithms are one of three - AStar, Breath First Search and Recursive Depth First Search.  These find paths to the Spartan and send the spiders after him even when he moves
  * AI type is either fuzzy logic or neural networks - this control how the spider fights
  
  ### AI Features
  The AI functionality of the game is seen in the ferocious battle scenes between the Spartan and the spiders.
  * If a spider is a fuzzy logic type of spider he will start a fuzzyfight with the Spartan
    * The input values for the fight function block are the value of the Spartans weapon and the spiders anger leverl
    * The output value is the damage that is caused to the spider
    * The rules are as seen:
      * RULE 1 : IF weapon IS poor OR angerLevel IS low THEN damage IS low;
      * RULE 2 : IF weapon IS good THEN damage IS medium; 
      * RULE 3 : IF weapon IS excellent AND angerLevel IS high THEN damage IS high;
      
 * If a spider is a neural network type of spider he will start an nnfight with the Spartan
   * There are two neural network activation functions used - Sigmoid and HyperbolicTangent
   * Each neural network contains
     * 3 input nodes which represent spider health, player sword and spider anger
     * 3 hidden nodes 
     * 3 output nodes which tell the spider how to react - either Panic (deducts health), Attack (deducts from weapon) or Hide (which deducts from health because no one likes a sissy)
   * Training data and expected data are contained inside the fight class and are used to train the Back Propogation Trainer
     
   
   

  


	

