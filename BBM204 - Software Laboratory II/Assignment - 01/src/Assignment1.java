import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Assignment1 {
	static void InsertionSort(int array[]) {
		int lenght = array.length;
		for(int i = 1; i < lenght; i++) {
			int key = array[i];
			int j = i - 1;
			while(j >= 0 && array[j]>key) {
				array[j + 1] = array[j];
				j = j - 1;
			}
			array[j+1] = key;
		}
	}
	static void SelectionSort(int array[]) {
		int i,n,min,temp;
		n = array.length;
		for(i = 0;i<n-1;i++) {
			min = i;
			for(int j = i+1;j<n;j++) {
				if(array[j] < array[min]) {
					min = j;
				}
			}
			if(min != i) {
				temp = array[min];
				array[min] = array[i];
				array[i] = temp;
			}
		}
	}
	static int GetMax(int[] array, int length) {
		int max = array[0];
		for(int i = 1; i < length; i++) {
			if(array[i]>max) {
				max = array[i];
			}
		}
		return max;
	}
	static void Radix(int array[]) {
		int m = GetMax(array,array.length);
        for (int exp = 1; m/exp > 0; exp *= 10) {
        	RadixSort(array, exp);
        }
	}
	static void RadixSort(int array[],int digit) {
		for(int j = 1; j<=digit; j++) {
			int count[] = new int[10];
			Arrays.fill(count, 0);
			int n = array.length;
			for(int i = 0;i<n;i++){
				count[(array[i]/j)%10]++;
			}
			for(int i = 1;i<10;i++) {
				count[i]= count[i] + count[i-1];
			}
			int result[] = new int[n];
			for(int k = n-1; k>=0;k--) {
				result[count[(array[k]/j)%10]-1] = array[k];
				count[(array[k]/j)%10]--;
			}
			for (int i = 0; i < n; i++) 
		            array[i] = result[i]; 
		}
	}
	static void MergeSort(int array[],int l,int r) {
		if (l < r) {
            int m = (l+r)/2; 
            MergeSort(array, l, m); 
            MergeSort(array , m+1, r); 
            Merge(array, l, m, r); 
        }
	}
	static void Merge(int arr[], int l, int m, int r) { 
        int n1 = m - l + 1; 
        int n2 = r - m; 
        int L[] = new int [n1]; 
        int R[] = new int [n2]; 
        for (int i=0; i<n1; ++i) 
            L[i] = arr[l + i]; 
        for (int j=0; j<n2; ++j) 
            R[j] = arr[m + 1+ j]; 
        int i = 0, j = 0;
        int k = l; 
        while (i < n1 && j < n2) { 
            if (L[i] <= R[j]) { 
                arr[k] = L[i]; 
                i++; 
            } 
            else { 
                arr[k] = R[j]; 
                j++; 
            } 
            k++; 
        }
        while (i < n1) { 
            arr[k] = L[i]; 
            i++; 
            k++; 
        }
        while (j < n2) { 
            arr[k] = R[j]; 
            j++; 
            k++; 
        } 
    }
	static int BinarySearch(int array[],int Value) {
		int L = 0;
		int R = array.length - 1;
		while (L<=R) {
			int middle = (L+R)/2;
			if(array[middle] < Value)
				L = middle + 1;
			else if(array[middle] > Value)
				R = middle - 1;
			else
				return middle;
		}
		return -1;
	}
	static void PrintArray(int array[]) { 
        int length = array.length; 
        for (int i = 0; i < length; ++i) 
            System.out.print(array[i] + " "); 
  
        System.out.println(); 
    }
	static void Create() throws IOException {
        FileOutputStream out = new FileOutputStream("output.txt");
        out.close();
	}
	static void Print(String line) throws IOException {
		FileWriter fw = new FileWriter("output.txt", true);
	    BufferedWriter bw = new BufferedWriter(fw);
	    PrintWriter out = new PrintWriter(bw);
	    out.println(line);
	    out.close();
	}
	static void AvarageTest(int type) throws IOException {
		Random rand = new Random();
		long temp[]=new long[10];
		long startTime = 0 ,elapsedTime = 0;
		if(type==3) {
			for(int j = 100; j<501;j=j+25) {
				for(int k = 0; k<10; k++) {
					int array[] = new int[j];
					for (int i = 0; i < j; i++) {
						array[i] = rand.nextInt(2000);
			        }
					startTime = System.nanoTime();
		        	Radix(array);
		        	elapsedTime = System.nanoTime() - startTime;
			        temp[k] = elapsedTime;
				}
				int Avarage = (int) ((temp[0] + temp[1] + temp[2] + temp[3] + temp[4] + temp[5] + temp[6] + temp[7] + temp[8] + temp[9])/10);
				Print(j +","+Avarage);
			}
		}else{
			if(type == 2) {
				for(int j = 1000; j<25001;j=j+1600) {
					for(int k = 0; k<6; k++) {
						int array[] = new int[j];
						for (int i = 0; i < j; i++) {
							array[i] = rand.nextInt(50000);
				        }
						startTime = System.nanoTime();
						SelectionSort(array);
			        	elapsedTime = System.nanoTime() - startTime;
				        temp[k] = elapsedTime;
					}
					int Worst = (int) ((temp[0] + temp[1] + temp[2] + temp[3] + temp[4] + temp[5])/10);
					Print(j +","+Worst);
					}
			}else {
				for(int j = 1000; j<25001;j=j+1000) {
					for(int k = 0; k<10; k++) {
						int array[] = new int[j];
						if(type != 5) {
							for (int i = 0; i < j; i++) {
								array[i] = rand.nextInt(50000);
					        }
							startTime = System.nanoTime();
						}
				        switch(type) {
					        case 1:
					        	InsertionSort(array);
					        	break;
					        case 2:
					        	break;
					        case 3:
					        	break;
					        case 4:
					        	MergeSort(array, 0, array.length-1);
					        	break;
					        case 5:
					        	for (int i = 0; i < j; i++) {
									array[i] = i;
						        }
								startTime = System.nanoTime();
					        	BinarySearch(array,rand.nextInt(j));
				        }
				        elapsedTime = System.nanoTime() - startTime;
				        temp[k] = elapsedTime;
					}
					int Avarage = (int) ((temp[0] + temp[1] + temp[2] + temp[3] + temp[4] + temp[5] + temp[6] + temp[7] + temp[8] + temp[9])/10);
					Print(j +","+Avarage);
				}
			}
		}
	}
	static void BestTest(int type) throws IOException {
		long temp[]=new long[10];
		long startTime = 0 ,elapsedTime = 0;
		if(type==3) {
			for(int j = 100; j<501;j=j+25) {
				for(int k = 0; k<10; k++) {
					int array[] = new int[j];
					for (int i = 0; i < j; i++) {
						array[i] = i;
			        }
					startTime = System.nanoTime();
		        	Radix(array);
		        	elapsedTime = System.nanoTime() - startTime;
			        temp[k] = elapsedTime;
				}
				int Best = (int) ((temp[0] + temp[1] + temp[2] + temp[3] + temp[4] + temp[5] + temp[6] + temp[7] + temp[8] + temp[9])/10);
				Print(j +","+Best);
			}
		}else{
			if(type == 2) {
				for(int j = 1000; j<25001;j=j+1600) {
					for(int k = 0; k<6; k++) {
						int array[] = new int[j];
						for (int i = 0; i < j; i++) {
							array[i] = i;
				        }
						startTime = System.nanoTime();
						SelectionSort(array);
			        	elapsedTime = System.nanoTime() - startTime;
				        temp[k] = elapsedTime;
					}
					int Worst = (int) ((temp[0] + temp[1] + temp[2] + temp[3] + temp[4] + temp[5])/10);
					Print(j +","+Worst);
					}
			}else {
				for(int j = 1000; j<25001;j=j+1000) {
					for(int k = 0; k<10; k++) {
						int array[] = new int[j];
						if(type != 5) {
							for (int i = 0; i < j; i++) {
								array[i] = i;
					        }
							startTime = System.nanoTime();
						}
				        switch(type) {
					        case 1:
					        	InsertionSort(array);
					        	break;
					        case 2:
					        	break;
					        case 3:
					        	break;
					        case 4:
					        	MergeSort(array, 0, array.length-1);
					        	break;
					        case 5:
					        	for (int i = 0; i < j; i++) {
									array[i] = i;
						        }
								startTime = System.nanoTime();
					        	BinarySearch(array,((array.length)/2));
				        }
				        elapsedTime = System.nanoTime() - startTime;
				        temp[k] = elapsedTime;
					}
					int Best = (int) ((temp[0] + temp[1] + temp[2] + temp[3] + temp[4] + temp[5] + temp[6] + temp[7] + temp[8] + temp[9])/10);
					Print(j +","+Best);
				}
			}
		}
	}
	static void WorstTest(int type) throws IOException {
		long temp[]=new long[10];
		long startTime = 0 ,elapsedTime = 0;
		if(type==3) {
			for(int j = 100; j<501;j=j+25) {
				for(int k = 0; k<10; k++) {
					int array[] = new int[j];
					for (int i = j, p = 0; i > 0;i--,p++) {
						array[p] = i;
			        }
					startTime = System.nanoTime();
		        	Radix(array);
		        	elapsedTime = System.nanoTime() - startTime;
			        temp[k] = elapsedTime;
				}
				int Worst = (int) ((temp[0] + temp[1] + temp[2] + temp[3] + temp[4] + temp[5] + temp[6] + temp[7] + temp[8] + temp[9])/10);
				Print(j +","+Worst);
			}
		}else{
			if(type == 2) {
				for(int j = 1000; j<25001;j=j+1600) {
					for(int k = 0; k<6; k++) {
						int array[] = new int[j];
						for (int i = j,p=0; i > 0; i--,p++) {
							array[p] = i;
				        }
						startTime = System.nanoTime();
						SelectionSort(array);
						elapsedTime = System.nanoTime() - startTime;
				        temp[k] = elapsedTime;
					}
					int Worst = (int) ((temp[0] + temp[1] + temp[2] + temp[3] + temp[4] + temp[5])/10);
					Print(j +","+ Worst);
				}
			}else {
				for(int j = 1000; j<25001;j=j+1000) {
					for(int k = 0; k<10; k++) {
						int array[] = new int[j];
						if(type != 5) {
							for (int i = j,p=0; i > 0; i--,p++) {
								array[p] = i;
					        }
							startTime = System.nanoTime();
						}
				        switch(type) {
					        case 1:
					        	InsertionSort(array);
					        	break;
					        case 2:
					        	break;
					        case 3:
					        	break;
					        case 4:
					        	MergeSort(array, 0, array.length-1);
					        	break;
					        case 5:
					        	int control = -10;
					        	for (int i = j; i < j; i++) {
					        		if(i != control) {
					        			array[i] = i;
					        		}else{
					        			continue;
					        		}
						        }
								startTime = System.nanoTime();
					        	BinarySearch(array,control);
				        }
				        elapsedTime = System.nanoTime() - startTime;
				        temp[k] = elapsedTime;
					}
					int Worst = (int) ((temp[0] + temp[1] + temp[2] + temp[3] + temp[4] + temp[5] + temp[6] + temp[7] + temp[8] + temp[9])/10);
					Print(j +","+ Worst);
				}
			}
		}
	}
	public static void main(String args[]) throws IOException {
    	Create();
    	Print("begin worst insertion");
    	WorstTest(1);
    	Print("begin avr insertion");
    	AvarageTest(1);
    	Print("begin best insertion");
    	BestTest(1);
        Print("end\nbegin worst selection");
        WorstTest(2);
        Print("end\nbegin avr selection");
        AvarageTest(2);
        Print("end\nbegin best selection");
        BestTest(2);
        Print("end\nbegin worst radix");
        WorstTest(3);
        Print("end\nbegin avr radix");
        AvarageTest(3);
        Print("end\nbegin best radix");
        BestTest(3);
        Print("end\nbegin worst merge");
        WorstTest(4);
        Print("end\nbegin avr merge");
        AvarageTest(4);
        Print("end\nbegin best merge");
        BestTest(4);
        Print("end\nbegin worst binary");
        WorstTest(5);
        Print("end\nbegin avr binary");
        AvarageTest(5);
        Print("end\nbegin best binary");
        BestTest(5);
    }
}
