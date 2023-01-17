import java.util.Random;

public abstract class Player implements Comparable<Player> {
	private int playerNum;
	private int[][] state = new int[3][3];
	private int moveNumber;
	protected Random prng = new Random();
	private int wins, losses, ties;

	public abstract Move getMove(boolean debug);

	public abstract String getBotName();

	public int compareTo(Player that) {
		if (this.getWinPercent() > that.getWinPercent())
			return -1;
		if (this.getWinPercent() < that.getWinPercent())
			return 1;
		else
			return 0;
	}

	/**
	 * Determine is a square is available based on the bot's view of the board
	 * 
	 * @param move the square
	 * @return true if the square has not been marked, as far as the bot knows
	 */
	public boolean isAvailable(Move move) {
		// Check if available
		return state[move.getRow()][move.getColumn()] == 0;
	}

	public boolean canWinWithMove(Move move) {
		// Check if available
		if (!isAvailable(move))
			return false;

		// Check row
		boolean canWin = true;
		for (int i = 1; i < 3 && canWin; i++) {
			int row = (move.getRow() + i) % 3;
			if (state[row][move.getColumn()] != getPlayerNum()) {
				canWin = false;
			}
		}
		if (canWin)
			return true;

		// Check column
		canWin = true;
		for (int i = 1; i < 3 && canWin; i++) {
			int col = (move.getColumn() + i) % 3;
			if (state[move.getRow()][col] != getPlayerNum()) {
				canWin = false;
			}
		}
		if (canWin)
			return true;

		// Check diagonals
		canWin = false;
		if (move.getRow() == 0 && move.getColumn() == 0) {
			canWin |= state[1][1] == state[2][2] && state[1][1] == getPlayerNum();
		}
		if (move.getRow() == 2 && move.getColumn() == 2) {
			canWin |= state[0][0] == state[1][1] && state[1][1] == getPlayerNum();
		}
		if (move.getRow() == 0 && move.getColumn() == 2) {
			canWin |= state[2][0] == state[1][1] && state[1][1] == getPlayerNum();
		}
		if (move.getRow() == 2 && move.getColumn() == 0) {
			canWin |= state[0][2] == state[1][1] && state[1][1] == getPlayerNum();
		}
		if (move.getRow() == 1 && move.getColumn() == 1) {
			canWin |= (state[0][0] == state[2][2] && state[0][0] == getPlayerNum())
					|| (state[2][0] == state[0][2] && state[2][0] == getPlayerNum());
		}
		return canWin;
	}

	/**
	 * Determine if the bot thinks that a move will give block three in a row for
	 * the opponent
	 * 
	 * @param move the move the bot is considering
	 * @return true iff this move would block opponent from winning
	 */
	public boolean canBlockWithMove(Move move) {
		// Check if available
		if (!isAvailable(move))
			return false;

		// Check row
		boolean canWin = true;
		for (int i = 1; i < 3 && canWin; i++) {
			int row = (move.getRow() + i) % 3;
			if (state[row][move.getColumn()] != getPlayerNum()) {
				canWin = false;
			}
		}
		if (canWin)
			return true;

		// Check column
		canWin = true;
		for (int i = 1; i < 3 && canWin; i++) {
			int col = (move.getColumn() + i) % 3;
			if (state[move.getRow()][col] != getPlayerNum()) {
				canWin = false;
			}
		}
		if (canWin)
			return true;

		// Check diagonals
		canWin = false;
		if (move.getRow() == 0 && move.getColumn() == 0) {
			canWin |= state[1][1] == state[2][2] && state[1][1] == getPlayerNum();
		}
		if (move.getRow() == 2 && move.getColumn() == 2) {
			canWin |= state[0][0] == state[1][1] && state[1][1] == getPlayerNum();
		}
		if (move.getRow() == 0 && move.getColumn() == 2) {
			canWin |= state[2][0] == state[1][1] && state[1][1] == getPlayerNum();
		}
		if (move.getRow() == 2 && move.getColumn() == 0) {
			canWin |= state[0][2] == state[1][1] && state[1][1] == getPlayerNum();
		}
		if (move.getRow() == 1 && move.getColumn() == 1) {
			canWin |= (state[0][0] == state[2][2] && state[0][0] == getPlayerNum())
					|| (state[2][0] == state[0][2] && state[2][0] == getPlayerNum());
		}
		return canWin;
	}

