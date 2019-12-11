import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class NoLocks {

	ArrayList<String> fileRows = new ArrayList<String>();
	boolean isFib;
	public NoLocks(ArrayList<String> filerows, boolean isFib) {
			this.isFib = isFib;
		this.fileRows = filerows;
	}
	/*
	 * Tmax average calculation without any locks*/
	public void calculateAverageNewLocks() {
		
		double Avg;
		long sum = 0;
		long min= Integer.MAX_VALUE;
		long max = 0;
		long timeElapsed;
		
		for(int i=0;i<10;i++) {
		long startTime = System.currentTimeMillis();
		/*
		 *offsets of splits for each thread*/
		int first = this.fileRows.size()/3;
		int second = this.fileRows.size()*2/3;
		int third = this.fileRows.size();
		
		/*New thread objects*/
		Thread t1 =  new Thread(new Threads(fileRows,0,first,this.isFib));
		Thread t2 =new Thread( new Threads(fileRows,first,second,this.isFib));
		Thread t3 = new Thread ( new Threads(fileRows,second,third,this.isFib));
		
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
			/*
			 * Average,min,max time calculation*/
			timeElapsed= endTime - startTime;
			sum = sum +timeElapsed;
			if(min>timeElapsed)
				min = timeElapsed;
			if(max < timeElapsed)
				max = timeElapsed;
		}
		
		Avg = sum/10;
		System.out.println("NoLocks");
		System.out.println("Average = "+Avg);
		System.out.println("Minimum = "+min);
		System.out.println("Maximum = "+max);
		System.out.println();
			PrintWriter ufoutput = null;
			
			try
			{
				File ufile = new File("NoLocks.out.txt");            
				FileWriter uoutput = new FileWriter(ufile,true);
//				ufoutput = new PrintWriter(uoutput);
//				ufoutput.println(" NoLocks Avg = "+Avg + " ms");
//				ufoutput.println(" NoLocks Min = "+min + " ms");
//				ufoutput.println(" NoLocks Max = "+max + " ms");
//				for(String s :Threads.AccmData.keySet()){ 
//					ufoutput.println("NoLocks--->"+"Average => "+ (Threads.AccmData.get(s)[0] /Threads.AccmData.get(s)[1]) + 
//							" station=> "+ s + " sum=> "+ Threads.AccmData.get(s)[0] +
//							" count=> " + Threads.AccmData.get(s)[1]);
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
