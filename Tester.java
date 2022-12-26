
public class Tester {
	public static void main(String[] args) {
		BattleshipPlayer player = new BattleshipPlayer();
		BattleshipGame game = new BattleshipGame(player);
		game.play();
		
		ComputerBattleshipPlayer comp = new PatrickZhangStrategy();
		PlayerEvaluator evaluate = new PlayerEvaluator(comp, 10000);
		System.out.println("The minimum amount of turns: " + evaluate.minTurns());
		System.out.println("The maximum amount of turns: " + evaluate.maxTurns());
		System.out.println("The average amount of turns: " + evaluate.averageTurns());
		}
}


