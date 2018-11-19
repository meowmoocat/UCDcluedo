package Sprint4;

/* created by Garlic
 * Anna Davison	16382333
 * James Kearns	15467622
 * Orla Keating	15205679
 */

final public class Names {

	public final static String[] SUSPECT_NAMES = {"Bleakley Plum", "Kelly White", "Caka Scarlett", "Sweeney Green", "Kalra Mustard", "Delorey Peacock"};
	public final static String[] WEAPON_NAMES = {"Book", "Bored", "Gradcap", "Librocop", "Microscope", "Seagull"};
	public final static String[] ROOM_NAMES = {"Computer Science", "O'Reilly Hall", "Engineering", "Sutherland", "Quinn",
			"Newman", "Library", "Ag Science", "O'Brien", "Lake"};
	public final static String[] ROOM_CARD_NAMES = new String[ROOM_NAMES.length-1];  // exclude Lake

	static {
		System.arraycopy(ROOM_NAMES, 0, ROOM_CARD_NAMES, 0, ROOM_CARD_NAMES.length);
	}

}
