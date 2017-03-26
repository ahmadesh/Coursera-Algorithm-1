import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
   
    private Node front;
    private Node end;
    private int n;
    private class Node {
        private Node next;
        private Node prev;
        private Item value;
    }
   // construct an empty deque
   public Deque() {
        front = new Node();
        end = new Node();
        n = 0;
   }
   
   // is the deque empty?
   public boolean isEmpty() {
       return n == 0;
   }
   
   // return the number of items on the deque
   public int size() {
       return n;
   }
   
   // add the item to the front
   public void addFirst(Item item) {
       if (item == null) throw new java.lang.NullPointerException("can not add null");
       if (this.isEmpty())
       {
           front.value = item;
           front.next = null;
           front.prev = null;
           end = front;
           n = 1;
       }
       else 
       {
           Node oldfront = front;
           front = new Node();
           front.value = item;
           front.next = oldfront;
           oldfront.prev=front;
           front.prev = null;
           n++;
       }
   }
   
   // add the item to the end
   public void addLast(Item item) {
       if (item == null) throw new java.lang.NullPointerException("can not add null");
       
       if (this.isEmpty())
       {
           end.value = item;
           end.next = null;
           end.prev = null;
           front = end;
           n = 1;
       }
       else 
       {
           Node oldend = end;
           end = new Node();
           end.value = item;
           end.prev = oldend;
           oldend.next = end;
           end.next = null;
           n++;
       }
   }
   
   // remove and return the item from the front
   public Item removeFirst() {
       if (this.isEmpty()) throw new java.util.NoSuchElementException("no item in the list");
       
       Item retitem = front.value; 
       front = front.next;
       if (front != null) front.prev = null;
       else end = null;
       n--;  
       return retitem;
   }
   
   // remove and return the item from the end
   public Item removeLast() {
       if (this.isEmpty()) throw new java.util.NoSuchElementException("no item in the list");
       Item retitem = end.value; 
       end = end.prev;
       if (end != null) end.next = null;
       else front = null;
       n--;  
       return retitem;
   }
   
   // return an iterator over items in order from front to end
   public Iterator<Item> iterator() { 
       return new ListIterator();
   }
   
   private class ListIterator implements Iterator<Item> {
    
       private Node current = front;
       
       public boolean hasNext() { return current != null;}
       
       public void remove() {throw new java.lang.UnsupportedOperationException();}
       
       public Item next() {
           if (current == null) throw new java.util.NoSuchElementException();
           Item value = current.value;
           current = current.next;
           return value;
       }
       
       
   }
   
   // unit testing
   public static void main(String[] args) {
       //Deque<Integer> test = new Deque<Integer>();
       
       //test.addLast(1);
       //test.addLast(2);
       //test.addLast(3);
       //test.removeFirst();
       //test.removeLast();
       //test.removeLast();
       
       //for (Integer i : test){
       //   System.out.println(i);
       //}  
       
   }
}