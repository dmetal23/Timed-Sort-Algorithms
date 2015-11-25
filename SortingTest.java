 /*
Daniel Rojas
Math 482 Fall 2015
 */
 
 
import java.util.*; 

public abstract class SortingTest { 
   int[] data; 
   protected String name; 
   
   protected SortingTest() { 
      name = "SortingTest"; data = null; 
   } 
   protected SortingTest(String n) { 
      this(); name = n; 
   } 
	
   public abstract void sort(); 

   protected boolean isValid(int index) { 
      return index >= 0 && index < data.length;
      
   } 
	
   protected void checkedswap(int a, int b) { 
      int temp = data[a];
      if(isValid(a) && isValid(b)) {
         data[a] = data[b];
         data[b] = temp; 
      }
      else
         System.out.println("Invalid positions");
   } 
	
   protected void uncheckedswap(int a, int b) { 
      int temp = data[a];
      data[a] = data[b];
      data[b] = temp;
   } 
	
   protected boolean isSorted(boolean ascending) {
      if(ascending) { 
         for(int i=0;i<data.length-1;i++){
            if (data[i] > data[i+1]) {
               return false;
            }
         }
         return true;
      }
      else {
         for(int i=0;i<data.length-1;i++){
            if (data[i] < data[i+1]) {
               return false;
            }
         }
         return true;
      }
   }
   
   public void generate(int n, int range) { //performs a random permutation on the array
      data = new int[n];
      for(int i = 0; i< data.length; i++) {
         int x = (int)Math.floor(Math.random()*range);
         data[i] = x;
      }
   } 
	
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
	
   public long timedsort() { 
      long x = System.currentTimeMillis();
      sort();
      long y = System.currentTimeMillis();
      long xy = y - x;
      return xy;
   } 

   public static <T extends SortingTest> void basicSortTest(T s, 
   int n, int range) { 
      System.out.println("Basic Sort Test: Sort = " + s.name); 
      s.generate(n, range); 
      System.out.println(s); 
      s.sort(); 
      System.out.println(s); 
      System.out.println("Sorted?:" + s.isSorted(true)); 
      System.out.println(); 
   } 
   
   public static void doSortingTestTest(SortingTest[] sorters, int n, 
   int range) { 
      for (int i=0; i<sorters.length; i++) { 
         basicSortTest(sorters[i],n,range); 
      } 
   } 
   
   public static <T extends SortingTest> void timedSortTest(T s, 
   int[] values) { 
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
	
   public static void main(String[] args) {
      SortingTest[] sorters = {new InsertionSort()}; 
      int[] val = new int[17]; //creating subarray with 2^i number of values where i<=16.
      for(int i = 0; i< val.length; i++) {
         int x = (int)Math.pow(2,i);
         val[i] = x;
      }
      SortingTest.basicSortTest(new MergeSort(), 5, 5); //proof that merge sort works
      SortingTest.timedSortTest(new MergeSort(),val);  //merge sort timed test 
      SortingTest.doTimedSortTest(sorters,val); //insertion sort timed test
   
   } 
}

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

