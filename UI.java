/* created by Garlic
 * Anna Davison	16382333
 * James Kearns	15467622
 * Orla Keating	15205679
 */
package Sprint4;

import javax.swing.*;
import java.awt.*;

public class UI {

	private static final int FRAME_WIDTH = 1095;
	private static final int FRAME_HEIGHT = 700;

	private final BoardPanel boardPanel;
	private final InfoPanel infoPanel;
	private final CommandPanel commandPanel;
	private String input, playerName, tokenName, command, move;
	private int door;
	private boolean inputIsDone;

	UI(Tokens characters, Weapons weapons) {
		JFrame frame = new JFrame();
		infoPanel = new InfoPanel();
		commandPanel = new CommandPanel();
		boardPanel = new BoardPanel(characters, weapons);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("UCD Cluedo");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(boardPanel, BorderLayout.LINE_START);
		frame.add(infoPanel, BorderLayout.LINE_END);
		frame.add(commandPanel,BorderLayout.PAGE_END);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	/* Display Methods */

	public void display() {
		boardPanel.refresh();
	}

	private void displayString(String string) {
		infoPanel.addText(string);
	}

	public void displayMurderAnnouncement() {
		displayString("WELCOME TO CLUEDO");
		displayString("A murder has been committed.");
		displayString("You must solve the case.");
	}

	public void displayCardsDealt() {
		displayString("The cards have been dealt.");
	}

	public void displayDice(Player player, Dice dice) {
		displayString(player + " rolls " + dice + ".");
	}

	public void displayRollDraw() {
		displayString("Draw.");
	}

	public void displayRollWinner(Player player) {
		displayString(player + " wins the roll.");
	}

	private void displayNote(Player player, Deck deck, String cardName) {
		String displayName = String.format("%-18s",cardName);
		if (player.hasCard(cardName)) {
			displayString(displayName + "X");
		} else if (deck.isSharedCard(cardName)) {
			displayString(displayName + "A");
		} else {
			displayString(displayName + ".");
		}
	}

	public void displayNotes(Player player, Deck deck) {
		displayString("\n\t*****Detective Notes*****");
		displayString("\nSUSPECTS");
		for (String cardName : Names.SUSPECT_NAMES) {
			displayNote(player, deck, cardName);
		}
		displayString("\nWEAPONS");
		for (String cardName : Names.WEAPON_NAMES) {
			displayNote(player, deck, cardName);
		}
		displayString("\nROOMS");
		for (String cardName : Names.ROOM_CARD_NAMES) {
			displayNote(player, deck, cardName);
		}
	}

	public void displaySolution(Cards cards) {
		displayString("The solutions is: " + cards);
	}

	public void displayHelp(Token currentToken, boolean moveOver) {
		displayString("Available Commands:");
		//if in corridor - roll, cheat, done, exit, notes
		if(!currentToken.isInRoom())
		{
			displayString("Possible inputs:\n'roll' - rolls dice to move"
					+ "\n'cheat' - views cards in murder envelope\n'done' - ends turn"
					+ "\n'quit' - ends game\n'notes' - view notes");
		}
		//if entered room - done, exit, notes, (accuse)
		else if(currentToken.isInRoom() && moveOver == true)
		{
			displayString("Possible inputs:\n'cheat' - views cards in murder envelope"
					+ "\n'done' - ends turn\n'quit' - ends game\n'notes' - view notes");
			//TODO add accusations
		}
		//if start turn room - notes, roll, done, exit, (passage)
		else if(currentToken.isInRoom() && moveOver == false)
		{
			displayString("Possible inputs:\n'roll' - rolls dice to move"
					+ "\n'cheat' - views cards in murder envelope\n'done' - ends turn"
					+ "\n'quit' - ends game\n'notes' - view notes");
			if(currentToken.getRoom().hasPassage())
			{
				displayString("'passage' - move through secret passage");
			}
		}
//		displayString("roll = roll the dice and move your token.");
//		displayString("   u = up");
//		displayString("   d = down");
//		displayString("   l = left");
//		displayString("   r = right");
//		displayString("passage = move to another room via the passage.");
//		displayString("notes = see a record of your cards.");
//		displayString("done = end your turn.");
//		displayString("quit = end the game.");
	}

	/* Display Error Messages */

	private void displayError(String message) {
		displayString("Error: " + message + ".");
	}

	public void displayErrorNotADoor() {
		displayError("Not a door");
	}

	public void displayErrorInvalidMove() {
		displayError("Invalid move");
	}

	public void displayErrorAlreadyMoved() {
		displayError("Already moved this turn");
	}

	public void displayErrorNoPassage() {
		displayError("Not in a room with a passage");
	}

	/* User Input Methods */

	private void inputString() {
		input = commandPanel.getCommand();
	}

	public void inputName(Players playersSoFar) {
		boolean valid = false;
		inputIsDone = false;
		do {
			if (playersSoFar.getNumberOfPlayers() < 2) {
				displayString("Enter new player name:");
			} else {
				displayString("Enter new player name or done:");
			}
			inputString();
			displayString("> " + input);
			playerName = input.trim();
			if (playerName.isEmpty()) {
				displayError("Name is blank");
			} else if (playersSoFar.contains(playerName)) {
				displayError("Same name used twice");
			} else if (playersSoFar.getNumberOfPlayers() >= 2 && playerName.toLowerCase().equals("done")) {
				valid = true;
				inputIsDone = true;
			} else {
				valid = true;
			}
		} while (!valid);
	}

	public String getPlayerName() {
		return playerName;
	}

	public void inputToken(Tokens tokens) {
		boolean valid = false;
		do {
			displayString("Enter your character name:");
			inputString();
			displayString("> " + input);
			tokenName = input.trim().toLowerCase();
			if (tokens.contains(tokenName)) {
				if (!tokens.get(tokenName).isOwned()) {
					valid = true;
				} else {
					displayError("Character name already in use");
				}
			} else {
				displayError("Not a valid character name");
			}
		} while (!valid);
	}

	public String getTokenName() {
		return tokenName;
	}

	public boolean inputIsDone() {
		return inputIsDone;
	}

	public void inputCommand(Player player) {
		boolean valid = false;
		do {
			displayString(player + " type your command:");
			inputString();
			displayString("> " + input);
			command = input.trim().toLowerCase().replaceAll("( )+", " ");
			if (command.matches("quit|done|roll|passage|notes|cheat|help")) {
				valid = true;
			} else {
				displayError("No such command");
			}
		} while (!valid);
	}

	public String getCommand() {
		return command;
	}

	public void inputMove(Player player, int moveNumber, int movesAvailable) {
		boolean valid = false;
		do {
			displayString(player + " enter move " + moveNumber + " of " + movesAvailable + ":");
			inputString();
			displayString("> " + input);
			move = input.trim().toLowerCase();
			if (move.matches("[udlr]")) {
				valid = true;
			} else {
				displayError("Move must be u, d, l or r");
			}
		} while (!valid);
	}

	public String getMove() {
		return move;
	}

	public void inputDoor(Player player) {
		boolean valid = false;
		do {
			displayString(player + " enter door number:");
			inputString();
			displayString("> " + input);
			input = input.trim();
			if (input.matches("[1234]")) {
				door = Integer.valueOf(input);
				valid = true;
			} else {
				displayError("Input must be a number");
			}
		} while (!valid);
	}

	public int getDoor() {
		return door;
	}

}
