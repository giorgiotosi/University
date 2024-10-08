package it.univr.identifiers;

//TODO: fate compilare questa classe

public class SnakeStyleIdentifier extends MultiWordIdentifier {

	// costruisce un identicatore snake-style le cui parole sono quelle indicate;
	// fallisce nelle stesse condizioni del costruttore della superclasse
	public SnakeStyleIdentifier(String... words) {
		super(words);
	}

	// come sopra
	
	public SnakeStyleIdentifier(Iterable<String> words) {
		super(words);
	}

	// costruisce un identificatore snake-style le cui parole componenti
	// sono la concatenazione delle parole degli ids
	
	public SnakeStyleIdentifier(MultiWordIdentifier... ids) {
		super(concat(ids));
	}

	// restituisce un identificatore three-style con le stesse parole di this
	
	public ThreeStyleIdentifier toThreeStyle() {
		return new ThreeStyleIdentifier(this);
	}

	@Override
	protected String toString(int pos, String word) {
		return pos == 0 ? word: "_" + word;
	}
}