	/**
	 * Count the number of marked symbols in the same row, column, or diagonal as a
	 * move
	 * 
	 * @param move the move the bot is considering
	 * @return a count of the number of X's or O's in the same row, column or
	 *         diagonal as this move
	 */
	public int countColinearPieces(Move move) {
		// Check if available
		if (!isAvailable(move))
			return 0;

		// Check row
		int count = 0;
		for (int i = 1; i < 3; i++) {
			int row = (move.getRow() + i) % 3;
			if (state[row][move.getColumn()] != 0) {
				count++;
			}
		}

		// Check column
		for (int i = 1; i < 3; i++) {
			int col = (move.getColumn() + i) % 3;
			if (state[move.getRow()][col] != 0) {
				count++;
			}
		}

		// Check diagonals
		if (move.getRow() == 0 && move.getColumn() == 0) {
			if (state[1][1] != 0)
				count++;
			if (state[2][2] != 0)
				count++;
		}
		if (move.getRow() == 2 && move.getColumn() == 2) {
			if (state[1][1] != 0)
				count++;
			if (state[0][0] != 0)
				count++;
		}
		if (move.getRow() == 0 && move.getColumn() == 2) {
			if (state[1][1] != 0)
				count++;
			if (state[2][0] != 0)
				count++;
		}
		if (move.getRow() == 2 && move.getColumn() == 0) {
			if (state[1][1] != 0)
				count++;
			if (state[0][2] != 0)
				count++;
		}
		if (move.getRow() == 1 && move.getColumn() == 1) {
			if (state[2][0] != 0)
				count++;
			if (state[0][2] != 0)
				count++;
			if (state[0][0] != 0)
				count++;
			if (state[2][2] != 0)
				count++;
		}
		return count;
	}

	/**
	 * Record the result of the last move in the state array
	 * 
	 * @param lastMove the last move made by the human
	 */
	public void updateState(Move lastMove) {
		state[lastMove.getRow()][lastMove.getColumn()] = lastMove.getResult();
		moveNumber++;
	}

	/**
	 * Reset the state of the bot so that it can play a new game
	 * 
	 * @param playerNum it's player number in the new game
	 */
	public void reset(int playerNum) {
		this.playerNum = playerNum;
		moveNumber = 0;
		state = new int[3][3];
	}

	/**
	 * Get the current move number. Before any moves have been made, this will
	 * return 0. After the first move has been made, it returns 1. After the second,
	 * 2, and so on.
	 * 
	 * @return the current move number
	 */
	public int getMoveNumber() {
		return moveNumber;
	}

	/**
	 * Get the symbol in a particular square according to the bot's view of the game
	 * 
	 * @param move the square in question.
	 * 
	 * @return a 0 if the square is empty, otherwise the player number that controls
	 *         the square.
	 */
	public int getState(Move move) {
		return state[move.getRow()][move.getColumn()];
	}

	/**
	 * @return the player number (either 1 or 2)
	 */
	public int getPlayerNum() {
		return playerNum;
	}

	/**
	 * Update the appropriate count based on whether a game ended in a win, tie, or
	 * loss.
	 * 
	 * @param winner the player number who won, or 0 if it was a tie.
	 */
	public void recordGameResult(int winner) {
		if (winner == 0)
			ties++;
		else if (winner == getPlayerNum())
			wins++;
		else
			losses++;
	}

	/**
	 * Get the winning percentage of a player. Note that ties count as half a win.
	 * 
	 * @return the winning percentage.
	 */
	public double getWinPercent() {
		return (wins + 0.5 * ties) / (wins + losses + ties);
	}

	/**
	 * Use the state array and System.out to display the state of the board as known
	 * to the bot player.
	 * 
	 * You should start by indicating whether the bo is X's or O's. Then display a
	 * board with X's, O's, and ?'s, where '?' is used for squares that are unknown.
	 */
	public void printState() {
		System.out.println("Bot is " + (playerNum == 1 ? "X" : "O") + "'s");
		for (int r = 0; r < state.length; r++) {
			for (int c = 0; c < state[r].length; c++) {
				switch (state[r][c]) {
				case 0:
					System.out.print(" ?");
					break;
				case 1:
					System.out.print(" X");
					break;
				case 2:
					System.out.print(" O");
					break;
				}
			}
			System.out.println();
		}
	}
}
