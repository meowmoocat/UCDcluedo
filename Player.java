package Sprint4;

public class Player {

	private final String name;
	private final Token token;
	private Cards cards;

	Player(String name, Token token) {
		this.name = name;
		this.token = token;
		cards = new Cards();
	}

	public boolean hasName(String name) {
		return this.name.toLowerCase().trim().equals(name.toLowerCase().trim());
	}

	public String getName() {
		return name;
	}

	public Token getToken() {
		return token;
	}

	public void addCards(Cards cards) {
		this.cards = cards;
	}

	public boolean hasCard(String name) {
		return cards.contains(name);
	}

	@Override
	public String toString() {
		return name + " (" + token.getName() + ")";
	}
}


