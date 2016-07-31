 /*
Daniel Rojas
Combinatorial Algorithms, Fall 2015
California State University Northrdige 
 */
 
 
import java.util.*; 

public abstract class SortingTest { 
   int[] data; //our data structure where we will store our numbers 
   protected String name; 
   
   protected SortingTest() { 
      name = "SortingTest"; data = null; 
   } 
   protected SortingTest(String n) { 
      this(); name = n; 
   } 
	
   public abstract void sort(); 

   protected boolean isValid(int index) { //method to check if our position is within bounds of our array.
      return index >= 0 && index < data.length; //if index is a positive number and it is less than the size of the array, it is valid. 
      
   } 
	
   protected void checkedswap(int a, int b) { //method that checks for valid position between two indexes and performs a swap. 
      int temp = data[a]; //a temporary position value set to the index of 'a' in our array. 
      if(isValid(a) && isValid(b)) {
         data[a] = data[b]; //the value at index 'a' is now set to the value at index 'b'. 
         data[b] = temp;  //the value at index 'b' is now set to the value of our temporary position value. The swap is now complete. 
      }
      else
         System.out.println("Invalid positions"); 
   } 
	
   protected void uncheckedswap(int a, int b) { //method that works similarly to checkedswap, only it does not check for valid positions. (Unsafe) 
      int temp = data[a];
      data[a] = data[b];
      data[b] = temp;
   } 
	
   protected boolean isSorted(boolean ascending) { //method to check if our array is sorted in ascending order. 
      if(ascending) { //if our array is already in ascending order
         for(int i=0;i<data.length-1;i++){ //we iterate through the array
            if (data[i] > data[i+1]) { //if the value at index i is greater than its adjacent value for any of our iterations
               return false; //then our array must not be sorted
            }
         }
         return true; //if our iteration check does not return false, the array must be sorted. 
      }
      else { //if our array is not in descending order
         for(int i=0;i<data.length-1;i++){ //we pretty much do the same thing only check if index i is less than its adjacent index. 
            if (data[i] < data[i+1]) { //if the value at index i is less than its adjacent value for any of our iterations 
               return false; //then our array must not be sorted
            }
         }
         return true;  //if our iteration check does not return false, the array must be sorted. 
      }
   }
   
   public void generate(int n, int range) { //method that performs a random permutation on the array. n = size of our sample, range = upper bound on our random range of values. 
      data = new int[n]; //create a new data structure of size n 
      for(int i = 0; i< data.length; i++) { //for every index in our array
         int x = (int)Math.floor(Math.random()*range); //we shall generate a random number within our range. 
         data[i] = x; //then we will set our current index to our random value. 
      }
   } 
	
  /***
  Methods to print our array
  ***/
 
   public String toString() { 
      return toString(0,data.length-1); 
   } 
	
   public String toString(int start, int stop) { 
      return toString(data); 
   } 
	
   public static String toString(int[] data) { 
      if (data==null || data.length==0) 
         return "empty"; 
      else { 
         String s = ""; 
         for (int i=0; i<data.length; i++) { 
            s += String.format("%4d",data[i]); 
         } 
         return s; 
      } 
   } 
	
   
   public long timedsort() { //method that returns the time our sort took to complete. 
      long x = System.currentTimeMillis(); //check current system time
      sort(); //perform sort
      long y = System.currentTimeMillis(); //check our new system time after sort 
      long xy = y - x; //take away the difference 
      return xy; //return the difference 
   } 
   
   /*************
   TESTING FUNCTIONS
   **************/

   public static <T extends SortingTest> void basicSortTest(T s, int n, int range) { //A basic sorting test using a generic-type 's' input which represents our sorting algorithm.
      System.out.println("Basic Sort Test: Sort = " + s.name); //first print the sort that we are currently using
      s.generate(n, range); //generate a random permutation of n numbers for our sort using upper bound range
      System.out.println(s); //print the current sort
      s.sort(); //actually sort 
      System.out.println(s); //print the final sort
      System.out.println("Sorted?:" + s.isSorted(true)); //check if we have completed our sort. 
      System.out.println(); 
   } 
   
   public static void doSortingTestTest(SortingTest[] sorters, int n, int range) { 
      for (int i=0; i<sorters.length; i++) { 
         basicSortTest(sorters[i],n,range); 
      } 
   } 
   
   public static <T extends SortingTest> void timedSortTest(T s, int[] values) { 
      System.out.println("Timed Sort Test: Sort = " + s.name); 
      System.out.println("# of values  Time(ms) Sorted?");
      for (int i=0; i<values.length; i++) { 
         s.generate(values[i], values[i]); //calling generate to randomize our array
         long t = s.timedsort(); 
         System.out.println(String.format( 
            "%8d %10d   %b",values[i],t,s.isSorted(true))); 
      } 
   } 
   public static void doTimedSortTest(SortingTest[] sorters, int[] values) { 
      for (int i=0; i<sorters.length; i++) { 
         timedSortTest(sorters[i],values); 
      } 
   }
	
