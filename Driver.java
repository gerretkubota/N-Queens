/****************************************
* Name: Takuya Gerret Kubota            *
* Project #: 2 (N-Queens Problem)       *
* CS 420                                *
*****************************************/

import java.util.Scanner;

public class Driver{
	public static void main(String[] args){
		String input = "";
		Scanner kb = new Scanner(System.in);
		char c;
		do{
			boolean stat = true;
			int count = 100;
			int solved = 0;
			double minAverage = 0.0;
			double steepestAverage = 0;
			//long result = 0;
			
			System.out.println("|------Average Time Menu------|" + 
			                   "\n1) Average time for Steepest Hill Algorithm." +
			                   "\n2) Average time for Min-Conflicts Algorithm." +
			                   "\n3) Exit.");
			System.out.print("input> ");
			c = kb.nextLine().charAt(0);
			switch(c){
		 		case '1':
		 			for(int i = count; i > 0; i--){
						NQueen n = new NQueen();
						System.out.println("|-------Initial Board------|");
						n.print();
						long startTime = System.currentTimeMillis();
						n.steepest();
						long endTime = System.currentTimeMillis();
						long result = (endTime - startTime);
						System.out.println("Time: " + result + " milliseconds.");
						steepestAverage += result;
						solved += n.getSolved();
					}
					System.out.println("Steepest Hill Algorithm");
					System.out.println("Average time took: " + (steepestAverage/100) + " milliseconds.");
					System.out.println((((double)solved/100) * 100) + "% of it was solved.");
					break;
					
				case '2':
					for(int i = count; i > 0; i--){//count; i > 0; i--){
						NQueen n = new NQueen();
						n.print();
						long startTime = System.currentTimeMillis();
						n.minConflict();
						long endTime = System.currentTimeMillis();
						stat = n.getStatus();
						long result = (endTime - startTime); /// 1000;
						System.out.println("Time: " + result + " milliseconds.");
						minAverage += result;
					}
					System.out.println("Min Conflict Algorithm");
					System.out.println("Average time took: " + (minAverage/100) + " milliseconds.");
					break;
				
				case '3':
					System.exit(0);
					
				default:
					System.out.println("Incorrect input.");
					break;
			}
		}while(input != "3");
	}
}
	