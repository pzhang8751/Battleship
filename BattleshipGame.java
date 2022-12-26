
public class BattleshipGame {
	Ocean ocean;
	BattleshipPlayer human;
	int turns;

	BattleshipGame(BattleshipPlayer player) {
		ocean = new Ocean();
		ocean.placeAllBoats();
		human = player;
		human.startGame();
		turns = 1;
	}

	public int play() {
		boolean gameOver = ocean.allSunk();
		while (gameOver == false) {

			Position inputShot = human.shoot();

			ocean.shootAt(inputShot);
			boolean hit = ocean.hit(inputShot);
			char initial = 'X';
			String boatName = "X";
			boolean sunk = ocean.sunk(inputShot);

			boolean tooManyTurns = false;

			if (turns == 100) {
				gameOver = true;
				tooManyTurns = true;
			}
			if (ocean.allSunk()) {
				gameOver = true;
			}
			if (hit) {
				initial = ocean.boatInitial(inputShot);
				boatName = ocean.boatName(inputShot);
				tooManyTurns = false;
			}

			human.updateGrid(inputShot, ocean.hit(inputShot), initial);
			human.updatePlayer(inputShot, hit, initial, boatName, sunk, gameOver, tooManyTurns, turns);
			if (!gameOver) {
				turns++;
			}
		}
		return turns;
	}
}
