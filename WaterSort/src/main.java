import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;

public class main {
    /**
     * 1 - blue
     * 2 - light blue
     * 3 - green
     * 4 - light green
     * 5 - red
     * 6 - orange
     * 7 - purple
     * 8 - pink
     * 9 - gray
     *10 - yellow
     *11 - dark green
     *12 - brown
     *
     */

    /* Level 99
            new Jar(2, 9, 1, 2),
            new Jar(6, 5, 4, 8),
            new Jar(4, 2, 8, 9),
            new Jar(6, 2, 9, 6),
            new Jar(4, 5, 1, 6),
            new Jar(8, 4, 1, 5),
            new Jar(5, 1, 8, 9),
            new Jar(),
            new Jar()
     */

    //Initial board
    private static final Jar[] initialBoard = new Jar[] {
            new Jar(1, 11, 2, 2),
            new Jar(7, 8, 3, 9),
            new Jar(6, 7, 5, 12),
            new Jar(6, 8, 5, 6),
            new Jar(11, 5, 10, 1),
            new Jar(10, 11, 12, 11),
            new Jar(12, 7, 5, 4),
            new Jar(4, 7, 8, 4),
            new Jar(3, 9, 2, 1),
            new Jar(12, 10, 9, 3),
            new Jar(9, 10, 4, 1),
            new Jar(3, 2, 8, 6),
            new Jar(),
            new Jar()
    };

    public static void main(String[] args) throws FileNotFoundException {
        //Collections.reverse(Arrays.asList(initialBoard));

        long sTime = System.currentTimeMillis();            //time solution
        Solver problem = new Solver(initialBoard);
        File outFile = new File("output.txt");
        PrintWriter output = new PrintWriter(outFile);
        problem.solve(output);

        long eTime = System.currentTimeMillis();
        long time = eTime - sTime;
        long m = time/1000/60;
        long s = ((time/1000) - m*60) % 60;
        long ms = time - m*1000*60 - s*1000;

        System.out.println("Time taken: " +
                m + "m " +
                s + "s " +
                ms + "ms "
        );

        output.close();
    }
}
