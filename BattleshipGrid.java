import java.util.*;

public class BattleshipGrid {
	private ArrayList<Position> misses = new ArrayList<Position>();
	private ArrayList<Object[]> hits = new ArrayList<Object[]>();

	public void shotAt(Position pos, boolean hit, char initial) {
		if (hit) {
			Object[] temp = { pos, initial };
			hits.add(temp);
		} else {
			misses.add(pos);
		}
	}

	public boolean hit(Position pos) {
		boolean hit = false;
		for (Object[] info : hits) {
			if (equals((Position) info[0], pos)) {
				hit = true;
			}
		}
		return hit;
	}

	public boolean miss(Position pos) {
		boolean miss = false;
		for (Position MISS : misses) {
			if (equals(MISS, pos)) {
				miss = true;
			}
		}
		return miss;
	}

	public boolean empty(Position pos) {
		boolean empty = true;
		if ((miss(pos)) || (hit(pos))) {
			empty = false;
		}
		return empty;
	}

	public char boatInitial(Position pos) {
		char initial = 'X';
		for (Object[] info : hits) {
			if (equals((Position) info[0], pos)) {
				initial = (char) info[1];
			}
		}
		return initial;
	}

	private boolean equals(Position pos, Position pos2) {
		boolean equals = false;
		if ((pos.row() == pos2.row()) && (pos.column() == pos2.column())) {
			equals = true;
		}
		return equals;
	}
}
