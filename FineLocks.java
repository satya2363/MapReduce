import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FineLocks {

	ArrayList<String> fileRows = new ArrayList<String>();
	boolean isFib;
	public FineLocks(ArrayList<String> fileRows, boolean isFib) {
		this.isFib = isFib;
		this.fileRows = fileRows;
	}
	
	/*Calculation of the Tmax values with lock on tuples of the accumulated data structure*/
	public void calculateAverageNewLocks() {
		
		double Avg;
		long sum = 0;
		long min= Integer.MAX_VALUE;
		long max = 0;
		long timeElapsed;
		
		for(int i=0;i<10;i++) {
		long startTime = System.currentTimeMillis();
		int first = this.fileRows.size()/3;
		int second = this.fileRows.size()*2/3;
		int third = this.fileRows.size();
		
		Thread t1 =  new Thread(new ThreadsFinelock(fileRows,0,first,this.isFib));
		Thread t2 =new Thread( new ThreadsFinelock(fileRows,first,second,isFib));
		Thread t3 = new Thread ( new ThreadsFinelock(fileRows,second,third,isFib));
		
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
			long endTime = System.currentTimeMillis();
			
			timeElapsed= endTime - startTime;
			sum = sum +timeElapsed;
			if(min>timeElapsed)
				min = timeElapsed;
			if(max < timeElapsed)
				max = timeElapsed;
		}
		Avg = sum/10;
		System.out.println("FineLocks");
		System.out.println("Average = "+Avg);
		System.out.println("Minimum = "+min);
		System.out.println("Maximum = "+max);
		System.out.println();	
			PrintWriter ufoutput = null;
			
			try
			{
				File ufile = new File("FineLocks.out.txt");            
				FileWriter uoutput = new FileWriter(ufile,true);
				ufoutput = new PrintWriter(uoutput);
//				ufoutput.println(" FineLocks Avg = "+Avg + " ms");
//				ufoutput.println(" FineLocks Min = "+min + " ms");
//				ufoutput.println(" FineLocks Max = "+max + " ms");
//				for(String s :ThreadsFinelock.AccmData.keySet()){ 
//					ufoutput.println("FineLocks--->"+"Average => "+ (ThreadsFinelock.AccmData.get(s)[0] /ThreadsFinelock.AccmData.get(s)[1]) + 
//							" station=> "+ s + " sum=> "+ ThreadsFinelock.AccmData.get(s)[0] +
//							" count=> " + ThreadsFinelock.AccmData.get(s)[1]);
//				}
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
