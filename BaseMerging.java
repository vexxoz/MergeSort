import java.util.Random;

/**
 * Implements various merge style algorithms.
 * 
 * Completion time: (your completion time)
 *
 * @author (your name), Acuna, Sedgewick and Wayne
 * @verison (version)
 */

public class BaseMerging {
     
    /**
     * Entry point for sample output.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Queue q1 = new ListQueue(); q1.enqueue("T"); q1.enqueue("R"); q1.enqueue("O"); q1.enqueue("L"); q1.enqueue("E");
        Queue q2 = new ListQueue(); q2.enqueue("X"); q2.enqueue("S"); q2.enqueue("P"); q2.enqueue("M"); q2.enqueue("E"); q2.enqueue("A");        
        Queue q3 = new ListQueue(); q3.enqueue(20); q3.enqueue(17); q3.enqueue(15); q3.enqueue(12); q3.enqueue(5);
        Queue q4 = new ListQueue(); q4.enqueue(18); q4.enqueue(16); q4.enqueue(13); q4.enqueue(12); q4.enqueue(4); q4.enqueue(1);       
        
        //Q1 - sample test cases
        Queue merged1 = mergeQueues(q1, q2);
        System.out.println(merged1.toString());
        Queue merged2 = mergeQueues(q3, q4);
        System.out.println(merged2.toString());
        
        //Q2 - sample test cases
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        sort(a);
        assert isSorted(a);
        show(a);
        
        //Q3 - sample test cases
        String[] b = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        shuffle(b);
        show(b);
        
        shuffle(b);
        show(b);
    }
    
    public static Queue mergeQueues(Queue<Comparable> q1, Queue<Comparable> q2) {
        Queue returnQue = new ListQueue();
        while(!q1.isEmpty() || !q2.isEmpty()) {
        	if(q1.isEmpty()) returnQue.enqueue(q2.dequeue());
        	else if(q2.isEmpty()) returnQue.enqueue(q1.dequeue());
        	else if(less(q1.front(),q2.front())) {returnQue.enqueue(q2.dequeue());}else{returnQue.enqueue(q1.dequeue());}
        }
        return returnQue;
    }
    
    public static void sort(Comparable[] a) {
        mergesort(a);
    }

    public static Comparable[] mergesort(Comparable[] a) {
    	Comparable[] newArray;
    	if(a.length == 1) return a;
    	int mid = a.length/2;
    	//if(a.length<=1) return;
    	// left side
    	Comparable[] left = new Comparable[mid];
    	for(int i=0;i<mid;i++) {
    		left[i]=a[i];
    	}
    	mergesort(left);
    	// right side
    	Comparable[] right = new Comparable[a.length-mid];
    	for(int i=mid;i<a.length;i++) {
    		right[i-mid]=a[i];
    	}
    	mergesort(right);
    	newArray = merge(left,right);
    	for(int i=0;i<newArray.length;i++) {
    		a[i]=newArray[i];
    	}
    	return newArray;
    }

    public static Comparable[] merge(Comparable[] a, Comparable[] b) {
    	Comparable[] newArray = new Comparable[a.length+b.length];
    	Comparable[] temp = new Comparable[a.length+b.length];
    	
    	for(int i=0;i<temp.length;i++) {
    		if(i<a.length) temp[i]=a[i];
    		else temp[i]=b[i-a.length];
    	}
    	
        int i = 0, j = a.length;
        
        // merge back to a[]
        for (int k = 0; k < temp.length; k++) {
            if      (i > a.length-1)            newArray[k] = temp[j++];
            else if (j > temp.length-1)         newArray[k] = temp[i++];
            else if (less(temp[j], temp[i])) 	newArray[k] = temp[j++];
            else                           		newArray[k] = temp[i++];
        }
        return newArray;
    }
    
    // This shuffle method has a Big-Oh of O(nlogn) because it very closely follows the algorithm of mergesort. 
    // Except instead of comparing values it randomly inserts them in using the randomBool function
    // which has a Big-Oh of O(1);
    public static void shuffle(Object[] a) {
        Object[] aux = new Object[a.length];
        breakDown(a, aux, 0, a.length-1);
    }
    
    private static void breakDown(Object[] a, Object[] aux, int start, int end) {
    	if (end <= start) return;
    	int mid = start+(end-start)/2;
    	
    	// left side
    	breakDown(a, aux, start, mid);
    	//right side
    	breakDown(a, aux, mid+1, end);
    	//bring together
    	shuffleMerge(a,aux,start,mid,end);
    }
    
    private static void shuffleMerge(Object[] a, Object[] aux, int start, int mid, int end) {
    	for(int i=start;i<=end;i++) {
    		aux[i]=a[i];
    	}
    	
    	int first = start, second = mid+1;
    	
        for (int i = start; i <= end; i++) {
            if      (first > mid)   a[i] = aux[first++];
            else if (second > end)   a[i] = aux[first++];
            else if (randomBool()) 	a[i] = aux[second++];
            else                    a[i] = aux[first++];
        }
    }

    private static boolean randomBool() {
    	Random rand = new Random();
    	return rand.nextBoolean();
    }
    
    //sorting helper from text
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    //sorting helper from text
    private static void show(Comparable[] a) {
        for (Comparable a1 : a)
            System.out.print(a1 + " ");

        System.out.println();
    }
    
    //sorting helper from text
    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1]))
                return false;
        
        return true;
    }
}