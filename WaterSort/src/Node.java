import java.util.ArrayList;

/**
 * This class implements node functionality to be represented in a tree
 */
public class Node {

    private GameState state;
    private Node parent;
    private Integer to, from;
    private int total, score, level;

    //Constructor for children nodes
    public Node(GameState state, Node parent, int score, int level){
        this.state = state;
        this.parent = parent;
        this.score = score;
        this.level = level;
    }

    //Constructor for initial node
    public Node(GameState state) {
        this(state,  null, 0, 0);
    }

    //find node from the list with the same game state
    public static Node findNodeWithState(ArrayList<Node> nodeList, GameState gs){
        for (Node node : nodeList)
            if(gs.isSameBoard(node.state))
                return node;

        return null;
    }

    //Getters
    public Integer getTo() {
        return to;
    }

    public Integer getFrom() {
        return from;
    }

    public int getTotal() {
        return getScore() + getLevel();
    }

    public int getScore(){
        return score;
    }

    public int getLevel(){
        return level;
    }

    public GameState getState(){
        return state;
    }

    public Node getParent(){
        return parent;
    }

    //setter for two values
    public void updateToFrom(){
        from = this.state.getFrom();
        to = this.state.getTo();
    }

    //Support methods
    @Override
    public String toString(){
        return "Node: " + state;
    }

}
