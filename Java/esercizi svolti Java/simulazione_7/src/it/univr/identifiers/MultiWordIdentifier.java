package it.univr.identifiers;

import java.util.ArrayList;
import java.util.List;

// un identificatore fatto da piu' parole in sequenza
public abstract class MultiWordIdentifier implements Identifier {

	private final String[] words;

	// fallisce con una IllegalArgumentException se non c'e' nessuna parola
	// o se c'e' una parola null oppure vuota
	// o se una parola contiene un carattere non alfabetico
	protected MultiWordIdentifier(String... words) throws IllegalArgumentException {
		
		if(words.length == 0)
			throw new IllegalArgumentException();
		
		for(String word: words) 
			if(word == null|| word.isEmpty() || !isAlphabetic(word))
				throw new IllegalArgumentException();
		
		this.words = words;
	}
	
	

	private boolean isAlphabetic(String word) {
		
		Character c;
		
		for(int i = 0; i< word.length(); i++) {
			c = word.charAt(i);
			if(!Character.isAlphabetic(c)) {
				return false;
			}
		}
		return true;
	}



	// fallisce con un'eccezione nelle stesse condizioni viste sopra
	protected MultiWordIdentifier(Iterable<String> words) throws IllegalArgumentException {
		this(intoArray(words));
	}

	private static String[] intoArray(Iterable<String> words) {
		
		List<String> list = new ArrayList<String>();
		for(String word: words)
			list .add(word);
		
		return list.toArray(new String[0]);
	}



	@Override
	public final String toString() {
		// si richiami il metodo ausiliario toString(pos, word)
		// e si concateni il risultato in un'unica stringa
		
		String result = "";
		int pos = 0;
		for(String word: words) {
			result += toString( pos, word);
			pos++;
		}
		return result;
		
	}

	// restituisce la stringa che descrive la componente pos-esima dell'identificatore
	protected abstract String toString(int pos, String word);

	// restituisce la concatenazione delle parole degli identificatori indicati
	protected static List<String> concat(MultiWordIdentifier... ids) {
		List<String> list = new ArrayList<>();
		for(MultiWordIdentifier id: ids) {
			for(String s: id.words) {
				list.add(s);
			}
		}
		return list;
	}

	// POTETE AGGIUNGERE METODI PRIVATE
}