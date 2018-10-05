package assignment4Game;

public class Configuration {
	
	public int[][] board;
	public int[] available;
	boolean spaceLeft;
	
	public Configuration(){
		board = new int[7][6];
		available = new int[7];
		spaceLeft = true;
	}
	
	public void print(){
		System.out.println("| 0 | 1 | 2 | 3 | 4 | 5 | 6 |");
		System.out.println("+---+---+---+---+---+---+---+");
		for (int i = 0; i < 6; i++){
			System.out.print("|");
			for (int j = 0; j < 7; j++){
				if (board[j][5-i] == 0){
					System.out.print("   |");
				}
				else{
					System.out.print(" "+ board[j][5-i]+" |");
				}
			}
			System.out.println();
		}
	}
	
	public void addDisk (int index, int player){
		// ADD YOUR CODE HERE
		if ((index > board.length - 1) || (index < 0)) {
			System.out.println("Incorrect index");
		} else {
			/*
			for (int i = 0; i < board[index].length; i++) {
				if (board[index][i] == 0) {
					board[index][i] = player;
					available[index] = i++;
					break;
				} else {
					continue;
				}
			}
			*/
			if (available[index] >= 6) {
				System.out.println("This column is filled");
			}
			int row = available[index];
			board[index][row] = player;
			available[index]++;
		}	
	}
	
	public boolean isWinning (int lastColumnPlayed, int player){
		// ADD YOUR CODE HERE
		
		//System.out.println(board[lastColumnPlayed].length);
		//this.print();
		
		// CHECKING VERTICAL WIN
		
		int vertCnt = available[lastColumnPlayed] - 1;
		int vertTrue = 0;
		int rememberVert = 0;
		while (!(vertCnt < 0)) {
			
			if ((board[lastColumnPlayed][vertCnt]) == 0) {
				vertCnt--;
				continue;
			}
			if ((board[lastColumnPlayed][vertCnt]) == player) {
				if (vertTrue != rememberVert) {
					break;
				}
				vertTrue++;
				rememberVert = vertTrue;
			} else {
				vertTrue--;
			}
			vertCnt--;
		}
		if (vertTrue >= 4) {
			return true;
		}
		
		// CHECKING HORIZONTAL WIN
		
		int rowToCheck = available[lastColumnPlayed] - 1;
		//System.out.println(lastColumnPlayed);
		//System.out.println(rowToCheck);
		int horiCnt = 0;
		// right side check
		for (int i = 0; i < 4; i++) {
			int check = lastColumnPlayed + i;
			if (check > board.length - 1) {
				break;
			}
			if ((board[lastColumnPlayed + i][rowToCheck] == 0)) {
				break;
			}
			if ((board[lastColumnPlayed + i][rowToCheck]) == player) {
				horiCnt++;
			} else {
				break;
			}
			//System.out.println("right: " + horiCnt);
			
		}
		// left side check
		for (int i = 0; i < 4; i++) {
			int check = lastColumnPlayed - i;
			if (check < 0) {
				break;
			}
			if ((board[lastColumnPlayed - i][rowToCheck] == 0)) {
				break;
			}
			if ((board[lastColumnPlayed - i][rowToCheck]) == player) {
				horiCnt++;
			} else {
				break;
			}
			//System.out.println("left: " + horiCnt);
		}
	
		// to negate for double counting of last placed coin, we take one away from horiCnt
		horiCnt--;
		if (horiCnt >= 4) {
			return true;
		}
		
		// CHECKING DIAGONALS (4 LOOPS FOR BOTH DIAGONALS)
		// FOR FORWARDSLASH (fSlash) + upwards
		int fSlash = 0;
		int nextRow = available[lastColumnPlayed] - 1;
		for (int i = 0; i < 4; i++) {
			int nextCol = lastColumnPlayed + i;
			//int nextRow = available[lastColumnPlayed];
			
			if (((nextCol >= board.length) || (nextCol < 0)) || ((nextRow >= board[0].length) || (nextRow < 0))) {
				break;
			}
			if (board[nextCol][nextRow] == 0) {
				break;
			}
			if (board[nextCol][nextRow] == player) {
				fSlash++;
			} else {
				break;
			}
			nextRow++;
		}
		// FOR FORWARDSLASH (fSlash) + downwards
		nextRow = available[lastColumnPlayed] - 1;
		for (int i = 0; i < 4; i++) {
			int nextCol = lastColumnPlayed - i;
			
			if (((nextCol >= board.length) || (nextCol < 0)) || ((nextRow >= board[0].length) || (nextRow < 0))) {
				break;
			}
			if (board[nextCol][nextRow] == 0) {
				break;
			}
			if (board[nextCol][nextRow] == player) {
				fSlash++;
			} else {
				break;
			}
			nextRow--;
		}
		// to negate double counting of last played coin
		fSlash--;
		if (fSlash >= 4) {
			return true;
		}
		
		// FOR BACKSLASH (bSlash) + upwards
		int bSlash = 0;
		nextRow = available[lastColumnPlayed] - 1;
		for (int i = 0; i < 4; i++) {
			int nextCol = lastColumnPlayed - i;
			
			if (((nextCol >= board.length) || (nextCol < 0)) || ((nextRow >= board[0].length) || (nextRow < 0))) {
				break;
			}
			if (board[nextCol][nextRow] == 0) {
				break;
			}
			if (board[nextCol][nextRow] == player) {
				bSlash++;
			} else {
				break;
			}
			nextRow++;
			
		}
		// FOR BACKSLASH (bSlash) + downwards
		nextRow = available[lastColumnPlayed] - 1;
		for (int i = 0; i < 4; i++) {
			int nextCol = lastColumnPlayed + i;
			
			if (((nextCol >= board.length) || (nextCol < 0)) || ((nextRow >= board[0].length) || (nextRow < 0))) {
				break;
			}
			if (board[nextCol][nextRow] == 0) {
				break;
			}
			if (board[nextCol][nextRow] == player) {
				bSlash++;
			} else {
				break;
			}
			nextRow--;	
		}
		// to negate double counting of last played coin
		bSlash--;
		if (bSlash >= 4) {
			return true;
		}
		
		return false; // DON'T FORGET TO CHANGE THE RETURN
	}
	
