import java.util.*;

public class PatrickZhangStrategy extends ComputerBattleshipPlayer {
	private ArrayList<String> shipsFound;
	private int row, column, counter, newCounterRow;
	private boolean shipLocated, HIT, findingDIR, firstShot;
	private int currentShipRow, currentShipCol, currentShipSize;
	private int lastHitRow, lastHitCol;
	private char currentShip;
	private String dir;
	// private boolean UP, DOWN, LEFT, RIGHT;
	// order will always go up, down, left, right
	private boolean[] spacesAroundShip;
	private ArrayList<Object[]> pendingShips;

	// when hitting, if it reaches the end of the ship
	private boolean endShip;
	// current # of hits on the current ship
	private int currentHits;
	private ArrayList<ArrayList<Integer>> spacesHit;

	private ArrayList<Integer> counterChanges;

	public void startGame() {
		initializeGrid();
		shipsFound = new ArrayList<String>();
		row = 0;
		column = 0;
		newCounterRow = 0;
		shipLocated = false;
		HIT = false;
		findingDIR = false;
		firstShot = false;
		currentShipRow = 0;
		currentShipCol = 0;
		currentShipSize = 0;
		lastHitRow = 0;
		lastHitCol = 0;
		currentShip = 'X';
		currentHits = 1;
		dir = "";

		spacesAroundShip = new boolean[4];
		for (int i = 0; i < 4; i++) {
			spacesAroundShip[i] = false;
		}
		counter = 2;

		endShip = false;
		pendingShips = new ArrayList<Object[]>();
		spacesHit = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < 10; i++) {
			spacesHit.add(new ArrayList<Integer>());
		}
		counterChanges = new ArrayList<Integer>();
	}

	public String playerName() {
		return "Patrick Zhang's Strategy";
	}

	public String author() {
		return "Patrick Zhang";
	}

	public void updatePlayer(Position pos, boolean hit, char initial, String boatName, boolean sunk, boolean gameOver,
			boolean tooManyTurns, int turns) {
		super.updatePlayer(pos, hit, initial, boatName, sunk, gameOver, tooManyTurns, turns);
		BattleshipGrid grid = getGrid();
		// if finding spaces around a ship that was found
		System.out.println(pos.toString());
		lastHitRow = pos.rowIndex();
		lastHitCol = pos.columnIndex();

		if (findingDIR) {
			if (hit) {
				if (initial == currentShip) {
					if ((pos.rowIndex() == currentShipRow - 1) || (pos.rowIndex() == currentShipRow + 1)) {
						dir = "v";
					} else {
						dir = "h";
					}
					// reset te spaces around ship now that we know the direction
					for (int i = 0; i < 4; i++) {
						spacesAroundShip[i] = false;
					}
					findingDIR = false;
					firstShot = true;
				} else {
					// if we find a different boat, we are adding it to the pending list
					addPendingBoat(initial, boatName, pos);
				}
			}
		} else {
			// if we know how to hit the boat now
			if (hit) {
				// if we were prev. hitting the same boat
				if (shipLocated) {
					if (initial == currentShip) {
						HIT = true;
					} else {
						HIT = false;
						addPendingBoat(initial, boatName, pos);
					}
				} else {
					// if we just found a new boat
					shipLocated = true;
					boolean inList = false;
					for (String boats : shipsFound) {
						if (boats.equals(boatName)) {
							inList = true;
						}
					}
					if (!inList) {
						shipsFound.add(boatName);
						currentShip = initial;
						shipSize();
						currentShipRow = pos.rowIndex();
						currentShipCol = pos.columnIndex();
					}
				}
			} else {
				HIT = false;
			}

			if (sunk && (initial == currentShip)) {
				System.out.println("SUNK!!");
				for (int i = 0; i < 4; i++) {
					spacesAroundShip[i] = false;
				}
				dir = "";
				if (pendingShips.size() > 0) {
					shipLocated = true;
					currentShip = (char) (pendingShips.get(0)[0]);
					currentShipRow = (int) (pendingShips.get(0)[1]);
					currentShipCol = (int) (pendingShips.get(0)[2]);

					shipSize();
					pendingShips.remove(0);
				} else {
					shipLocated = false;
					dir = "";
				}
				currentHits = 1;
				endShip = false;
				System.out.println(shipLocated);
			}
		}

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
	}

	private void addPendingBoat(char initial, String boatName, Position pos) {
		boolean inList = false;
		for (String found : shipsFound) {
			if (found.equals(boatName)) {
				inList = true;
			}
		}
		if (!inList) {
			Object[] temp = { initial, pos.rowIndex(), pos.columnIndex() };
			pendingShips.add(temp);
			shipsFound.add(boatName);
		}
	}

	private void shipSize() {
		if (currentShip == 'A') {
			currentShipSize = 5;
		} else if (currentShip == 'B') {
			currentShipSize = 4;
		} else if (currentShip == 'D') {
			currentShipSize = 2;
		} else {
			currentShipSize = 3;
		}
	}

	public Position shoot() {
		Position shot;
		boolean prevHit = true;
		if (!shipLocated) {
			shot = new Position(row, column);

			while (prevHit) {
				boolean hit = false;
				column += counter;
				if (column > 9) {
					row++;
					if (counter == 2) {
						if (row % 2 == 0) {
							column = 0;
						} else {
							column = column - 9;
						}
					} else {
						newCounterRow++;
						column = newCounterRow % counter;
					}

					if (newCounter(counter)) {
						column = 0;
						newCounterRow = 0;
						counterChanges.add(row - 1);
					}
				}

				if (row > 9) {
					prevHit = false;
					if (counterChanges.size() > 0) {
						row = counterChanges.get(0);
					} else {
						row = 0;
					}

				} else {
					if (!(spacesHit.get(row).contains(column))) {
						prevHit = false;
					}
				}

			}

		} else {
			if (dir.equals("")) {
				// need to reset all values associated with finding direction once it is found
				findingDIR = true;
				shot = determineDirection();
			} else {
				shot = shootShip();
			}
		}

//		System.out.println(shot.toString());
//		System.out.println(shot.rowIndex());
//		System.out.println(shot.columnIndex());

		if (shot.rowIndex() <= 9) {
			ArrayList<Integer> tempSpaces = spacesHit.remove(shot.rowIndex());
			tempSpaces.add(shot.columnIndex());
			spacesHit.add(shot.rowIndex(), tempSpaces);
		}

		return shot;
	}

	private boolean newCounter(int currentCounter) {
		boolean newCounter = true;

		if (shipsFound.contains("Destroyer")) {
			counter = 3;
			if (shipsFound.contains("Cruiser") && shipsFound.contains("Submarine")) {
				counter = 4;
				if (shipsFound.contains("Battleship")) {
					counter = 5;
				}
			}
		}
		if (counter == currentCounter) {
			newCounter = false;
		}
		return newCounter;
	}

	private Position determineDirection() {
		// System.out.println("determining direction");
		Position shot = new Position(0, 0);
		boolean shotFound = false;
		if (currentShipRow == 0) {
			spacesAroundShip[0] = true;
		}
		if (currentShipRow == 9) {
			spacesAroundShip[1] = true;
		}
		if (currentShipCol == 0) {
			spacesAroundShip[2] = true;
		}
		if (currentShipCol == 9) {
			spacesAroundShip[3] = true;
			// System.out.println("going here");
		}

		int count = 0;
		for (boolean status : spacesAroundShip) {
//			System.out.println(count + "" + status);
//			System.out.println(shotFound);
			if (!status) {
				if (!shotFound) {
					if (count == 0) {
						shot = new Position(currentShipRow - 1, currentShipCol);
						// System.out.println(count);
					} else if (count == 1) {
						shot = new Position(currentShipRow + 1, currentShipCol);
					} else if (count == 2) {
						shot = new Position(currentShipRow, currentShipCol - 1);
					} else {
						shot = new Position(currentShipRow, currentShipCol + 1);
					}
					shotFound = true;
					spacesAroundShip[count] = true;
				}
			}
			count++;
		}
		return shot;
	}

	private Position shootShip() {
		Position shot = new Position(currentShipRow, currentShipCol);

		if (firstShot) {
			// System.out.println("FIRSTSHOT");
			if (dir.equals("v")) {
				if (currentShipRow + 1 > 9) {
					endShip = true;
				}
			} else {
				if (currentShipCol + 1 > 9) {
					endShip = true;
				}
			}

			if (!endShip) {
				if (dir.equals("v")) {
					shot = new Position(currentShipRow + 1, currentShipCol);
				} else {
					shot = new Position(currentShipRow, currentShipCol + 1);
				}
				// currentHits ++;
			} else {
				currentHits--;
			}
			firstShot = false;
			endShip = false;
		} else {
			if (HIT) {
				currentHits++;

				System.out.println(endShip);
				// going down/ right first
				if (dir.equals("v")) {
					System.out.println("going here");
					if ((currentShipRow + 1 > 9) || (lastHitRow + 1 > 9)) {
						endShip = true;

					}
				} else {
					if ((currentShipCol + 1 > 9) || (lastHitCol + 1 > 9)) {
						endShip = true;
					}

				}
				// System.out.println("CURRENTHITS" + currentHits);
				if (!endShip) {
					if (dir.equals("v")) {
						shot = new Position(currentShipRow + currentHits, currentShipCol);
					} else {
						shot = new Position(currentShipRow, currentShipCol + currentHits);
					}
				}

			} else {
				// System.out.println("ENDSHIP");
				endShip = true;
			}

			if (endShip) {
				// going up/ let now
				if (dir.equals("v")) {
					shot = new Position(currentShipRow - (currentShipSize - currentHits), currentShipCol);
				} else {
					shot = new Position(currentShipRow, currentShipCol - (currentShipSize - currentHits));
				}
				// currentHits ++;
			}
			// System.out.println("currentHits " + currentHits);
			System.out.println(currentShipRow + 1);
			System.out.println(currentShipCol + 1);
			System.out.println(endShip + shot.toString());
		}

		return shot;
	}
}
