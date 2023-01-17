import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MainWindow extends JFrame implements ActionListener {
	public static final int ROWS = 7;
	public static final int COLS = 5;
	public static final int CELLSIZE = 135;
	public static final int WIDTH = COLS * CELLSIZE;
	public static final int HEIGHT = ROWS * (CELLSIZE - 30);
	private JButton startButton;
	private Game theGame;
	private JButton[][] boardButtons = new JButton[3][3];

	public MainWindow() {
//Styling the Window - size,title,layout, and centering window
		setTitle("Extremain Tic Tac Toe");
		setSize(WIDTH, HEIGHT);
		setLayout(new GridLayout(ROWS, COLS));
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//First row is blank
		for (int i = 0; i < COLS; i++) {
			add(new JLabel());
		}
//Rows 2-4 have the board
		for (int i = 0; i < 3; i++) {// for each row in the board
			add(new JLabel());
			for (int j = 0; j < 3; j++) {
				boardButtons[i][j] = new JButton("?");
				boardButtons[i][j].setEnabled(false);
				boardButtons[i][j].setFont(new Font("serif", Font.BOLD, 30));
				boardButtons[i][j].addActionListener(this);
				add(boardButtons[i][j]);
			}
			add(new JLabel());
		}
		for (int i = 0; i < COLS; i++) {
			add(new JLabel());
		}
		for (int i = 0; i < 3; i++) {
			add(new JLabel());
		}
		startButton = new JButton("Start Game");
		add(startButton);
		add(new JLabel());
		startButton.addActionListener(this);
		for (int i = 0; i < COLS; i++) {
			add(new JLabel());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
// TODO Auto-generated method stub
		if (e.getActionCommand().equals("Start Game")) {
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					boardButtons[i][j].setText("?");
					boardButtons[i][j].setEnabled(true);
				}
			theGame = new Game(new PlayerBot());
			JOptionPane.showMessageDialog(null, "You are playing O's.");
		} else if (e.getActionCommand().equals("?")) {
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					if (boardButtons[i][j] == e.getSource()) {
						Move move = new Move(i, j);
						int winner = theGame.makeMove(move);
						if (move.getResult() == 1)
							boardButtons[i][j].setText("X");
						else
							boardButtons[i][j].setText("O");
						if (winner != -1) {
							if (winner == 1)
								JOptionPane.showMessageDialog(null, "You lost!");
							else if (winner == 2)
								JOptionPane.showMessageDialog(null, "You won!");
							else
								JOptionPane.showMessageDialog(null, "The game was a tie.");
							for (int a = 0; a < 3; a++)
								for (int b = 0; b < 3; b++) {
									boardButtons[a][b].setText(theGame.getBoardState()[a][b]);
									boardButtons[a][b].setEnabled(false);
								}
						}
					}
				}
		}
	}
}