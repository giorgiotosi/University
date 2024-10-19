package it.univr.elezioni;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Un oggetto di questa classe permette di registrare voti per dei partiti.
 * Iterando su questo oggetto, si ottengono delle coppie partito/voti ottenuti,
 * messe in ordine crescente per partito.
 */
public class Elezioni implements Iterable<VotiPerPartito> {
	
	private int numTotVoti = 0;
	private SortedMap<Partito, Integer> voti = new TreeMap<>();

	// registra un voto per il partito indicato
	public final void vota(Partito partito) {
		voti.put(partito, voti.getOrDefault(partito, 0)+1);
		numTotVoti++;
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
		int counter = 0;
		String result = "";
		for(Partito p: voti.keySet()) {
			result += String.format("%d ", ++counter) + p.toString() + ": " + voti.get(p) + " voti" + String.format(" (%.2f%%)\n",(voti.get(p)*100.00)/numTotVoti);	
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
			
			Partito current = null;

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return current == voti.lastKey() ? false : true;
			}

			@Override
			public VotiPerPartito next() {
				boolean flag = false;
				for(Partito p : voti.keySet()) {
					if(flag) {
						current = p;
						break;
					}
					
					if(current == p )
						flag =true;
					if(current == null) {
						current = voti.firstKey();
						break;
					}
				}
				flag = false;
				return new VotiPerPartito(current,voti.get(current));
			}} ;
		
	}
}
