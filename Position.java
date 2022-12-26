import java.util.*;

public class Position {
	private char ROW;
	private int COL, rowIndex, columnIndex;

	private char[] letters = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };

	Position(char character, int number) {
		ROW = character;
		COL = number;
		setPositionData(this);
	}

	Position(int row, int col) {
		rowIndex = row;
		columnIndex = col;
		ROW = letters[rowIndex];
		COL = col + 1;
	}

	Position() {
		Scanner input = new Scanner(System.in);
		Position pos = PositionChecker.checkPosition(input.nextLine());
		setPositionData(pos);
	}

	private void setPositionData(Position pos) {
		ROW = pos.row();
		COL = pos.column();
		if (pos.row() == 'X') {
			rowIndex = -1;
		} else {
			for (int i = 0; i < letters.length; i++) {
				if (letters[i] == pos.row()) {
					rowIndex = i;
				}
			}
		}

		columnIndex = pos.column() - 1;
	}

	public char row() {
		return ROW;
	}

	public int column() {
		return COL;
	}

	public String toString() {
		return ROW + "-" + COL;
	}

	public int rowIndex() {
		return rowIndex;
	}

	public int columnIndex() {
		return columnIndex;
	}
}
