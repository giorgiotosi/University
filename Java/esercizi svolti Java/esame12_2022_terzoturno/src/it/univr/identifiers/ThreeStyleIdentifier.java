package it.univr.identifiers;

// TODO: fate compilare questa classe

public class ThreeStyleIdentifier extends MultiWordIdentifier {

	// costruisce un identicatore three-style le cui parole sono quelle indicate;
	// fallisce nelle stesse condizioni del costruttore della superclasse
	
	public ThreeStyleIdentifier(String... words) {
		super(words);
	}

	// come sopra
	
	public ThreeStyleIdentifier(Iterable<String> words) {
		super(words);
	}

	// costruisce un identificatore three-style le cui parole componenti
	// sono la concatenazione delle parole degli ids
	
	public ThreeStyleIdentifier(MultiWordIdentifier... ids) {
		super(concat(ids));
	}

	// restituisce un identificatore snake-style con le stesse parole di this
	
	public SnakeStyleIdentifier toSnakeStyle() {
		return new SnakeStyleIdentifier(this);
	}

	@Override
	protected String toString(int pos, String word) {
		String result = "";
		
		if(word.length() <= 3) {
			result = word;
		}
		else
			result = word.substring(0, 3);
		
		return result;
	}
}