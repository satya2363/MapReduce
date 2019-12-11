 import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadsFinelock implements Runnable {

		// shared data structure must be a class variable because 
		// different threads must be able to manipulate a the same data structure
		// Or else each thread object will change its own data structured and it is no more
		// a shared data structure.
		public static  Map<String,float[]> AccmData =  new ConcurrentHashMap<String,float[]>();
	
	static int numberOfThreads=0;
	ArrayList<String> fileRows = new ArrayList<String>();
	int offset1;
	int offset2;
	boolean isFib;
	Assignment1 a = new Assignment1();
	public ThreadsFinelock(ArrayList<String> fileRows, int offset1, int offset2, boolean isFib) {
		numberOfThreads++;
		this.offset1 = offset1;
		this.offset2 = offset2;
		this.fileRows = fileRows;
		this.isFib = isFib;
	}

	@Override
	public void run() {
		for(int i=offset1;i<offset2;i++)
		{ 
			float arr[] = new float[2];
			if(fileRows.get(i).contains("TMAX")){   
				String str[] = new String[fileRows.get(i).length()]; 
				str = fileRows.get(i).split(",");

				
				arr[0] = Float.parseFloat(str[3]);
				arr[1] = 1;
				while(true){
					/*
					 * put a particular row only if the particular key is absent
					 * If it is absent the method returns null
					 * else it returns the value
					 * In concurrent hashmap put if absent method is inherently synchronized on the tuples
					 * that's the reason we do not have to explicitly put a lock*/
				float[] ar = AccmData.putIfAbsent(str[0],arr);
				if(ar!= null){
					if(!str[3].equals(null)) {
						if(isFib)
							a.fibonacci(17);
					arr[0]= AccmData.get(str[0])[0]+ Float.parseFloat(str[3]);
					arr[1]= AccmData.get(str[0])[1]+1;
					}
					/*
					 * Concurrent hashmaps are only locked while updating 
					 * However it is important to lock them while reading
					 * replace method restricts the thread to put any value if whatever 
					 * value it read previously is not present now
					 * therefore we write a while loop for the thread to comeback and
					 * process the value again.(implicit read locking).
					 * */
					if(AccmData.replace(str[0],ar,arr)){
						break;
					}
					else continue;
					}
				else break;
			}
			
			}
		}
		
	}
	}
 

 