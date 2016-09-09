/****************************************
* Name: Takuya Gerret Kubota            *
* Project #: 2 (N-Queens Problem)       *
* CS 420                                *
*****************************************/
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class NQueen implements Comparable<NQueen>{
	private String[][] board;
	private int hOfN;
	private final int size = 18;
	private boolean status = true;
	private int solved = 0;

	public NQueen(){
		this.board = new String[size][size];
		setDefault();
		scrambleBoard();
		//this.status = true;
	}

	public boolean getStatus(){
		return status;
	}

	public NQueen(String[][] b, int h){
		this.board = new String[size][size];
		copyBoard(b, this.board);
		this.hOfN = h;
		this.status = true;
	}
	// function that places the queen randomly
	public void scrambleBoard(){
		Random rand = new Random();
		// generates a number between 0-(N-1)
		for(int col = 0; col < size; col++){
			int srand = rand.nextInt((size-1)-0+1)+0;
			//System.out.println(srand + " " + col);
			board[srand][col] = "Q";
		}
		System.out.println();
	}
	// fills the board with x
	public void setDefault(){
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				this.board[i][j] = "X";
			}
		}
	}

	public int getH(){
		return this.hOfN;
	}

	public String[][] getBoard(){
		return this.board;
	}
	
	public int getSolved(){
		return this.solved;
	}
	
	public void steepest(){
		int counter = 0;
 		PriorityQueue<NQueen> tempQueue = new PriorityQueue<NQueen>();
		this.hOfN = calculateH(this.board);
		//System.out.println("this is current hofN: " + this.hOfN);
		//System.out.println("hello from steepest");
		// Iterate through each row of the column and check where the queen is placed.
		// When a queen is found, copy it's current state of the board to a temp board.
		// Then take out that current state's queen from that column and place the queen
		// either up or down, depending on how many spaces are above the current state's queen placement.
		// After placing each of the boards into a queue with its h, check to see if the h in the queue is smaller
		// than the current state's h, if it is, copy the board and value of h to the original board and h.
		// If the h is not smaller than the current h, then do nothing, move onto the next column
		for(int col = 0; col < size; col++){
			for(int row = 0; row < size; row++){
				if(this.board[row][col].equals("Q")){
					String[][] tempBoard = new String[size][size];
					copyBoard(this.board, tempBoard);
					//System.out.print(row + " " + col + "\n");
					tempBoard[row][col] = "X";
					int tempRow = 0;
					//System.out.println("iteration " + counter);
					// The queen is placed downward
					for(int i = (row+1); i < size; i++){
						tempBoard[i-1][col] = "X";
						tempBoard[i][col] = "Q";
						//System.out.println("going down");
						//print(tempBoard);
						tempQueue.add(new NQueen(tempBoard, calculateH(tempBoard)));
						//System.out.println("h: " + calculateH(tempBoard));
						//System.out.println("position: " + i + " " + col);
						tempRow = i;
					}

					// Remove the queen from the previous state so that it will not appear when going downward
					tempBoard[tempRow][col] = "X";
					
					// The queen is placed upward
					for(int i = (row-1); i >= 0; i--){
						tempBoard[i+1][col] = "X";
						tempBoard[i][col] = "Q";
						//System.out.println("going up");
						//print(tempBoard);
						tempQueue.add(new NQueen(tempBoard, calculateH(tempBoard)));
						//System.out.println("h: " + calculateH(tempBoard));
						//System.out.println("position: " + i + " " + col);
					}

					if((tempQueue.peek().getH()) < this.hOfN){
						this.hOfN = tempQueue.peek().getH();
						copyBoard(tempQueue.peek().getBoard(), this.board);
						//System.out.println("cheapest " + this.hOfN);
						//print();
						tempQueue.clear();
						counter++;
						break;
					}
					else{
						tempQueue.clear();
						tempBoard[row][col] = "Q";
					}
				}
			}
		}
		
		if(this.hOfN == 0){
			System.out.println("Success!!");
			System.out.println("Number of steps: " + counter);
			System.out.println("Final state: ");
			solved++;
			print();
			//status = false;
		}
		else{
			System.out.println("Nope ): h is: " + this.hOfN + " number of steps: " + counter);
			//status = true;
		}
	}
	
	// This function is the minConflict algorithm
	// This function will choose a random column and calculate
	// the heuristic for each to the coordinates of that column.
	// If it finds a heuristic that is less than or equal to the current heuristic
	// the queen will be moved there, and the processs will repeat until the solution is found.
	public void minConflict(){
 		PriorityQueue<NQueen> tempQueue = new PriorityQueue<NQueen>();
 		//System.out.println("hello from min conflict");
		this.hOfN = calculateH(this.board);
		System.out.println("this is the initial h: " + this.hOfN);
		if(this.hOfN != 0){
			Random ran = new Random();
			while(this.status){
				//System.out.println("hello");
				int col = ran.nextInt((size-1)-0+1)+0;
				for(int row = 0; row < size; row++){
					if(this.board[row][col].equals("Q")){
					String[][] tempBoard = new String[size][size];
					copyBoard(this.board, tempBoard);
					//System.out.println(row + " " + col + "\n");
					tempBoard[row][col] = "X";
					int tempRow = 0;
					//System.out.println("iteration: " + counter);
					// downward
					for(int i = (row+1); i < size; i++){
						tempBoard[i-1][col] = "X";
						tempBoard[i][col] = "Q";
						//System.out.println("going down");
						//print(tempBoard);
						tempQueue.add(new NQueen(tempBoard, calculateH(tempBoard)));
						//System.out.println("h: " + calculateH(tempBoard));
						//System.out.println("position: " + i + " " + col);
						tempRow = i;
					}

					tempBoard[tempRow][col] = "X";
					
					// The queen is placed upward
					for(int i = (row-1); i >= 0; i--){
						tempBoard[i+1][col] = "X";
						tempBoard[i][col] = "Q";
						//calculateH(tempBoard);
						//System.out.println("going up");
						//print(tempBoard);
						tempQueue.add(new NQueen(tempBoard, calculateH(tempBoard)));
						//System.out.println("h: " + calculateH(tempBoard));
						//System.out.println("position: " + i + " " + col);
					}

					if((tempQueue.peek().getH()) <= this.hOfN){
						//counter++;
						this.hOfN = tempQueue.peek().getH();
						if(this.hOfN == 0){
							copyBoard(tempQueue.peek().getBoard(), this.board);
							tempQueue.clear();
							print();
							System.out.println("h of n: " + this.hOfN);
							//System.out.println("steps: " + counter);
							this.status = false;
							break;
						}
						else{
							copyBoard(tempQueue.peek().getBoard(), this.board);
							//System.out.println("cheapest " + this.hOfN);
							//print();
							tempQueue.clear();
							//counter++;
							break;
						}
					}
				 }
				}
			}
		}
		/*
		else{//(this.hOfN == 0){
			System.out.println("Success!!");
			System.out.println("Number of steps: " + counter);
			//status = false;
		}*/
	}

	public void copyBoard(String[][] a, String[][] b){
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				b[i][j] = a[i][j];
			}
		}
	}

	public void test(){
		board[1][0] = "Q";
		board[1][1] = "Q";
		board[0][2] = "Q";
		board[3][3] = "Q";
	}
	// checks which row the Queen is placed and returns the row's index
	public int check(int col){
		int temp = 0;
		for(int row = 0; row < size; row++){
			if(board[col][row].equals("Q"))
				temp = row;
		}
		return temp;
	}
	// this function calculates the h and returns that value
	public int calculateH(String[][] a){
		int h = 0;
		for(int col = 0; col < this.size; col++){
			for(int row = 0; row < this.size; row++){
				if(a[row][col].equals("Q")){
					h = h + checkRow(a, row, col) + checkDiagonals(a, row, col);
				}
			}
		}
		return h;
	}
	// function that counts the number of queens in the same row
	public int checkRow(String[][] array, int row, int col){
		int temp = 0;
		for(int col2 = (col+1); col2 < this.size; col2++){
			if(array[row][col2].equals("Q"))
				temp++;
		}
		return temp;
	}

	// function that counts the number of queens being hit diagonally (upright and bottomright)
	public int checkDiagonals(String[][] array, int row, int col){
		int temp = 0;
		int colTemp = col;
		
		for(int tempRow = row-1; tempRow >= 0; tempRow--){
			++colTemp;
			if(colTemp < size)
				if(array[tempRow][colTemp].equals("Q"))
				  temp++;
		}
		colTemp = col;
		// down right
		for(int tempRow = row+1; tempRow < size; tempRow++){
			++colTemp;
			if(colTemp < size)
				if(array[tempRow][colTemp].equals("Q"))
				  temp++;
		}
		return temp;
	}

	// Print board
	public void print(){
		for(int i = 0; i < size; i++){
			for(int j = 0 ; j < size; j++){
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void print(String[][] a){
		for(int i = 0; i < size; i++){
			for(int j = 0 ; j < size; j++){
				System.out.print(a[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	@Override
	public int compareTo(NQueen b){
		if(this.hOfN > b.hOfN)
			return 1;
		if(this.hOfN == b.hOfN) 
			return 0;
		return -1;
	}

}