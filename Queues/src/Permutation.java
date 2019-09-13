import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> strs = new RandomizedQueue<String>();


        while (!StdIn.isEmpty()) {
            strs.enqueue(StdIn.readString());
        }

        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
            StdOut.println(strs.dequeue());
        }
    }
//    {
//        int k = 4;
//        StdOut.println("k : " + k);
//
////        int n = Integer.parseInt(args[0]);
//        String alphabet = "abcdefghijklmn";
//        String elements = alphabet.substring(0, k);
//        String[] elementsArray = elements.split("");
//        RandomizedQueue<String> strs = new RandomizedQueue<String>();
//
//        for (String element : elementsArray) {
//            strs.enqueue(element);
//        }
//
//        for (int i = 0; i < k; i++) {
//            StdOut.println(strs.dequeue());
//        }
//    }

}
