package assignment4Game;

import java.io.*;

public class Game {
	
	public static int play(InputStreamReader input){
		BufferedReader keyboard = new BufferedReader(input);
		Configuration c = new Configuration();
		int columnPlayed = 3; int player;
		
		// first move for player 1 (played by computer) : in the middle of the grid
		c.addDisk(firstMovePlayer1(), 1);
		int nbTurn = 1;
		
		while (nbTurn < 42){ // maximum of turns allowed by the size of the grid
			player = nbTurn %2 + 1;
			if (player == 2){
				columnPlayed = getNextMove(keyboard, c, 2);
			}
			if (player == 1){
				columnPlayed = movePlayer1(columnPlayed, c);
			}
			System.out.println(columnPlayed);
			c.addDisk(columnPlayed, player);
			if (c.isWinning(columnPlayed, player)){
				c.print();
				System.out.println("Congrats to player " + player + " !");
				return(player);
			}
			nbTurn++;
		}
		return -1;
	}
	
	public static int getNextMove(BufferedReader keyboard, Configuration c, int player){
		// ADD YOUR CODE HERE
		int nextMove = -1;
		boolean falseInput = true;
		System.out.println("Hi Player " + player + "! Here is the board currently: ");
		c.print();
		System.out.println("Please enter the column wou would like to drop your coin in: ");
		try {
			while (falseInput) {
				nextMove = Integer.parseInt(keyboard.readLine());
				if ((nextMove < 0) || (nextMove > 6)) {
					System.out.println("Invalid input! Please try again.");
					continue;
				}
				if (c.available[nextMove] >= 6) {
					System.out.println("Sorry that column is filled! Please try again.");
					continue;
				} else {
					falseInput = false;
				}
			}
		} catch (Exception e) {
			return -1;
		}
		return nextMove; // DON'T FORGET TO CHANGE THE RETURN
	}
	
	public static int firstMovePlayer1 (){
		return 3;
	}
	
	public static int movePlayer1 (int columnPlayed2, Configuration c){
		// ADD YOUR CODE HERE
		
		/* ---------SUBMITTED MOVEPLAYER1-------------------
		// checking if board is full
		// board is not full
		int win = c.canWinNextRound(1);
		if (win != -1) {
			//c.board[win][(c.available[win])] = 1;
			return win;
		}
		int winByTwo = c.canWinTwoTurns(1);
		if (winByTwo != -1) {
			//c.board[winByTwo][(c.available[winByTwo])] = 1;
			return winByTwo;
		}
		
		int last = columnPlayed2;
		int [] possible = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6};
		for (int i = 0; i < possible.length; i++) {
			// Making sure that 'last' value will not result in Exception
			if ((last > 6) || (last < 0)) {
				if ((i % 2) != 0) {
					last = columnPlayed2 + possible[i];
				} else {
					last = columnPlayed2 - possible[i];
				}
				continue;
			} else {
				if (c.available[last] < 6) {
					return last;
				} else {
					if ((i % 2) != 0) {
						last = columnPlayed2 + possible[i];
					} else {
						last = columnPlayed2 - possible[i];
					}
				}
			}			
		}
		-----------END SUBMITTED MOVEPLAYER1--------------
		*/
		
		
		// * MY OWN VERSION OF AI (BETTER THAN SUBMISSION)
		int win = c.canWinNextRound(1);
		if (win != -1) {
			//c.board[win][(c.available[win])] = 1;
			return win;
		}
		int winByTwo = c.canWinTwoTurns(1);
		if (winByTwo != -1) {
			//c.board[winByTwo][(c.available[winByTwo])] = 1;
			return winByTwo;
		}
		int otherWin = c.canWinNextRound(2);
		if (otherWin != -1) {
			//c.board[win][(c.available[win])] = 1;
			return otherWin;
		}
		int otherWinBy2 = c.canWinTwoTurns(2);
		if (otherWinBy2 != -1) {
			//c.board[winByTwo][(c.available[winByTwo])] = 1;
			return otherWinBy2;
		}
		boolean valid = false;
		while (!valid) {
			int last = (int) (Math.random()*7);
			//System.out.println(last);
			valid = true;
			if (c.available[last] >= 6) {
				valid = false;
				continue;
			} else {
				c.board[last][c.available[last]] = 1;
				c.available[last]++;
				if (c.canWinNextRound(2) != -1) {
					c.removeCoin(last);
					valid = false;
					continue;
				} else if (c.canWinTwoTurns(2) != -1){
					c.removeCoin(last);
					valid = false;
					continue;
				} else {
					c.removeCoin(last);
					valid = true;
					return last;
				}
			}
		}
		
		// -1 returned if board is full
		return -1; // DON'T FORGET TO CHANGE THE RETURN
	}
	
}










