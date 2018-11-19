package Sprint4;

/* created by Garlic
 * Anna Davison	16382333
 * James Kearns	15467622
 * Orla Keating	15205679
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Tokens implements Iterable<Token>, Iterator<Token> {

	private final ArrayList<Token> tokens = new ArrayList<>();
	private Iterator<Token> iterator;

	Tokens() {
		tokens.add(new Token(Names.SUSPECT_NAMES[0], new Color(148, 0, 211), new Coordinates(23,19))); //plum
		tokens.add(new Token(Names.SUSPECT_NAMES[1], Color.WHITE, new Coordinates(9,0))); //white
		tokens.add(new Token(Names.SUSPECT_NAMES[2], Color.RED, new Coordinates(7,24))); //scarlett
		tokens.add(new Token(Names.SUSPECT_NAMES[3], Color.GREEN, new Coordinates(14,0))); //green
		tokens.add(new Token(Names.SUSPECT_NAMES[4], Color.YELLOW, new Coordinates(0,17))); //mustard
		tokens.add(new Token(Names.SUSPECT_NAMES[5], new Color(0, 191, 255), new Coordinates(23,6))); //peacock
	}

	//returns true if tokens have names
	public boolean contains(String name) {
		for (Token token : tokens) {
			if (token.hasName(name)) {
				return true;
			}
		}
		return false;
	}

	public Token get(String name) {
		for (Token token : tokens) {
			if (token.hasName(name)) {
				return token;
			}
		}
		return null;
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public Token next() {
		return iterator.next();
	}

	public Iterator<Token> iterator() {
		iterator = tokens.iterator();
		return iterator;
	}

}
