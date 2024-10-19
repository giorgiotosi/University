package it.univr.identifiers;

// TODO: fate compilare questa classe

public class VowelStyleIdentifier extends MultiWordIdentifier {

	// costruisce un identicatore vowel-style le cui parole sono quelle indicate;
	// fallisce nelle stesse condizioni del costruttore della superclasse
	public VowelStyleIdentifier(String... words) {
		super(words);
	}

	// come sopra
	public VowelStyleIdentifier(Iterable<String> words) {
		super(words);
	}

	// costruisce un identificatore vowel-style le cui parole componenti
	// sono la concatenazione delle parole degli ids
	public VowelStyleIdentifier(MultiWordIdentifier... ids) {
		super(concat(ids));
	}

	// restituisce un identificatore snake-style con le stesse parole di this
	public SnakeStyleIdentifier toSnakeStyle() {
		// TODO
		return new SnakeStyleIdentifier(this);
	}

	@Override
	protected String toString(int pos, String word) {
		String result = "";
		for(char c: word.toCharArray()) {
			if(isVowel(c)) {
				result += Character.toUpperCase(c);
			}
			else
				result += Character.toLowerCase(c);
		}
		return result;
	}
	
	private boolean isVowel(char c) {
		if(c== 'a' ||c== 'e' ||c== 'i' ||c== 'o' ||c== 'u') {
			return true;
		}
		else
			return false;
	}
}