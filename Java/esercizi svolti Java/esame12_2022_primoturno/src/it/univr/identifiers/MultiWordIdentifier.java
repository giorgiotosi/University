package it.univr.identifiers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// un identificatore fatto da piu' parole in sequenza
public abstract class MultiWordIdentifier implements Identifier {

	// TODO
	List<String> identifier = new ArrayList<>();

	// fallisce con una IllegalArgumentException se non c'e' nessuna parola
	// o se c'e' una parola null oppure vuota
	// o se una parola contiene un carattere non alfabetico
	protected MultiWordIdentifier(String... words) throws IllegalArgumentException {		
		if(words.length == 0 ) {
			throw new IllegalArgumentException();
		}

		for(String s: words) {
			if(s == null || s=="" ) {
				throw new IllegalArgumentException();
			}
			for(int i =0; i< s.length(); i++) {
				if( s.charAt(i) >= 'a'   &&  s.charAt(i) <= 'z' && s.charAt(i) >= 'A'   &&  s.charAt(i) <= 'Z' ) {
					throw new IllegalArgumentException();
				}
			}
			identifier.add(s);
		}
		
		
		
	}

	// fallisce con un'eccezione nelle stesse condizioni viste sopra
	protected MultiWordIdentifier(Iterable<String> words) throws IllegalArgumentException {


		for(String s: words) {
			if(s == null || s=="" ) {
				throw new IllegalArgumentException();
			}
			for(int i =0; i< s.length(); i++) {
				if( s.charAt(i) >= 'a'   &&  s.charAt(i) <= 'z' && s.charAt(i) >= 'A'   &&  s.charAt(i) <= 'Z' ) {
					throw new IllegalArgumentException();
				}
			}
		}
			
			if(!identifier.addAll((Collection<? extends String>) words)) {
				throw new IllegalArgumentException();
			}
		
	}

	@Override
	public final String toString() {
		String result = "";
		int pos = 0;
		// TODO: si richiami il metodo ausiliario toString(pos, word)
		// e si concateni il risultato in un'unica stringa
		for(String word: identifier) {
			result += toString(pos, word);
			pos += word.length();
		}
		return result;
	}

	// restituisce la stringa con cui si stampa la componente pos-esima dell'identificatore
	protected abstract String toString(int pos, String word);

	// restituisce la concatenazione delle parole degli identificatori indicati
	protected static List<String> concat(MultiWordIdentifier... ids) {
		List<String> words = new ArrayList<>();
		for(MultiWordIdentifier id: ids) {
			for(String s : id.identifier) {
				words.add(s);
			}
		}
		return words;
	}

	// POTETE AGGIUNGERE METODI PRIVATE
}
