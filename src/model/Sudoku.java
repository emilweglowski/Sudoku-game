package model;

public class Sudoku {

	int[][] sudokuTable;
	int sts = 9;		//sudokuTableSize
	int sss = 3;		//smallSquareSize
	int nomd;			//numberOfMissingDigits
	
	public Sudoku (int sudokuTableSize, int numberOfMissingDigits) {
		
		this.sts = sudokuTableSize;
		this.nomd = numberOfMissingDigits;
		
		sudokuTable = new int[sts][sts];
	}
	
	public int[][] fillSudokuTable() {
		
		fillThreeDiagonalSquares();
		fillRemaining(0, sss);
		removeKDigits();
		return sudokuTable;
	}

	public void fillThreeDiagonalSquares() {
		
		for (int i=0; i<sts; i+=sss) {
			fill3x3Box(i, i);
		}
	}

	public void fill3x3Box(int row, int column) {
		int rdm;
		for (int i=0; i<sss; i++) {
			for (int j=0; j<sss; j++) {
				do {
					rdm = randomGenerator(sts);
				}
				while (!checkIfUnusedInSquare(row, column, rdm));
				sudokuTable[row+i][column+j] = rdm;
			}
		} 
	}

	public int randomGenerator(int size) {
		return (int) Math.floor((Math.random()*size+1));
	}
	
	public boolean checkIfUnusedInColumn(int j, int num) {
		for (int i=0; i<sts; i++) {
			if (sudokuTable[i][j] == num) {
				return false;
			}
		}
		return true;
	}

	public boolean checkIfUnusedInRow(int i, int num) {
		for (int j=0; j<sts; j++) {
			if (sudokuTable[i][j] == num) {
				return false;
			}
		}
		return true;
	}
	
	public boolean checkIfUnusedInSquare(int initRow, int initColumn, int numToBeChecked) {
		for (int i=0; i<sss; i++) {
			for (int j=0; j<sss; j++) {
				if(sudokuTable[initRow+i][initColumn+j] == numToBeChecked) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean ckechIfPossibleToPutInCell(int i, int j, int num) {
		
		return(checkIfUnusedInRow(i, num) && checkIfUnusedInColumn(j, num) && checkIfUnusedInSquare(i-i%sss, j-j%sss, num));
	}	
	
	boolean fillRemaining(int i, int j) {		//recursive method to fill remaining fields - iterates horizontally by verses
	        
		if (j>=sts && i<sts-1) {				//enter the next verse when j=9 (size of the sudoku table), because j can only be 0-8
			i = i + 1;							//that is: i++ and j=0 (next verse and set column number to 0)
			j = 0;
		}
		if (i>=sts && j>=sts) {					//for not to exceed sudoku table size
			return true;
		}
		if (i < sss) {							//here's starting the whole filling: when i<3 set j=3 (go to column 3) because:
			if (j < sss) {						//1st 3x3 square is already filled after diagonally filling squares 1, 5, 9
				j = sss;
			}
		} else if (i < sts-sss) {				//skip j on columns 3, 4, 5 when 2<i<6 (because center (5th) square is filled)
			if (j==(int)(i/sss)*sss) {			//by simply adding 3 (small square size) to j
				j =  j + sss;
			}
		}
		else {									//for i<6,7,8> and j<0,1,2,3,4,5> :
			if (j == sts-sss) {					//j can be max 5 here, so when j=6
				i = i + 1;						//then skip to next verse
				j = 0;							//and set j=0
				if (i>=sts) {					//when i=9 :
					return true;				//return true - means: execute method calls that are on stack from the beginning of this recursive method
				}
			}
		}
	 
		for (int num = 1; num<=sts; num++) {
	        	
			if (ckechIfPossibleToPutInCell(i, j, num)) {
				sudokuTable[i][j] = num;
				if (fillRemaining(i, j+1)) {
					return true;
				}
				sudokuTable[i][j] = 0;
			}
		}
		return false;
	}
	
	public void removeKDigits() {
		int counter = nomd;
		while (counter!=0) {
			int cellId = randomGenerator(sts*sts)-1;
			int i = cellId/9;
			int j = cellId%9;
			if (sudokuTable[i][j] > 0) {
				counter--;
				sudokuTable[i][j] = -1*sudokuTable[i][j];
			}
		}
	}

	public void printSudokuTable() {
		for (int i=0; i<sts; i++) {
			for (int j=0; j<sts; j++) {
				System.out.print(sudokuTable[i][j] + " ");
			}
			System.out.println();
		}
	}
}
