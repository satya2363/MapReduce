import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CoarseLock {

	ArrayList<String> fileRows = new ArrayList<String>();
	boolean isFib;
	public CoarseLock(ArrayList<String> fileRows, boolean isFib) {
		this.fileRows = fileRows;
		this.isFib = isFib;
	}

	/*Tmax average calculation with lock on the whole data structure*/
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
	
	Thread t1 =  new Thread(new ThreadsCoarse(fileRows,0,first,isFib));
	Thread t2 =new Thread( new ThreadsCoarse(fileRows,first,second,isFib));
	Thread t3 = new Thread ( new ThreadsCoarse(fileRows,second,third,isFib));
	
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
	System.out.println("CoarseLock");
	System.out.println("Average = "+Avg);
	System.out.println("Minimum = "+min);
	System.out.println("Maximum = "+max);
	System.out.println();
		PrintWriter ufoutput = null;
		
		try
		{
			File ufile = new File("Coarse.out.txt");            
			FileWriter uoutput = new FileWriter(ufile,true);
			ufoutput = new PrintWriter(uoutput);
//			ufoutput.println(" CoarseLock Avg = "+Avg + " ms");
//			ufoutput.println(" CoarseLock Min = "+min + " ms");
//			ufoutput.println(" CoarseLock Max = "+max + " ms");
//			for(String s :ThreadsCoarse.AccmData.keySet()){ 
//				ufoutput.println("CoarseLocks--->"+"Average => "+ (ThreadsCoarse.AccmData.get(s)[0] /ThreadsCoarse.AccmData.get(s)[1]) + 
//						" station=> "+ s + " sum=> "+ ThreadsCoarse.AccmData.get(s)[0] +
//						" count=> " + ThreadsCoarse.AccmData.get(s)[1]);
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
