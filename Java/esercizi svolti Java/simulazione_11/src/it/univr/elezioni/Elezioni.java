package it.univr.elezioni;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


/**
 * Un oggetto di questa classe permette di registrare voti per dei partiti.
 * Iterando su questo oggetto, si ottengono delle coppie partito/voti ottenuti,
 * messe in ordine crescente per partito.
 */
public class Elezioni implements Iterable<VotiPerPartito> {
	
	
	Map<Partito, Integer> voti = new TreeMap<>();

	// registra un voto per il partito indicato
	public final void vota(Partito partito) {
		voti.put(partito, voti.getOrDefault(partito, 0) +1);
	}

	/**
	 * Ritorna una stringa che descrive l'elezione, del tipo:
		
		1        Bassotti:  4467 voti (28.11%)
		2         Caotico:  4679 voti (29.45%)
		3          Felice:  1591 voti (10.01%)
		4        Floreale:  3950 voti (24.86%)
		5      Pensionati:  1202 voti (7.56%)

	   I partiti sono riportati in ordine crescente, con a sinistra un indice
	   crescente del partito (da 1 in su). Dopo il nome del partito viene riportato
	   il numero dei voti ottenuti e la percentuale ottenuta fra tutti i voti espressi.
	 */
	@Override
	public String toString() {
		int sommaVoti = 0;
		for(Integer votiPartito : voti.values())
			sommaVoti += votiPartito;
		int counter = 1;
		String result = "";
		
		for(Partito p : voti.keySet()) {
			result += String.format("%d      %10s: %d voti (%.2f%%)\n", counter++, p, voti.get(p), voti.get(p)*100.00/sommaVoti );
		}
		
		
		return result; // modificare
	}

    /**
     * Iterando su questo oggetto, si ottengono delle coppie partito/voti ottenuti,
     * messe in ordine crescente per partito.
     */
	@Override
	public final Iterator<VotiPerPartito> iterator() {
		return new Iterator<VotiPerPartito>() {
			
			
			Iterator<Partito> it = voti.keySet().iterator();

			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public VotiPerPartito next() {
				Partito current = it.next();
				return new VotiPerPartito(current, voti.get(current));
			}};
	 
	}
}
