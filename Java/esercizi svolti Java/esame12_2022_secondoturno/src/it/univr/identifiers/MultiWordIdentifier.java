package it.univr.identifiers;

import java.util.ArrayList;
import java.util.List;

// un identificatore fatto da piu' parole in sequenza
public abstract class MultiWordIdentifier implements Identifier {
	
	private List<String> words = new ArrayList<>();

	// TODO

	// fallisce con una IllegalArgumentException se non c'e' nessuna parola
	// o se c'e' una parola null oppure vuota
	// o se una parola contiene un carattere non alfabetico
	protected MultiWordIdentifier(String... words) throws IllegalArgumentException {
		if(words.length == 0)
			throw new IllegalArgumentException();
		
		for(String s: words) {
			if(s == null || s== "" || !isAlphabetic(s) )
				throw new IllegalArgumentException();
			else
				this.words.add(s);
		}
		
	}

	// fallisce con un'eccezione nelle stesse condizioni viste sopra
	protected MultiWordIdentifier(Iterable<String> words) throws IllegalArgumentException {
		
		if(words.toString().length() == 0)
			throw new IllegalArgumentException();
		
		for(String s: words) {
			if(s == null || s== "" || !isAlphabetic(s) )
				throw new IllegalArgumentException();
			else
				this.words.add(s);
		}
		
	}

	@Override
	public final String toString() {
		// TODO: si richiami il metodo ausiliario toString(pos, word)
		// e si concateni il risultato in un'unica stringa
		int pos = 0;
		String result = "";
		for(String word: words) {
			result += toString(pos, word);
			pos+= word.length();
		}
			
		
		return result;
	}

	// restituisce la stringa che descrive la componente pos-esima dell'identificatore
	protected abstract String toString(int pos, String word);

	// restituisce la concatenazione delle parole degli identificatori indicati
	protected static List<String> concat(MultiWordIdentifier... ids) {
		List<String> newIdentifier = new ArrayList<>();
		
		for(MultiWordIdentifier id: ids) {
			for(String s: id.words) {
				newIdentifier.add(s);
			}
		}
		return newIdentifier; // TODO
	}

	// POTETE AGGIUNGERE METODI PRIVATE
	private boolean isAlphabetic(String word) {
		return word.chars().allMatch(Character::isAlphabetic);
	}
}