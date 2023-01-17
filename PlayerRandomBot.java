
public class PlayerRandomBot extends Player {

	/**
	 * Pick a move according to the bot's strategy
	 * 
	 * @param debug do we output debugging information
	 * @return the move we have chosen at random from the available moves.
	 */
	@Override
	public Move getMove(boolean debug) {
		Move move = new Move(prng.nextInt(3), prng.nextInt(3));
		while (!isAvailable(move)) {
			move = new Move(prng.nextInt(3), prng.nextInt(3));
		}

		if (debug) {
			System.out.println(
					"\nPlayer " + getBotName() + " (playing as player " + getPlayerNum() + ") view of the board:");
			printState();
			System.out.println(getBotName() + " chooses " + move + " as random next move.");
		}
		return move;
	}

	/**
	 * @return a string name for this bot
	 */
	@Override
	public String getBotName() {
		return "RandomBot";
	}

}