import java.util.*;

public class Boat {
	private String ship, direction;
	private Position startPos;
	private ArrayList<Position> spacesOccupied = new ArrayList<Position>();
	private ArrayList<Position> hits = new ArrayList<Position>();

	Boat(String name, Position start, String dir) {
		ship = name;
		startPos = start;
		direction = dir;
		boatSpaces();
	}

	private void boatSpaces() {
		int size = size();
		for (int i = 0; i < size; i++) {
			if (direction.equals("vertical")) {
				Position pos = new Position(startPos.rowIndex() + i, startPos.columnIndex());
				spacesOccupied.add(pos);
			}
			if (direction.equals("horizontal")) {
				Position pos = new Position(startPos.rowIndex(), startPos.columnIndex() + i);
				spacesOccupied.add(pos);
			}
		}
	}

	public String name() {
		return ship;
	}

	public char abbreviation() {
		char type = 'D';
		if (ship.equals("Aircraft Carrier")) {
			type = 'A';
		} else if (ship.equals("Battleship")) {
			type = 'B';
		} else if (ship.equals("Cruiser")) {
			type = 'C';
		} else if (ship.equals("Submarine")) {
			type = 'S';
		}
		return type;
	}

	public int size() {
		int size = 3;
		char type = abbreviation();
		if (type == 'A') {
			size = 5;
		} else if (type == 'B') {
			size = 4;
		} else if (type == 'D') {
			size = 2;
		}
		return size;
	}

	public boolean onBoat(Position pos) {
		boolean onBoat = false;
		for (Position space : spacesOccupied) {
			if (equals(space, pos)) {
				onBoat = true;
			}
		}
		return onBoat;
	}

	public boolean isHit(Position pos) {
		boolean isHit = false;
		for (Position hit : hits) {
			if (equals(hit, pos)) {
				isHit = true;
			}
		}
		return isHit;
	}

	public void hit(Position pos) {
		if (onBoat(pos)) {
			if (!(isHit(pos))) {
				hits.add(pos);
			}
		}
	}

	public boolean sunk() {
		boolean sunk = false;
		if (hits.size() == spacesOccupied.size()) {
			sunk = true;
		}
		return sunk;
	}

	public Position position() {
		return startPos;
	}

	public String direction() {
		return direction;
	}

	private boolean equals(Position pos, Position pos2) {
		boolean equals = false;
		if ((pos.row() == pos2.row()) && (pos.column() == pos2.column())) {
			equals = true;
		}
		return equals;
	}
}


