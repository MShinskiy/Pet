import java.util.*;

public class Jar {

    private Deque<Integer> j;
    public static final int JAR_MAX_SIZE = 4;

    //fill top to bottom
    public Jar(Integer... cols){
        j = new ArrayDeque<>();

        //fill initially
        j.addAll(List.of(cols));
    }

    public boolean isFilled(){
        int count = 0;
        for (Integer next : this.getDeque())
            if(Objects.equals(next, this.getDeque().getFirst()))
                count++;

        return count == JAR_MAX_SIZE;
    }

    public boolean isEmpty(){
        return this.getDeque().isEmpty();
    }

    //count number of different colours in the jar
    public int countCols(){
        if(this.isEmpty())
            return 0;

        int count = 1;
        Integer[] cols = this.getCols();

        for(int i = 1; i < cols.length; i++)
            if(!Objects.equals(cols[i - 1], cols[i]))
                count++;

        return count;
    }

    //pour from this jar to another jar
    public boolean pour(Jar to){
        if(!canPour(to)) return false;

        //count how many will be poured
        int countToPour = 0;
        for (Integer nextCol : this.getDeque())
            if (Objects.equals(nextCol, this.getDeque().getFirst()))
                countToPour++;
            else
                break;

        //transfer to another jar
        for(int i = 0; i < countToPour; i++)
            to.getDeque().push(this.getDeque().pop());

        return true;
    }

    //check if it is legal to pour from one jar to another
    public boolean canPour( Jar to){
        if(to.getDeque().size() == 0) return true;

        //count how many will be poured
        int countToPour = 0;
        for (Integer nextCol : this.getDeque()) {
            if (Objects.equals(nextCol, this.getDeque().getFirst()))
                countToPour++;
            else
                break;
        }

        boolean isEnoughSpace = countToPour <= JAR_MAX_SIZE - to.getDeque().size();
        boolean isSameCol = Objects.equals(this.getDeque().peek(), to.getDeque().peek());

        return isEnoughSpace && isSameCol && !(this.getDeque().size() == countToPour && to.isEmpty());
    }

    //getters
    public Deque<Integer> getDeque(){
        return this.j;
    }

    public Integer[] getCols(){
        List<Integer> cols = new ArrayList<>(j);
        Integer[] colsArr = new Integer[cols.size()];
        colsArr = cols.toArray(colsArr);
        return colsArr;
    }

    //support methods
    public Jar copy(){
        return new Jar(getCols());
    }
}
