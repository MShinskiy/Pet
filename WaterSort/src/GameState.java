import java.util.ArrayList;
import java.util.Objects;

/**
 * This class implement functionality to represent a game board
 * at every step of the game
 */
public class GameState {

    private Jar[] jars;
    private int from, to;

    //Constructor
    public GameState(Jar[] jars){
        this.jars = jars;
    }

    //Count how many jar are completely filled
    public int filledJars(){
        int count = 0;
        for (Jar j : this.jars)
            if(j.isFilled())
                count++;
        return count;
    }

    //Count how many jars are completely empty
    public int emptyJars(){
        int count = 0;
        for( Jar j : this.jars)
            if(j.isEmpty())
                count++;
        return count;
    }

    //Copy of current game state
    public GameState copy(){
        Jar[] copy = new Jar[this.jars.length];
        for(int i = 0 ; i < this.jars.length; i++) copy[i] = this.jars[i].copy();
        return new GameState(copy);
    }

    //Whether is a goal state
    public boolean isGoal(){
        for (Jar j : this.jars)
            if(!(j.isEmpty() || j.isFilled())) return false;

        return true;
    }

    //Whether jars config are the same
    public boolean isSameBoard(GameState gs){
        for(int i = 0; i < this.jars.length; i++){
            Integer[] thisCols = this.jars[i].getCols();
            Integer[] thatCols = gs.jars[i].getCols();

            if(thisCols.length != thatCols.length)
                return false;

            for (int j = 0; j < thisCols.length; j++)
                if(!(Objects.equals(thisCols[j], thatCols[j])))
                    return false;
        }
        return true;
    }

    //Count score of change of states (weight of edge)
    public int countCost(GameState currentGS){
        int count = 0;
        count += currentGS.filledJars()*10;

        //Higher score for less unconnected colours
        for(int i  = 0; i < currentGS.jars.length; i++) {
            int countCols = currentGS.jars[i].countCols();
            count += countCols > 0 ? Jar.JAR_MAX_SIZE / countCols : Jar.JAR_MAX_SIZE;
        }

        return count;
    }

    //find possible moves
    public ArrayList<GameState> possibleMoves(){
        ArrayList<GameState> moves = new ArrayList<>();

        for(int i = 0; i < this.jars.length - 1; i++)
            for(int j = 0; j < this.jars.length; j++) {
                if (i == j) continue;
                if (this.jars[i].canPour(this.jars[j])) {
                    GameState copyGS = this.copy();
                    copyGS.jars[i].pour(copyGS.jars[j]);
                    copyGS.setFrom(i+1);
                    copyGS.setTo(j+1);

                    if (!moves.contains(copyGS))
                        moves.add(copyGS);          //add new possible move
                }
            }

        return moves;
    }

    @Override
    public String toString(){
        Jar[] copyJarArr = copy().jars;
        StringBuilder sb = new StringBuilder("");
        for(int i = 1; i < copyJarArr.length+1; i++)
            sb.insert(0, String.format(" %2d ", i));

        sb.insert(0, "\n");
        for(int i = 0; i < 4; i++){
            for (Jar j : copyJarArr) {
                if(j.getDeque().size() > 0) {
                    int x = j.getDeque().removeLast();
                    sb.insert(0, String.format("|%2d|", x));
                } else sb.insert(0, "|  |");
            }
            sb.insert(0, "\n");
        }
        return sb.toString();
    }

    //setters
    public void setFrom(int from){
        this.from = from;
    }

    public void setTo(int to){
        this.to = to;
    }

    //getters
    public int getFrom(){
        return this.from;
    }

    public int getTo(){
        return this.to;
    }
}