   /*****
   main will act as our driver for now which will run our test cases.
   *****/
   
   public static void main(String[] args) {
      SortingTest[] sorters = {new InsertionSort()}; 
      int[] val = new int[17]; //creating subarray with 2^i number of values where i<=16. 
      for(int i = 0; i< val.length; i++) { //we shall fill up our subarray with random values 
         int x = (int)Math.pow(2,i);
         val[i] = x;
      }
      SortingTest.basicSortTest(new MergeSort(), 5, 5); //proof that merge sort works
      SortingTest.timedSortTest(new MergeSort(),val);  //merge sort timed test 
      SortingTest.doTimedSortTest(sorters,val); //insertion sort timed test
   
   } 
}

/*****
SORTING ALGORITHMS
*****/

class QuickSort extends SortingTest { 
   public QuickSort() { super("QuickSort"); }
   public void sort() { 
      sort(0,data.length-1); 
   } 
   	
   public void sort(int start, int stop) { 
      if (stop<=start) 
         return; 
      else if (stop-start == 1) 
         if (data[start]>data[stop]) 
            checkedswap(start,stop); 
         else { 
            int p = partition(start,stop); 
            sort(start,p-1); 
            sort(p+1,stop); 
         } 
   }
   	 
   int partition(int start, int stop) {
      int pivot = data[start]; 
      int pivotindex = start; 
      checkedswap(pivotindex,stop); 
      for (int i=start; i<stop; i++) { 
         if (data[i]<=pivot) { 
            checkedswap(i,pivotindex); 
            pivotindex++; 
         } 
      } 
      checkedswap(pivotindex,stop); 
      return pivotindex;  
   } 
}

class ShellSort extends SortingTest { 
   public ShellSort() { super("ShellSort"); } 
   public void sort() { 
      int temp;
      for (int i = 1; i < data.length; i++) {
         for(int j = i ; j > 0 ; j--){
            if(data[j] < data[j-1]){
               temp = data[j];
               data[j] = data[j-1];
               data[j-1] = temp;
            }
         }
      }
   }
}

class BubbleSort extends SortingTest { 
   public BubbleSort() { super("BubbleSort"); } 
   public void sort() { 
      for(int i=0; i<data.length; i++){
         for(int j=1; j<data.length; j++){
            if(data[j]< data[j-1] ){
               int temp = data[j];
               data[j] = data[j-1];
               data[j-1] = temp;            
            }
         }
      }
   }
}
	
class SelectionSort extends SortingTest { 
   public SelectionSort() { super("SelectionSort"); } 
   public void sort() { 
      int min;
      for (int i = 0; i < data.length; i++) {
         min = i;
         for (int j = i + 1; j < data.length; j++) {
            if (data[j] < data[min]) {
               min = j;
            }
         }
         if (min != i) {
            final int temp = data[i];
            data[i] = data[min];
            data[min] = temp;
         }
      }
   }
}

class InsertionSort extends SortingTest { 
   public InsertionSort() { super("InsertionSort"); } 
   public void sort() { 
      int temp;
      for (int i = 1; i < data.length; i++) {
         for(int j = i ; j > 0 ; j--){
            if(data[j] < data[j-1]){
               temp = data[j];
               data[j] = data[j-1];
               data[j-1] = temp;
            }
         }
      }
   }
}

class MergeSort extends SortingTest { 

   public MergeSort() { super("MergeSort"); } 
   public void sort() { 
      sort(0,data.length-1); 
   } 
   private void sort(int start, int stop) { 
      if (stop-start<=0) { 
         return; 
      } 
      else if (stop-start==1) { 
         if (data[start]>data[stop]) checkedswap(start,stop); 
      } 
      else { 
         int midpoint = start + (stop-start)/2; 
         sort(start,midpoint); 
         sort(midpoint+1,stop); 
         merge(start,midpoint,stop); 
      } 
   }
	
   private void merge(int start, int midpoint, int stop) { 
      int[] temp = new int[stop-start+1]; 
      int lindex = start; 
      int rindex = midpoint+1; 
      int tindex = 0; 
      while (lindex<=midpoint && rindex<=stop) { 
         if (data[lindex]<=data[rindex]) { 
            while (lindex<=midpoint && 
            data[lindex]<=data[rindex]) { 
               temp[tindex++] = data[lindex++]; 
            } 
         } 
         else { 
            while (rindex<=stop && 
            data[rindex]<data[lindex]) { 
               temp[tindex++] = data[rindex++]; 
            } 
         } 
      } 
      if (lindex<=midpoint) { 
         while (lindex<=midpoint) { 
            temp[tindex++] = data[lindex++]; 
         } 
      } 
      else { 
         while (rindex<=stop) { 
            temp[tindex++] = data[rindex++]; 
         } 
      } 
      for (int i=0; i<temp.length; i++) data[start+i] = temp[i]; 
   }  
}

