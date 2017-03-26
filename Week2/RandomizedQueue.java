import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] s;
    private int N;
    
   // construct an empty randomized queue
   public RandomizedQueue() {
       s = (Item[]) new Object[1];
       N = 0;
   }
   // is the queue empty?
   public boolean isEmpty() {
       return N == 0;
   }
   
   // return the number of items on the queue
   public int size() {
       return N;
   }
       
   // add the item
   public void enqueue(Item item) { 
       if (item == null) throw new java.lang.NullPointerException("can not add null");
       if (N > (s.length-1)) resize(2 * s.length); 
       s[N++] = item;
   }
   
   // remove and return a random item
   public Item dequeue() {
       if (this.isEmpty()) throw new java.util.NoSuchElementException("no item in the list");
       int randNum = StdRandom.uniform(0, N);    
       Item item = s[randNum];
       s[randNum] = s[N-1]; 
       s[N-1] = null;
       N--;              
       if (N < s.length / 2) resize(s.length / 2);
       return item;
   }
   
   // return (but do not remove) a random item
   public Item sample() {
       if (this.isEmpty()) throw new java.util.NoSuchElementException("no item in the list");
       int randNum;
       do 
       {
           randNum = StdRandom.uniform(0, s.length);
       } while (s[randNum] == null);
       
       return s[randNum];
   }
   
   // return an independent iterator over items in random order
   public Iterator<Item> iterator() { 
       return new ListIterator();
   }
   
   private class ListIterator implements Iterator<Item> {
       private int nn = N;
       private Item[] copy = s;
       
       public boolean hasNext() { 
           if (nn > 0) return true;
           else return false;
       }
       
       public void remove() {throw new java.lang.UnsupportedOperationException();}
       
       public Item next() {
           int randNum = StdRandom.uniform(0, nn);    
           Item item = copy[randNum];
           copy[randNum] = s[nn-1]; 
           s[nn-1] = null;
           nn--;
           return item;
       }   
   }
   
   private void resize(int capacity) {
       Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++)
        {
            copy[i] = s[i];
        }
        s = copy;
    }
   
   // unit testing
   public static void main(String[] args) {
       
      RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
      System.out.println(test.sample());
       
       /*for (int i=0; i<5;i++){
          test.enqueue(i); 
       }
      
      for (int i=0; i<20;i++){
          System.out.println(test.dequeue());  
          System.out.println(test.N);  
      }

       
       
      System.out.println(test.dequeue());
              System.out.println(test.dequeue());
       System.out.println(test.N);
       
       for (Integer i : test){
           System.out.println(i);    
       }
    */   
}
   
}