package it.univr.letters;

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
	private String stringa;

	/**
	 * Crea una sequenza minuscola casuale.
	 * 
	 * @param length la lunghezza della sequenza da creare
	 * @throws IllegalArgumentException se length e' negativo
	 */
	public LowerCase(int length) {
		if(length<0)
			throw new IllegalArgumentException();
		
		stringa  ="";
		for(int i = 0; i < length; i++) {
			stringa += (char) ('a' + random.nextInt(26));
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
		stringa  ="";
		char[] charArr = new char[s.length()];
		charArr = s.toCharArray();
		for(char c : charArr) {
			if(!(c>='a' && c<='z')) {
				throw new IllegalArgumentException();
			}
			else
				stringa += c;
		}
		
	}

	@Override
	public final int length() {
		return stringa.length(); // MODIFICARE E COMPLETARE
	}

	@Override
	public final String toString() {
		return stringa; // MODIFICARE E COMPLETARE
	}

	@Override
	public final void forEach(Consumer<Character> what) {
		char[] charArr = new char[stringa.length()];
		charArr = stringa.toCharArray();
		for(char c : charArr) {
			what.accept(c);
		}
	}
}