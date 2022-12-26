import java.util.*;

public class BattleshipPlayer {
	private String player;
	private BattleshipGrid grid = new BattleshipGrid();

	BattleshipPlayer() {
		player = null;
		startGame();
	}

	public void startGame() {
		if (player == null) {
			Scanner input = new Scanner(System.in);
			System.out.println("Hello, welcome to Battleship. \nPlease enter a name: ");
			player = input.nextLine();
		}
	}

	public String playerName() {
		return player;
	}

	public Position shoot() {
		Position inputPos;
		System.out.println("Enter a position: ");
		do {
			inputPos = new Position();
			if (inputPos.row() == 'X') {
				System.out.println("Invalid position, please try again: ");
			}
		} while (inputPos.row() == 'X');
		return inputPos;
	}

	public void updateGrid(Position pos, boolean hit, char initial) {
		grid.shotAt(pos, hit, initial);
	}

	public BattleshipGrid getGrid() {
		return grid;
	}

	public void initializeGrid() {
		grid = new BattleshipGrid();
	}

	public void updatePlayer(Position pos, boolean hit, char initial, String boatName, boolean sunk, boolean gameOver,
			boolean tooManyTurns, int turns) {
		updateGrid(pos, hit, initial);
		BattleshipGrid grid = getGrid();

		System.out.println("  1 2 3 4 5 6 7 8 9 10");
		for (int row = 0; row < 10; row++) {
			Position tempPos = new Position(row, 0);
			String output = "" + tempPos.row();
			for (int col = 0; col < 10; col++) {
				tempPos = new Position(row, col);
				if (grid.hit(tempPos)) {
					output = output + " " + grid.boatInitial(tempPos);
				} else if (grid.miss(tempPos)) {
					output = output + " *";
				} else {
					output = output + " .";
				}
			}
			System.out.println(output);
		}
		System.out.println("");
		System.out.println("Turn " + turns + ": ");
		if (hit) {
			System.out.println("You hit a " + boatName);
		} else {
			System.out.println("You missed");
		}

		if (sunk) {
			System.out.println("The " + boatName + " has sunk");
		}
		System.out.println("");

		if (tooManyTurns) {
			System.out.println("You took too many turns!");
			System.out.println("You lose");
		} else if (gameOver) {
			System.out.println("Congratulations you sunk all ships!");
		}
	}
}