	public int canWinNextRound (int player){
		// ADD YOUR CODE HERE
		// method simply iterates through the columns, adds a coin, and sees if that's a winning move
		// otherwise, it cancels that move and then moves to the next column
		for (int i = 0; i < board.length; i++) {
			// below if conditional handles case if column is full
			if (available[i] >= 6) {
				continue;
			}
			int rowToAdd = available[i];
			board[i][rowToAdd] = player;
			available[i] = rowToAdd + 1;
			if (isWinning(i, player)) {
				removeCoin(i);
				return i;
			}
			removeCoin(i);
		}
		return -1; // DON'T FORGET TO CHANGE THE RETURN
	}
	
	// ----------HELPER METHOD---------------
	public void removeCoin (int column) {
		int rowToRemove = available[column] - 1;
		board[column][rowToRemove] = 0;
		available[column] = rowToRemove;
	}
	
	// ----------HELPER METHOD---------------
	private int otherPlayer (int player) {
		if (player == 2) {
			return 1;
		} else {
			return 2;
		}
	}
	
	public int canWinTwoTurns (int player){
		// ADD YOUR CODE HERE
		/*
		for (int i = 0; i < board.length; i++) {
			if (available[i] >= 6) {
				continue;
			}
			board[i][(available[i])] = player;
			available[i] = available[i] + 1;
			if (canWinNextRound(player) >= 0) {
				int other = otherPlayer(player);
				if (available[canWinNextRound(player)] >= 6) {
					removeCoin(i);
					continue;
				} else {
					int toPlay = canWinNextRound(player);
					board[toPlay][(available[toPlay])] = other;
					available[toPlay] = available[toPlay] + 1;
					int toWin = canWinNextRound(player);
					if (toWin >= 0) {
						removeCoin(toPlay);
						removeCoin(i);
						return i;
					} else {
						removeCoin(toPlay);
						removeCoin(i);
						continue;
					}
				}
			}
			removeCoin(i);
		}
		*/
		
		// -------------POST SUBMISSION EDIT (is better)------------------
		for (int i = 0; i < board.length; i++) {
			if (available[i] >= 6) {
				continue;
			}
			board[i][(available[i])] = player;
			available[i] = available[i] + 1;
			if (canWinNextRound(2) >= 0) {
				removeCoin(i);
				continue;
			} else {
				if (canWinNextRound(player) >= 0) {
					int other = otherPlayer(player);
					if (available[canWinNextRound(player)] >= 6) {
						removeCoin(i);
						continue;
					} else {
						int toPlay = canWinNextRound(player);
						board[toPlay][(available[toPlay])] = other;
						available[toPlay] = available[toPlay] + 1;
						int toWin = canWinNextRound(player);
						if (toWin >= 0) {
							removeCoin(toPlay);
							removeCoin(i);
							return i;
						} else {
							removeCoin(toPlay);
							removeCoin(i);
							continue;
						}
					}
				}
			}
			removeCoin(i);
		}
		return -1; // DON'T FORGET TO CHANGE THE RETURN
	}
	
}
