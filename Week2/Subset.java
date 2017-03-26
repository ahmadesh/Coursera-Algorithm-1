import edu.princeton.cs.algs4.StdIn;

public class Subset {
    public static void main(String[] args) 
    {      
      RandomizedQueue<String> ranque = new RandomizedQueue<String>();
       int k = Integer.parseInt(args[0]); 
       
       while (!(StdIn.isEmpty())) {
          ranque.enqueue(StdIn.readString());
       }
       
       for (int i = 0; i < k; i++) {
           System.out.println(ranque.dequeue());
       }
         
    }
}

