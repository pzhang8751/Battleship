
public class PositionChecker {
	public static Position checkPosition(String inputPos) {
		char[] letters = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };
		String[] nums = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
		char letter = 'X';
		int num = 0;

		if (!(inputPos.length() < 3)) {
			for (int i = 0; i < letters.length; i++) {
				if (inputPos.substring(0, 1).equals(letters[i] + "")) {
					letter = letters[i];
				}
				if (inputPos.substring(2).equals(nums[i])) {
					num = i + 1;
				}
			}

			if (!(inputPos.substring(1, 2).equals("-")) || (letter == 'X') || (num == 0)) {
				letter = 'X';
				num = 0;
			}
		}

		Position pos = new Position(letter, num);
		return pos;
	}
}
