import edu.princeton.cs.algs4.StdOut;


public class HelloGoodbye {

    public static void main(String[] args) {

        String x = args[0];
        String y = args[1];
        StdOut.println("Hello " + x + " and " + y);
        StdOut.println("Goodbye " + y + " and " + x);
    }
}
