import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class ShareNothing {
	
	ArrayList<String> fileRows = new ArrayList<String>();
	boolean isFib;
	HashMap<String,float[]> FinalData = new HashMap<String,float[]>();
	public ShareNothing(ArrayList<String> fileRows, boolean isFib) {
		this.fileRows = fileRows;
		this.isFib= isFib;
	}

	public void calculateAverageNewLocks() {
		double Avg;
		long sum = 0;
		long min= Integer.MAX_VALUE;
		long max = 0;
		long timeElapsed;
		
		
		int first = this.fileRows.size()/3;
		int second = this.fileRows.size()*2/3;
		int third = this.fileRows.size();
		
		ThreadShareNothing tfl1 = new ThreadShareNothing(fileRows,0,first,this.isFib);
		ThreadShareNothing tfl2 = new ThreadShareNothing(fileRows,first,second,this.isFib); 
		ThreadShareNothing tfl3 = new ThreadShareNothing(fileRows,second,third,this.isFib);
		
		for(int i=0;i<10;i++) {
			long startTime = System.currentTimeMillis();
		Thread t1 =  new Thread(tfl1);
		Thread t2 =new Thread(tfl2);
		Thread t3 = new Thread(tfl3);
		
		t1.start();
		t2.start();
		t3.start();
	
			try {
				t1.join();
				t2.join();
				t3.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			for(String s :tfl1.AccmData.keySet()){ 
				float[] arr = new float[2];
				if(tfl2.AccmData.containsKey(s)){
					arr[0] =  tfl1.AccmData.get(s)[0] + tfl2.AccmData.get(s)[0];
					arr[1] =  tfl1.AccmData.get(s)[1] + tfl2.AccmData.get(s)[1];
					tfl2.AccmData.put(s, arr);
				}
				else{
					arr[0] =  tfl1.AccmData.get(s)[0];
					arr[1] =  tfl1.AccmData.get(s)[1];
					tfl2.AccmData.put(s, arr);
				}
			}

			
			/*
			 * Aggregation of all the values from all the individual thread's local 
			 * data structure*/
			
			for(String s :tfl2.AccmData.keySet()){ 
				float[] arr1 = new float[2];
				if(tfl3.AccmData.containsKey(s)){
					arr1[0] =  tfl2.AccmData.get(s)[0] + tfl3.AccmData.get(s)[0];
					arr1[1] =  tfl2.AccmData.get(s)[1] + tfl3.AccmData.get(s)[1];
					tfl3.AccmData.put(s, arr1);
				}
				else{
					arr1[0] =  tfl2.AccmData.get(s)[0];
					arr1[1] =  tfl2.AccmData.get(s)[1];
					tfl3.AccmData.put(s, arr1);
				}
			}
			
			long endTime = System.currentTimeMillis();
			timeElapsed= endTime - startTime;
			sum = sum +timeElapsed;
			if(min>timeElapsed)
				min = timeElapsed;
			if(max < timeElapsed)
				max = timeElapsed;
		}
		Avg = sum/10;
		System.out.println("Share nothing");
		System.out.println("Average = "+Avg);
		System.out.println("Minimum = "+min);
		System.out.println("Maximum = "+max);
		System.out.println();	
			PrintWriter ufoutput = null;
			
			try
			{
				File ufile = new File("ShareNothing.out.txt");            
				FileWriter uoutput = new FileWriter(ufile,true);
				ufoutput = new PrintWriter(uoutput);
//				ufoutput.println(" Share nothing Avg = "+Avg + " ms");
//				ufoutput.println(" Share nothing Min = "+min + " ms");
//				ufoutput.println(" Share nothing Max = "+max + " ms");
//				for(String s :tfl3.AccmData.keySet()){ 
//					ufoutput.println("No Sharing final--->"+"Average => "+ (tfl3.AccmData.get(s)[0] /tfl3.AccmData.get(s)[1]) + 
//							" station=> "+ s + " sum=> "+ tfl3.AccmData.get(s)[0] +
//							" count=> " + tfl3.AccmData.get(s)[1]);
//				}
//				
				
				}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			finally{
				if(ufoutput!=null){
					ufoutput.close();
				}
			}
			
		
	}
	}

