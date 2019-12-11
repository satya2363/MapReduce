import java.util.ArrayList;
import java.util.HashMap;

public class ThreadShareNothing implements Runnable {

	ArrayList<String> fileRows = new ArrayList<String>();
	int offset1;
	int offset2;
	boolean isFib;
	Assignment1 a = new Assignment1();
	//Not a shared data structure
	public HashMap<String,float[]> AccmData = new HashMap<String,float[]>();
	public ThreadShareNothing(ArrayList<String> fileRows, int offset1, int offset2, boolean isFib) {
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
					if(AccmData.containsKey(str[0])) {
						if(!str[3].equals(null)) {
							if(this.isFib)
								a.fibonacci(17);
						arr[0]= AccmData.get(str[0])[0]+ Float.parseFloat(str[3]);
						arr[1]= AccmData.get(str[0])[1]+1;
						AccmData.put(str[0],arr);
						
						}
					}
					else{
						
						arr[0] = Float.parseFloat(str[3]);
						arr[1] = 1;
						AccmData.put(str[0],arr);
					}	
			}
		}
	
	}
}
