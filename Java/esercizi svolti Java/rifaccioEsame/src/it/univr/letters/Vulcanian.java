package it.univr.letters;

import java.util.Arrays;

/**
 * Una sequenza vulcaniana di lettere, cioe'
 * un caso particolare di sequenza LowerCase che e' fatta da due parti:
 * la prima parte contiene vocali in ordine alfabetico;
 * la seconda parte contiene consonanti in ordine alfabetico.
 */
public class Vulcanian extends LowerCase {

	// AGGIUNGERE QUI CAMPI PRIVATI SE SERVISSERO

	/**
	 * Crea una sequenza vulcaniana di length lettere.
	 * 
	 * @param length la lunghezza della sequenza da creare
	 * @throws IllegalArgumentException se length e' negativo
	 */	
	public Vulcanian(int length) {
		super(randomVulcanian(length));
	}

	/**
	 * Genera una stringa vulcaniana casuale lunga length.
	 */
	private static String randomVulcanian(int length) {
		
	    if (length < 2) {
	        throw new IllegalArgumentException("La lunghezza deve essere almeno 2 per garantire sia vocali che consonanti.");
	    }
		
	    String vocali = "", consonanti = "";
		int boundVocali = 1 + random.nextInt(length-1);
		int numeroCons = length - boundVocali;
		char[] voc = new char[boundVocali];
		char[] cons = new char[numeroCons];
		char c;
		
		//vocali
		for(int i = 0; i<boundVocali; i++) {
			do {
			c= (char) ('a' + random.nextInt(26));
			}while(!isVowel(c));
			voc[i] = c;
		}
		Arrays.sort(voc);
		for(char c1 : voc)
			vocali += c1;
		
		//consonanti
		for(int i = 0; i<numeroCons; i++) {
			do {
			c= (char) ('a' + random.nextInt(26));
			}while(isVowel(c));
			cons[i] = c;
		}
		Arrays.sort(cons);
		for(char c2 : cons)
			consonanti += c2;
		
		String result = "";
		result = vocali + consonanti ;

		return result;
	}

	private static boolean isVowel(char c) {
		// TODO Auto-generated method stub
		return c=='a' || c=='e' || c=='i' || c=='o' || c=='u';
	}

	/**
	 * Crea una sequenza vulcaniana fatta dai caratteri di s,
	 * identici, nello stesso ordine.
	 * 
	 * @param s la stringa che contiene i caratteri da inserire nella sequenza
	 * @throws IllegalArgumentException se i caratteri di s non formano
	 *                                  una sequenza vulcaniana
	 */
	public Vulcanian(String s) {
		super(s);
		char[] charArr = new char[s.length()];
		charArr = s.toCharArray();
		
		for(int i =0 ; i<s.length()-1; i++){
			if( !isVowel(charArr[i]) && isVowel(charArr[i+1]) ) {
				throw new IllegalArgumentException();
			}
			if( isVowel(charArr[i]) && isVowel(charArr[i+1]) 
					&& charArr[i] > charArr[i+1]) {
				throw new IllegalArgumentException();
			}
			if( !isVowel(charArr[i]) && !isVowel(charArr[i+1]) 
					&& charArr[i] > charArr[i+1]) {
				throw new IllegalArgumentException();
			}
			
		}
	}
}