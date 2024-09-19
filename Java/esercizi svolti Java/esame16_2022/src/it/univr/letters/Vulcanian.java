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
		//System.out.println("ciao");
		int ascii=0;
		char c = 0;
		String Vowels = "";
		int vowelsBound = random.nextInt(length);
		int consonantsNumber = length - vowelsBound;
		String Consonants = "";
		for(; Vowels.length()<vowelsBound;) {
			ascii = '?';
			while(!(((char) ascii>= 'a' && (char) ascii <= 'z' )
					||((char) ascii >= 'A' && (char) ascii <= 'Z'))) {
				ascii = random.nextInt();
				if((((char) ascii>= 'a' && (char) ascii <= 'z' )
						||((char) ascii >= 'A' && (char) ascii <= 'Z')) && isVowel((char)ascii)) {
					c = (char) ascii;
					c = Character.toLowerCase(c);
					Vowels += c;
				}

			}

			
		}
		//System.out.println(">>>vocali :" + Vowels);
	
		for(; Consonants.length()<consonantsNumber; ) {
			ascii = '?';
			while(!(((char) ascii>= 'a' && (char) ascii <= 'z' )
					||((char) ascii >= 'A' && (char) ascii <= 'Z'))) {
				ascii = random.nextInt();
				if((((char) ascii>= 'a' && (char) ascii <= 'z' )
						||((char) ascii >= 'A' && (char) ascii <= 'Z')) && isConsonant((char)ascii)) {
					c = (char) ascii;
					c = Character.toLowerCase(c);
					Consonants += c;
				}
			
			}
			
		}
		//consonanti in ordine alfabetico
		char[] cons = new char[Consonants.length()];
		cons = Consonants.toCharArray();
		Arrays.sort(cons);
		Consonants= "";
		for(char ch: cons)
			Consonants += ch;
		//vocali in ordine alfabetico
		char[] voc = new char[Vowels.length()];
		voc = Vowels.toCharArray();
		Arrays.sort(voc);
		Vowels= "";
		for(char ch: voc)
			Vowels += ch;
		//System.out.println(">>>consonanti :" + Consonants);
		//System.out.println( Vowels + Consonants);
		return Vowels+Consonants; // MODIFICARE E COMPLETARE
	}

	private static boolean isConsonant(char c) {
		// TODO Auto-generated method stub
		if(isVowel(c))
			return false;
		else 
			return true;
	}

	private static boolean isVowel(char c) {
		return c == 'a'|| c == 'e'|| c == 'i'|| c == 'o'|| c == 'u';
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
		super(checkAndReturn(s));
	}

	private static String checkAndReturn(String s) {
		char[] string = new char[s.length()];
		string = s.toCharArray();
		for(char c: string) {
			if(!Character.isAlphabetic(c)) {
				throw new IllegalArgumentException();
			}
		}
		// controllo ora che formino una sequenza vulcaniana
		for(int i = 0; i< string.length - 1; i++) {
			if(isConsonant(string[i]) && isVowel(string[i+1])) {
				throw new IllegalArgumentException();
			}
			if(isConsonant(string[i]) && isConsonant(string[i+1])) {
				
				
					if(string[i] > string[i+1]) {
						throw new IllegalArgumentException();
				}
			}
			if(isVowel(string[i]) && isVowel(string[i+1])) {
				
				
					if(string[i] > string[i+1]) {
						throw new IllegalArgumentException();
				}
			}
		}
 		return s;
	}
}