
public class PlayerBaseBot extends Player {

	// An array of moves, with number of entries equivalent to how desirable a move
	// is
	private Move[] moves = { new Move(1, 1), new Move(0, 0), new Move(0, 0), new Move(0, 0), new Move(0, 2),
			new Move(2, 0), new Move(2, 2), new Move(0, 1), new Move(1, 0), new Move(1, 2), new Move(2, 1) };

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

		Move move = moves[prng.nextInt(moves.length)];
		while (!isAvailable(move)) {
			move = moves[prng.nextInt(moves.length)];
		}
		if (debug)
			System.out.println(getBotName() + " chooses " + move + " at random.");
		return move;
	}

	/**
	 * @return a string name for this bot
	 */
	@Override
	public String getBotName() {
		return "BaseBot";
	}

}