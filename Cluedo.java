package Sprint4;

public class Cluedo {

	private static final int MAX_NUM_PLAYERS = 6;

	private final Tokens tokens = new Tokens();
	private final Players players = new Players();
	private final Dice dice = new Dice();
	private final Map map = new Map();
	private final Weapons weapons = new Weapons(map);
	private final UI ui = new UI(tokens,weapons);
	private boolean moveOver;
	private Player currentPlayer;
	private Token currentToken;
	private final Deck deck = new Deck();

	private void announceTheGame() {
		ui.displayMurderAnnouncement();
	}

	private void inputPlayerNames() {
		int numPlayersSoFar = 0;
		do {
			ui.inputName(players);
			if (!ui.inputIsDone()) {
				ui.inputToken(tokens);
				Token token = tokens.get(ui.getTokenName());
				players.add(new Player(ui.getPlayerName(),token));
				token.setOwned();
				numPlayersSoFar++;
			}
		} while (!ui.inputIsDone() && numPlayersSoFar<MAX_NUM_PLAYERS);
	}

	private void rollToStart() {
		Players playersToRoll = new Players(players), playersWithHighRoll = new Players();
		boolean tie = false;
		do {
			int highRoll = 0;
			for (Player player : playersToRoll) {
				dice.roll();
				ui.displayDice(player,dice);
				if (dice.getTotal() > highRoll) {
					tie = false;
					highRoll = dice.getTotal();
					playersWithHighRoll.clear();
					playersWithHighRoll.add(player);
				} else if (dice.getTotal() == highRoll) {
					tie = true;
					playersWithHighRoll.add(player);
				}
			}
			if (tie) {
				ui.displayRollDraw();
				playersToRoll = new Players(playersWithHighRoll);
				playersWithHighRoll.clear();
			}
		} while (tie);
		players.setCurrentPlayer(playersWithHighRoll.get(0).getName());
		ui.displayRollWinner(players.getCurrentPlayer());
		ui.display();
	}

	private void dealCards() {
		deck.selectMurderCards();
		deck.prepareToDeal(players.getNumberOfPlayers());
		for (Player player : players) {
			player.addCards(deck.dealCards());
		}
		ui.displayCardsDealt();
	}

	private void roll() {
		if (!moveOver) {
			dice.roll();
			ui.displayDice(currentPlayer, dice);
			int squaresMoved = 0;
			if (currentToken.isInRoom()) {
				if (currentToken.getRoom().getNumberOfDoors()>1) {
					boolean exitDone = false;
					do {
						ui.inputDoor(currentPlayer);
						if (ui.getDoor()>= 1 || ui.getDoor()<=currentToken.getRoom().getNumberOfDoors()) {
							currentToken.leaveRoom(ui.getDoor()-1);
							exitDone = true;
						} else {
							ui.displayErrorNotADoor();

						}
					} while (!exitDone);
				} else {
					currentToken.leaveRoom();
				}
				ui.display();
			}
			do {
				ui.inputMove(currentPlayer, squaresMoved+1, dice.getTotal());
				Coordinates currentPosition = currentToken.getPosition();
				Coordinates newPosition;
				if (map.isValidMove(currentPosition, ui.getMove())) {
					newPosition = map.getNewPosition(currentPosition, ui.getMove());
					if (map.isDoor(currentPosition, newPosition)) {
						Room room = map.getRoom(newPosition);
						currentToken.enterRoom(room);
					} else {
						currentToken.setPosition(newPosition);
					}
					squaresMoved++;
					if (squaresMoved==dice.getTotal() || currentPlayer.getToken().isInRoom()) {
						moveOver = true;
					}
					ui.display();
				} else {
					ui.displayErrorInvalidMove();
				}
			} while (!moveOver);
		} else {
			ui.displayErrorAlreadyMoved();
		}
	}

	private void passage() {
		if (!moveOver) {
			if (currentToken.isInRoom() && currentToken.getRoom().hasPassage()) {
				Room destination = currentToken.getRoom().getPassageDestination();
				currentToken.leaveRoom();
				currentToken.enterRoom(destination);
				moveOver = true;
				ui.display();
			} else {
				ui.displayErrorNoPassage();
			}
		} else {
			ui.displayErrorAlreadyMoved();
		}
	}

	private void question() {
		//make sure it's valid - only when entering a room
		
		//input suspect, weapon, and what room they're in
		
		//move suspect and weapon into room(swap weapon)
		
		//loops between other players
	}
	
	private void accuse() {
		//check murder cards if true that player wins
		// if wrong that player has no more turns
		
		//maybe return boolean turn over is false and player.turnOver()
	}
	
	private void takeTurns() {
		boolean turnOver, gameOver = false;
		do {
			turnOver = false;
			moveOver = false;
			do {
				currentPlayer = players.getCurrentPlayer();
				currentToken = currentPlayer.getToken();
				ui.inputCommand(currentPlayer);
				
				switch (ui.getCommand()) {
				case "roll": {
					roll();
					break;
				}
				case "passage": {
					passage();
					break;
				}
				case "notes": {
					ui.displayNotes(currentPlayer, deck);
					break;
				}
				case "cheat" : {
					ui.displaySolution(deck.getMurderCards());
					break;
				}
				case "help" : {
					ui.displayHelp(currentToken, moveOver);
					break;
				}
				case "done": {
					turnOver = true;
					break;
				}
				case "quit": {
					turnOver = true;
					gameOver = true;
					break;
				}
				case "question": {
					question();
					break;
				}
				case "accuse": {
					accuse();
					break;
				}
				}
			} while (!turnOver);
			if (!gameOver) {
				players.turnOver();
			}
		} while (!gameOver);
	}

	public static void main(String[] args) {
		Cluedo game = new Cluedo();
		game.announceTheGame();
		game.inputPlayerNames();
		game.dealCards();
		game.rollToStart();
		game.takeTurns();
		System.exit(0);
	}
}
