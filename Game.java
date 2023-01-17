import java.util.Arrays;

/**
 * A game of Extreme Tic-tac-toe
 * 
 * @author jjb24
 *
 */
public class Game {
	final static boolean DEBUG = false; // NOTE: If you set this to true, you will get debugging output, and play just 1
										// game per pair
	final static int GAME_COUNT = 1000; // The number of games to play between distinct player1-player2 matchups, if
										// DEBUG is false

	public static void main(String[] args) {
		// TODO: Use a more appropriate data type in order to support polymorphism
		// Object[] players = {new PlayerBaseBot(), new PlayerRandomBot(), new
		// PlayerRandomBot()};
		Player[] players = { new PlayerRandomBot(), new PlayerMyBot(), new PlayerBaseBot() };

		for (int g = 0; g < (DEBUG ? 1 : GAME_COUNT); g++) {
			for (int i = 0; i < players.length; i++) {
				for (int j = 0; j < players.length; j++) {
					if (i != j) {
						//// TODO: Simplify all of the code in this block so that we have just one call
						//// to play
						Game.play(players[i], players[j], DEBUG);
					}
				}
			}
		}

		// TODO: After you indicate that all Players implement the comparable interface,
		// you will be able to
		// uncomment the next line to order the players by winning percentage
		Arrays.sort(players);
		for (int i = 0; i < players.length; i++) {
			// TODO: simplify the following code so that it is just one line
			System.out.println((players[i]).getBotName() + " with a win percent of " + (players[i]).getWinPercent());
		}
	}

	// TODO: Replace the multiple copies of play with just a single method
	// definition
	/**
	 * Play a game between two bots
	 * 
	 * @param players  the player playing X's
	 * @param players2 the player playing O's
	 * @param debug    do we output debugging information
	 */
	public static void play(Player players, Player players2, boolean debug) {
		players.reset(1);
		players2.reset(2);

		Board board = new Board();

		int winner = -1;
		while (winner == -1) {
			Move[] moves = { players.getMove(debug), players2.getMove(debug) };
			board.update(moves);

			players.updateState(moves[0]);
			players2.updateState(moves[1]);

			winner = board.getWinner();
		}

		players.recordGameResult(winner);
		players2.recordGameResult(winner);

		if (debug) {
			System.out.println("\nBoard at end of game");
			if (winner == 0) {
				System.out.println("Game ended in a tie.");
			} else if (winner == 1) {
				System.out.println(players.getBotName() + " playing X's wins!");
			} else {
				System.out.println(players2.getBotName() + " playing O's wins!");
			}
			System.out.println(board + "\n");
		}
	}

}