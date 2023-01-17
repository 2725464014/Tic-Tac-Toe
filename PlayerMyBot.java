
public class PlayerMyBot extends Player {

	// An array of moves, with number of entries equivalent to how desirable a move
	// is
	private Move[] moves = { new Move(0, 0), new Move(0, 1), new Move(0, 2), new Move(1, 0), new Move(1, 1),
			new Move(1, 2), new Move(2, 0), new Move(2, 1), new Move(2, 2) };
	private Move[] moves2 = { new Move(0, 0), new Move(0, 2), new Move(2, 0), new Move(2, 2) };
	private int[][] state = new int[3][3];

	/**
	 * Pick a move according to the bot's strategy
	 * 
	 * @param debug do we output debugging information
	 * @return the move we have chosen at random from the available moves.
	 */
	@Override
	public Move getMove(boolean debug) {
		if (debug) {
			System.out.println(
					"\nPlayer " + getBotName() + " (playing as player " + getPlayerNum() + ") view of the board:");
			printState();
		}

		// Check for a win
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				Move move = new Move(row, col);
				if (isAvailable(move) && canWinWithMove(move)) {
					if (debug)
						System.out.println(getBotName() + " chooses " + move + " to try to win.");
					return move;
				}
			}
		}

		// Check for a block
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				Move move = new Move(row, col);
				if (isAvailable(move) && canBlockWithMove(move)) {
					if (debug)
						System.out.println(getBotName() + " chooses " + move + " to try to block.");
					return move;
				}
			}
		}

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				Move move = new Move(row, col);
				if (isAvailable(move) && willWinWithMove(move)) {
					if (col == 1)
						move.setColumn(col + 1);
					if (!isAvailable(move) || !willWinWithMove(move))
						move.setColumn(col - 1);
					if (row == 1)
						move.setRow(row + 1);
					if (!isAvailable(move) || !willWinWithMove(move)) {
						move.setRow(row);
						move.setColumn(col);
					}
					if (debug)
						System.out.println(getBotName() + " chooses " + move + " to next move win.");
					return move;
				}
			}
		}
		Move move = moves[4];
		while (!isAvailable(move)) {
			move = moves2[prng.nextInt(moves.length)];
		}
		if (debug)
			System.out.println(getBotName() + " chooses " + move + " first or at random.");
		return move;
	}

	public boolean willWinWithMove(Move move) {
		// Check if available
		if (!isAvailable(move))
			return false;
		// Check row
		boolean willWin = true;
		int anotherPlayer;
		if (getPlayerNum() == 1)
			anotherPlayer = 2;
		else
			anotherPlayer = 1;
		for (int i = 1; i < 3 && willWin; i++) {
			int row = (move.getRow() + i) % 3;
			if (state[row][move.getColumn()] == 0) {
				willWin = true;
				if (state[row][move.getColumn()] == anotherPlayer) {
					willWin = false;
				}
			}
		}
		if (willWin)
			return true;
		// Check column
		willWin = true;
		for (int i = 1; i < 3 && willWin; i++) {
			int col = (move.getColumn() + i) % 3;
			if (state[move.getRow()][col] == 0) {
				willWin = true;
			}
			if (state[move.getRow()][col] == anotherPlayer) {
				willWin = false;
			}
		}
		if (willWin)
			return true;
		// Check diagonals
		willWin = false;
		if (move.getRow() == 0 && move.getColumn() == 0) {
			willWin |= state[1][1] == state[2][2] && state[1][1] == 0;
		}
		if (move.getRow() == 2 && move.getColumn() == 2) {
			willWin |= state[0][0] == state[1][1] && state[1][1] == 0;
		}
		if (move.getRow() == 0 && move.getColumn() == 2) {
			willWin |= state[2][0] == state[1][1] && state[1][1] == 0;
		}
		if (move.getRow() == 2 && move.getColumn() == 0) {
			willWin |= state[0][2] == state[1][1] && state[1][1] == 0;
		}
		if (move.getRow() == 1 && move.getColumn() == 1) {
			willWin |= (state[0][0] == state[2][2] && state[0][0] == 0)
					|| (state[2][0] == state[0][2] && state[2][0] == 0);
		}
		return willWin;
	}

	/**
	 * @return a string name for this bot
	 */
	@Override
	public String getBotName() {
		return "ZB's bot";
	}
}
