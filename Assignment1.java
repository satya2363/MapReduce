
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Assignment1 {

	public Assignment1() {}

	public static void main(String[] args) throws IOException  {
		
		String fileName;
		boolean isFib;
		ArrayList<String> fileRows = new ArrayList<String>();
		
		/*Calling all the versions calculate average methods*/
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter file name or path");
		fileName = sc.nextLine();
		System.out.println("with Fibonacci enter true/false");
		
		isFib = Boolean.parseBoolean(sc.nextLine());
		
		readFile(fileName,fileRows);
		
		Sequential seq = new Sequential(fileRows,isFib);
		seq.CalculateAverageTmaxSeq();
		
		NoLocks noLock = new NoLocks(fileRows,isFib);
		noLock.calculateAverageNewLocks();
		
		CoarseLock coarseLock = new CoarseLock(fileRows,isFib);
		coarseLock.calculateAverageNewLocks();

		FineLocks fineLock = new FineLocks(fileRows,isFib);
		fineLock.calculateAverageNewLocks();
		
		ShareNothing shareLock = new ShareNothing(fileRows,isFib);
		shareLock.calculateAverageNewLocks();
	}
 

	/*
	 * File reader routine*/
	private static ArrayList<String> readFile(String fileName, ArrayList<String> fileRows)  {
		
		try {
			FileReader filereader = new FileReader(fileName);
			BufferedReader br = new BufferedReader(filereader);
			String row;
			while((row = br.readLine())!=null) {
				fileRows.add(row);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			
		}
		return fileRows;
	}
	
	public int fibonacci(int n) {
		if(n == 0)
			return 0;
		else if(n==1)
			return 1;
		else return fibonacci(n-1) + fibonacci(n-2);
		
	}

}
