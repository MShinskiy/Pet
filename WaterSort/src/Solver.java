import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static java.lang.Integer.MIN_VALUE;


/**
 * Class that implements search algorithms to find solution
 */
public class Solver {

    Node rootNode;
    ArrayList<Node> unexpanded = new ArrayList<>();
    ArrayList<Node> expanded = new ArrayList<>();
    GameState initialState;

    //Constructor to set up an instance of class with a
    //node corresponding to the initial state as the root node
    public Solver(Jar[] initialBoard){
        //Validate board
        if(!isValidBoard(initialBoard))
            throw new IllegalArgumentException("Invalid Board");

        //Initialize game state with provided starting board
        GameState initialState = new GameState(initialBoard);
        this.initialState = initialState;
        rootNode = new Node(initialState);
    }

    //Search algorithm
    public void solve(PrintWriter output){
        //Add first node
        unexpanded.add(rootNode);
        int nodeNumber = 1;     //count nodes for output

        //Run logic until all nodes are expanded
        //or solution is found
        while(unexpanded.size() > 0){

            int tempScore = MIN_VALUE;  //temporary score value
            int tempTotal = MIN_VALUE;  //temporary total score value
            int index = 0;              //index of a selected node

            //Pick a node with the highest total score
            //and the highest board score
            for(Node n : unexpanded) {
                if(n.getTotal() >= tempTotal) {
                    tempTotal = n.getTotal();
                    if(n.getScore() >= tempScore) {
                        tempScore = n.getScore();
                        index = unexpanded.indexOf(n);
                    }
                }
            }

            //Expand a node
            Node n = unexpanded.get(index);
            expanded.add(n);

            output.println( "N: " + nodeNumber++ + "\n" +
                            "Score: " + n.getScore() + "\n" +
                            "Level: " + n.getLevel() + "\n" +
                            "Node: " + n.getState().toString()
                    );

            int newLevel = n.getLevel() + 1;    //update level
            unexpanded.remove(n);

            //finish if goal state found
            if(n.getState().isGoal()){
                System.out.println("Solution found!");

                output.println("-----Solution-----");
                ArrayList<Node> solution = new ArrayList<>();
                getSolutionNodeList(solution, n);
                printSolution(output, solution);
                return;
            }

            //Get scores for every new possible move
            ArrayList<GameState> moveList = n.getState().possibleMoves();
            for(GameState gs : moveList){
                if (Node.findNodeWithState(unexpanded, gs) == null &&
                    Node.findNodeWithState(expanded, gs) == null){

                    int newScore = gs.countCost(gs);
                    Node newNode = new Node(gs, n, newScore, newLevel);
                    newNode.updateToFrom();
                    unexpanded.add(newNode);
                }
            }
        }
        System.out.println("No solution found!");
    }

    //Recursive method to find solution path
    public Node getSolutionNodeList(ArrayList<Node> solution, Node n){
        if(n != null) {
            solution.add(n);
            return getSolutionNodeList(solution, n.getParent());
        }
        return null;
    }

    //Method to print solution
    public void printSolution(PrintWriter output, ArrayList<Node> solution){
        Collections.reverse(solution);  //to print solution from starting board game state
        for (Node n : solution) {
            output.println(
                    "Score: " + n.getScore() + "\n" +
                    "Level: " + n.getLevel() + "\n" +
                    "Node: " + n.getState().toString() + "\n" +
                    "Move: " + n.getFrom() + " -> " + n.getTo() + "\n"
            );
        }
    }

    //Method to validate board
    //checks whether every colour can fit completely in at least one jar
    public boolean isValidBoard(Jar[] board){
        HashMap<Integer, Integer> map = new HashMap<>();
        for (Jar j : board)
            for (Integer col : j.getCols()) {
                if(!map.containsKey(col)) map.put(col, 1);
                else map.replace(col, map.get(col) + 1);
            }

        for (Integer i : map.values())
            if(i % Jar.JAR_MAX_SIZE != 0) return false;

        return true;
    }
}
