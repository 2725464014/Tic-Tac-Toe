import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class TicTacToeServlet extends HttpServlet {
	private static final long serialVersionUID = -3388076538168097844L;

	/**
	 * Handle Get requests
	 * 
	 * @param req  The request
	 * @param resp The response
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		int winner = -1;
		Move move = null;

		if (req.getRequestURI().equalsIgnoreCase("/bin/start_game")) {
			Game game = null;
			if (req.getParameter("botlist").equals("easy")) {
				game = new Game(new PlayerEasy());
				session.setAttribute("bot", "Easy");
			} else if (req.getParameter("botlist").equals("aggressive")) {
				game = new Game(new PlayerAggressive());
				session.setAttribute("bot", "Aggressive");
			} else {
				game = new Game(new PlayerDefensive());
				session.setAttribute("bot", "Defensive");
			}
			String[][] board = { { "?", "?", "?" }, { "?", "?", "?" }, { "?", "?", "?" } };
			// TODO: Store the board and game variables in the user's session
			session.setAttribute("game", game);
			session.setAttribute("board", board);

		} else if (req.getRequestURI().equalsIgnoreCase("/bin/make_move")) {
			// TODO: set the move variable to point to a new Move object. The row and column
			// for the Move object should come from the parameters sent with the HTML form.
			move = new Move(Integer.valueOf(req.getParameter("row")), Integer.valueOf(req.getParameter("column")));

			// TODO: Retrieve the Game object that is saved in the session
			Game game = (Game) session.getAttribute("game");

			// TODO: Activate the makeMove method on the game object with the user's move,
			// and store the return value in the winner variable
			winner = game.makeMove(move);

			// TODO: Replace the "???" in the
			// row below to update the appropriate cell in the array to the result of the
			// move.
			// board[???][???] = move.getResult()==1?"X":"O";
			String[][] board = (String[][]) session.getAttribute("board");
			board[move.getRow()][move.getColumn()] = move.getResult() == 1 ? "X" : "O";

		} else {
			log("NOT_FOUND: " + req.getRequestURL());
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}

		drawBoard(resp.getWriter(), session, winner, move);
	}

	private void drawBoard(PrintWriter out, HttpSession session, int winner, Move move) {
		out.println("<!doctype html>");
		out.println("<html>");
		out.println("<head><title>Extreme Tic Tac Toe</title></head>");
		out.println("<body style=\"text-align: center;\">");

		// TODO: Update the line below so that the ??? is replaced with the type of bot
		// they are playing
		// out.println("<H1>Extreme Tic Tac Toe vs. ??? Bot</H1>");
		out.println("<H1>Extreme Tic Tac Toe vs. " + session.getAttribute("bot") + " Bot</H1>");

		String[][] board = (String[][]) session.getAttribute("board");
		if (winner != -1) {
			Game game = (Game) session.getAttribute("game");
			board = game.getBoardState();
			switch (winner) {
			case 0:
				out.println("<H2>The game ended in a tie!</H2>");
				break;
			case 1:
				out.println("<H2>You lost!</H2>");
				break;
			case 2:
				out.println("<H2>You won!</H2>");
				break;
			}
		}
		// TODO: If the user has just made a move, output a line
		// "You [successfully chose|failed to select] row # and column #"
		if (move != null)
			if (move.getResult() == 2)
				out.println("<P>You successfully chose row " + move.getRow() + " and column " + move.getColumn());
			else
				out.println("<P>You failed to select row " + move.getRow() + " and column " + move.getColumn());

		out.println("<table border=1 style=\"margin-left:auto;margin-right:auto;\">");
		out.println("<tr><th>&nbsp;</th>");
		for (int i = 0; i < 3; i++) {
			out.println("<th>" + i + "</th>");
		}
		out.println("</tr>");
		for (int i = 0; i < 3; i++) {
			out.println("<tr><th>" + i + "</th>");
			for (int j = 0; j < 3; j++) {
				out.println("<td>");
				// TODO: Fix the output of the cell, so that it is in red for the cell that
				// the user just chose
				if (move != null && move.getRow() == i && move.getColumn() == j)
					out.println("<H2 style =\"color:red;\">");
				else
					out.println("<H2>");

				out.println("&nbsp;&nbsp;&nbsp;" + board[i][j] + "&nbsp;&nbsp;&nbsp;</h2></th>");
			}
			out.println("</tr>");
		}
		out.println("</table>");

		// TODO: Change the code below, so that you output a link to return to the main
		// page
		// (/index.html), if the winner variable is not equal to -1
		if (winner == -1) {
			out.println("<P><EM>You are playing O's</EM></P>");
			out.println("<form action=\"make_move\" method=\"get\">");
			out.println("<p>Select the row and column for your move.");
			out.println("<P>Row (between 0 and 2): <input name=\"row\" type=\"number\" min=\"0\" max=\"2\">");
			out.println("<P>Column (between 0 and 2): <input name=\"column\" type=\"number\" min=\"0\" max=\"2\">");
			out.println("<p><input type=\"submit\">");
		} else {
			out.println("<P><A HREF=\"/index.html\">Return to main page</A>");
		}
		out.println("</body>");
		out.println("</html>");
	}

}
