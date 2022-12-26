
public class ComputerBattleshipPlayer extends BattleshipPlayer {

	public void updatePlayer(Position pos, boolean hit, char initial, String boatName, boolean sunk, boolean gameOver,
			boolean tooManyTurns, int turns) {
		updateGrid(pos, hit, initial);
	}

	public String playerName() {
		return "Computer Player";
	}

	public Position shoot() {
		Position shot;
		do {
			int xIndex = (int) (Math.random() * (10));
			int yIndex = (int) (Math.random() * (10));
			shot = new Position(xIndex, yIndex);
		} while (!getGrid().empty(shot));

		return shot;
	}

	public void startGame() {
		initializeGrid();
	}
}
