package Sprint4;

import java.util.ArrayList;
import java.util.Iterator;

public class Players implements Iterable<Player>, Iterator<Player> {

	private ArrayList<Player> players = new ArrayList<>();
	private int currentPlayerIndex;
	private Iterator<Player> iterator;

	Players() {
	}

	Players(Players players) {
		this.players = new ArrayList<>();
		for (Player player : players) {
			this.players.add(player);
		}
	}

	public void clear() {
		players.clear();
	}

	public void add(Player player) {
		players.add(player);
	}

	public boolean contains(String name) {
		for (Player player : players) {
			if (player.hasName(name)) {
				return true;
			}
		}
		return false;
	}

	public int getNumberOfPlayers() {
		return players.size();
	}

	public Player get(String name) {
		for (Player player : players) {
			if (player.hasName(name)) {
				return player;
			}
		}
		return null;
	}

	public Player get(int index) {
		return players.get(index);
	}

	public void setCurrentPlayer(String name) {
		currentPlayerIndex = 0;
		while (!players.get(currentPlayerIndex).hasName(name)) {
			currentPlayerIndex++;
		}
	}

	public Player getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}

	public void turnOver() {
		if (currentPlayerIndex < players.size()-1) {
			currentPlayerIndex++;
		} else {
			currentPlayerIndex = 0;
		}
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public Player next() {
		return iterator.next();
	}

	public Iterator<Player> iterator() {
		iterator = players.iterator();
		return iterator;
	}

}
