import java.util.ArrayList;

public class Ocean {
	private ArrayList<Boat> boats = new ArrayList<Boat>();
	private int boatSize;
	private String dir;
	private int posRow, posCol;
	private ArrayList<ArrayList<Position>> allSpaces = new ArrayList<ArrayList<Position>>();
	private ArrayList<Position> hitSpaces = new ArrayList<Position>();

	public void placeBoat(String boatName, String direction, Position pos) throws Exception {
		dir = direction;
		posRow = pos.rowIndex();
		posCol = pos.columnIndex();

		if (boats.size() == 5) {
			throw (new Exception());
		}

		if (!checkBounds(boatName)) {
			throw (new Exception());
		}

		Boat Boat = new Boat(boatName, pos, direction);

		if (checkOverlap()) {
			throw (new Exception());
		}

		boats.add(Boat);
		ArrayList<Position> spacesOccupied = new ArrayList<Position>();

		for (int i = 0; i < boatSize; i++) {
			if (dir.equals("vertical")) {
				Position tempPos = new Position(posRow + i, posCol);
				spacesOccupied.add(tempPos);
			} else {
				Position tempPos = new Position(posRow, posCol + i);
				spacesOccupied.add(tempPos);
			}
		}
		allSpaces.add(spacesOccupied);
	}

	private boolean checkBounds(String name) {
		boolean inBound = true;
		if ((posRow < 0) || (posCol < 0)) {
			inBound = false;
		}
		if ((posRow > 9) || (posCol > 9)) {
			inBound = false;
		}

		boatSize = 3;
		if (name.equals("Aircraft Carrier")) {
			boatSize = 5;
		} else if (name.equals("Battleship")) {
			boatSize = 4;
		} else if (name.equals("Destroyer")) {
			boatSize = 2;
		}

		int finIndex;
		if (dir.equals("vertical")) {
			finIndex = posRow - 1 + boatSize;
		} else {
			finIndex = posCol - 1 + boatSize;
		}
		if (finIndex > 9) {
			inBound = false;
		}
		return inBound;
	}

	private boolean checkOverlap() {
		boolean overlap = false;
		ArrayList<Position> spacesOccupied = new ArrayList<Position>();
		for (int i = 0; i < boatSize; i++) {
			if (dir.equals("vertical")) {
				Position tempPos = new Position(posRow + i, posCol);
				spacesOccupied.add(tempPos);
			} else {
				Position tempPos = new Position(posRow, posCol + i);
				spacesOccupied.add(tempPos);
			}
		}
		ArrayList<Boat> boatsTemp = new ArrayList<Boat>();

		for (Boat BOAT : boats) {
			if (BOAT.direction().equals(dir)) {
				if (dir.equals("vertical")) {
					if (posCol == BOAT.position().columnIndex()) {
						boatsTemp.add(BOAT);
					}
				} else {
					if (posRow == BOAT.position().rowIndex()) {
						boatsTemp.add(BOAT);
					}
				}
			} else {
				boatsTemp.add(BOAT);
			}
		}

		for (Boat BOAT : boatsTemp) {
			for (Position space : spacesOccupied) {
				if (BOAT.onBoat(space)) {
					overlap = true;
				}
			}
		}
		return overlap;
	}

	private boolean equals(Position pos, Position pos2) {
		boolean equals = false;
		if ((pos.row() == pos2.row()) && (pos.column() == pos2.column())) {
			equals = true;
		}
		return equals;
	}

	private Boat getBoat(Position pos) {
		Boat Boat = new Boat("Aircraft Carrier", new Position(0, 0), "vertical");
		for (int i = 0; i < allSpaces.size(); i++) {
			for (Position spaces : allSpaces.get(i)) {
				if (equals(pos, spaces)) {
					Boat = boats.get(i);
				}
			}
		}
		return Boat;
	}

	public void shootAt(Position pos) {
		getBoat(pos).hit(pos);
		for (int i = 0; i < allSpaces.size(); i++) {
			for (Position spaces : allSpaces.get(i)) {
				if (equals(pos, spaces)) {
					hitSpaces.add(pos);
				}
			}
		}
	}

	public boolean hit(Position pos) {
		boolean hit = false;
		for (Position position : hitSpaces) {
			if (equals(pos, position)) {
				hit = true;
			}
		}
		return hit;
	}

	public char boatInitial(Position pos) {
		return getBoat(pos).abbreviation();
	}

	public String boatName(Position pos) {
		return getBoat(pos).name();
	}

	public boolean sunk(Position pos) {
		return getBoat(pos).sunk();
	}

	public boolean allSunk() {
		boolean allSunk = true;
		for (Boat Boat : boats) {
			if (!Boat.sunk()) {
				allSunk = false;
			}
		}
		return allSunk;
	}

	public void placeAllBoats() {
		String[] boatNames = { "Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer" };
		for (String name : boatNames) {
			boolean success = false;
			while (success == false) {
				int xIndex = (int) (Math.random() * (10));
				int yIndex = (int) (Math.random() * (10));
				Position startPos = new Position(xIndex, yIndex);
				int direction = (int) (Math.random() * 2);
				try {
					if (direction == 0) {
						placeBoat(name, "vertical", startPos);
					} else {
						placeBoat(name, "horizontal", startPos);
					}
					success = true;
				} catch (Exception ex) {
				}
			}
		}
	}
}
