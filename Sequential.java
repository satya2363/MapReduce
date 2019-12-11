import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Sequential {
	
	ArrayList<String> fileRows = new ArrayList<String>();
	boolean isFib;
	public Sequential(ArrayList<String> fileRows, boolean isFib) {
		this.fileRows = fileRows;
		this.isFib = isFib;
	}

	public void CalculateAverageTmaxSeq() {
		/*
		 * Let us have a hashmap with key as station id's and value an array 
		 * of floats having sum and count of tmax's for that station id.
		 * Whenever we find a Tmax in the string retrive the Tmax value 
		 * and add it to the sum of the station id(if present already)
		 * else create a new one.
		 * Similarly if the station id is already present add 1 to the count 
		 * else just create a record with 1 as count.
		 * 
		 * */
		Assignment1 a = new Assignment1();
		double Avg;
		long sum = 0;
		long min= Integer.MAX_VALUE;
		long max = 0;
		long timeElapsed;
	
		/*
		 * Running the loop for 10 times to calculate the avg, min and max
		 * */
		for(int i=0;i<10;i++) {
			
			 HashMap<String,float[]> AccmData = new HashMap<String,float[]>();
		//start time
		long startTime = System.currentTimeMillis();
		for(int k=0;k<fileRows.size();k++)
		{
			float arr[] = new float[2];
			if(fileRows.get(k).contains("TMAX")){  

				String str[] = new String[fileRows.get(k).length()]; 
				str = fileRows.get(k).split(",");
				
					if(AccmData.containsKey(str[0])) {
							if(this.isFib)
							 a.fibonacci(17);
						arr[0]= AccmData.get(str[0])[0]+ Float.parseFloat(str[3]);
						arr[1]= AccmData.get(str[0])[1]+1;
						AccmData.put(str[0],arr);
					}  
					else{
						arr[0] = Float.parseFloat(str[3]);
						arr[1] = 1;
						AccmData.put(str[0],arr);
					}
					
			}
		}
	
		// end time
		long endTime = System.currentTimeMillis();
		timeElapsed = endTime - startTime;
		sum = sum + timeElapsed;
		if(min>timeElapsed)
			min = timeElapsed;
		if(max < timeElapsed)
			max = timeElapsed;
		
		}
		
		Avg = sum/10;
		System.out.println("Sequential");
		System.out.println("Average = "+Avg);
		System.out.println("Minimum = "+min);
		System.out.println("Maximum = "+max);
		System.out.println();
		PrintWriter ufoutput = null;
		
		try
		{
			File ufile = new File("Sequential.out.txt");            
			FileWriter uoutput = new FileWriter(ufile,true);
//			ufoutput = new PrintWriter(uoutput);
//			ufoutput.println(" Sequential Avg = "+Avg + " ms");
//			ufoutput.println(" Sequential Min = "+min + " ms");
//			ufoutput.println(" Sequential Max = "+max + " ms");
//			
//			for(String s : AccmData.keySet()){ 
//				ufoutput.println("Sequential--->"+"Average => "+ (AccmData.get(s)[0] /AccmData.get(s)[1])+ " station=> "+ s + " sum=> "+ AccmData.get(s)[0] + " count=> " + AccmData.get(s)[1]);
//			}
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
