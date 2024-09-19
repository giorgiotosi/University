package it.univr.words;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * Una lista di parole, estratte da un file di testo.
 */
public class Words extends ArrayList<String>{ 


	/**
	 * Crea una lista di parole, estraendole dal file di testo
	 * col nome indicato.
	 * 
	 * @param fileName il nome del file di testo
	 * @throws IOException se si verifica un errore di accesso al file
	 */
	public Words(String fileName) throws IOException {
		this(fileName, s -> true);
		
	}

	/**
	 * Crea una lista di parole, estraendole dal file di testo
	 * col nome indicato e selezionando solo quelle che soddisfano
	 * il selettore fornito.
	 * 
	 * @param fileName il nome del file di testo
	 * @param selector il selettore usato per selezionare le parole da estrarre
	 * @throws IOException se si verifica un errore di accesso al file
	 */
	public Words(String fileName, Predicate<String> selector) throws IOException {
		
		String line;
		
		try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
			
			while(( line = reader.readLine()) != null ) {
				
				for(String word: line.split("\\W+")) {
					if(selector.test(word))
						this.add(word);
				}
				
				
			}
		}
	}

	/**
	 * Ridefinito in modo da fargli stampare "a list of XX words", dove
	 * "XX e' la lunghezza di questa lista di parole.
	 */
	@Override
	public String toString() {
		return "a list of " + this.size() + " words"; 
	}

	/**
	 * Restituisce la parola piu' frequente fra quelle presenti in questa
	 * lista di parole.
	 * 
	 * @return la parola piu' frequente
	 * @throws NoSuchElementException se questa lista e' vuota
	 */
	public String mostFrequent() {
		
		if(this.isEmpty())
			throw new NoSuchElementException("I have selected zero words");
		
		Map<String,Integer> frequenze = new HashMap<>();
		
		for(String word: this) {
			frequenze.put(word, frequenze.getOrDefault(word, 0)+1);
		}
		
		String result = null;
		int max = 0;
		
		for(String word: frequenze.keySet()) {
			if(result == null ||  frequenze.get(word) > max) {
				result = word;
				max = frequenze.get(word);
			}
		}
		
		return result; 
	}
}
