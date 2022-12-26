
public class PlayerEvaluator {
	private int max = 0;
	private int min = 100;
	private float average = 0;
	
	PlayerEvaluator(ComputerBattleshipPlayer player, int runs) {
		for(int i=0; i<runs; i++) {
			BattleshipGame game = new BattleshipGame(player);
			int turns = game.play();
			if (turns > max) {
				max = turns;
			}
			if (turns < min) {
				min = turns;
			}
			average += turns;
		}
		average = average / runs;
	}
	
	public int maxTurns() {
		return max;
	}
	
	public int minTurns() {
		return min;
	}
	
	public float averageTurns() {
		return average;
	}
}
