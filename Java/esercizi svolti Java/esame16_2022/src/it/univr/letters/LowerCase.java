package it.univr.letters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Una sequenza di caratteri "minuscola", cioe' fatta da
 * lettere minuscole dell'alfabeto inglese.
 * Sono ammesse lettere ripetute.
 */
public class LowerCase implements Letters {
	protected final static Random random = new Random();
	// AGGIUNGERE QUI CAMPI PRIVATI SE SERVISSERO
	private List<Character> seqMinAlfRip = new ArrayList<>();
 
	/**
	 * Crea una sequenza minuscola casuale.
	 * 
	 * @param length la lunghezza della sequenza da creare
	 * @throws IllegalArgumentException se length e' negativo
	 */
	public LowerCase(int length) {
		if(length < 0) {
			throw new IllegalArgumentException();
		}
		
		Random random = new Random();
		int ascii = '?';
		char c = 0;

		for(int i=0; i<length; i++) {
			ascii = '?';
			while(!(((char) ascii>= 'a' && (char) ascii <= 'z' )
					||((char) ascii >= 'A' && (char) ascii <= 'Z'))) {
				ascii = random.nextInt();
				if((((char) ascii>= 'a' && (char) ascii <= 'z' )
						||((char) ascii >= 'A' && (char) ascii <= 'Z'))) {
					c = (char) ascii;
					c = Character.toLowerCase(c);
					
				}
				
				
			}
			seqMinAlfRip.add(c);
		}
	}

	/**
	 * Crea una sequenza minuscola fatta dai caratteri di s, identici,
	 * nello stesso ordine.
	 * 
	 * @param s la stringa che contiene i caratteri da inserire nella sequenza
	 * @throws IllegalArgumentException se i caratteri di s non sono una sequenza minuscola
	 */
	public LowerCase(String s) {
		
		char[] stringa = new char[s.length()];
		stringa = s.toCharArray();
		for(char c: stringa) {
			if(!Character.isLowerCase(c))
				throw new IllegalArgumentException();
			seqMinAlfRip.add(c);
		}
	}

	@Override
	public final int length() {
		return seqMinAlfRip.size();
	}

	@Override
	public final String toString() {
		String s = "";
		for(char c: seqMinAlfRip) {
			s += c;
		}
		return s; // MODIFICARE E COMPLETARE
	}

	@Override
	public final void forEach(Consumer<Character> what) {
		for(char c: seqMinAlfRip) {
			what.accept(c);
		}
	}
}