import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        double count = 0;

        String x;
        String champ = "";
 
        while (!StdIn.isEmpty()) {
            count++;
            x = StdIn.readString();

            if (StdRandom.bernoulli((1.0 / count))) {
                champ = x;

            }

        }
        StdOut.println(champ);
    }
}
