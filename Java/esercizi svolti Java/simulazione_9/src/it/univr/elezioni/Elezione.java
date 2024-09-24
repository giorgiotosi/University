package it.univr.elezioni;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Elezione {
	
	private int numVoti = 0;
	
	/**
	 * Le coalizioni registrate per questa elezione. Se un partito si presenta da solo,
	 * stara’ in una coalizione in cui e’ presente solo esso stesso
	 */
	private final Set<Coalizione> coalizioni = new HashSet<Coalizione>();
	/**
	 * Una mappa che associa a ciascun partito i voti che ha preso fino ad ora
	 */
	private final Map<Partito, Integer> votiPerPartito = new HashMap<Partito, Integer>();
	/**
	 * Registra il partito indicato come un partecipante all’elezione, dentro una
	 * coalizione di cui fa parte solo esso stesso. Se il partito e’ gia’ registrato,
	 * genera una PartitoGiaRegistratoException.
	 */
	public void registra(Partito partito) { 
		
		boolean partitoRegistato = false;

		for(Coalizione c: coalizioni) {
			for(Partito p: c) {
				if(p.equals(partito)) {
					partitoRegistato = true;
				}
			}
		}
		
		if(partitoRegistato)
			throw new PartitoGiàRegistratoException(partito);
		Coalizione entry = new Coalizione(partito.getNome(), partito);
		coalizioni.add(entry);
	}
	/**
	 * Registra la coalizione indicata come partecipante all’elezione. Se esiste gia’ una
	 * coalizione uguale, deve generare una eccezione di classe CoalizioneGiaPresenteException.
	 * Altrimenti i partiti della coalizione vengono eliminati da tutte le altre coalizioni,
	 * se ne facevano parte. Tali coalizioni, se in tal modo diventassero vuote, devono venire
	 * de-registrate (eliminate) dall’elezione.
	 * Tutti i partiti della coalizione registrata sono automaticamente registrati all’elezione
	 */
	public void registra(Coalizione coalizione) { 



		if(coalizioni.contains(coalizione))
			throw new CoalizioneGiàPresenteException(coalizione);

		//i partiti della coalizione vengono eliminati da tutte le altre coalizioni, se ne facevano parte
		for(Coalizione c: coalizioni) {
			boolean coalizioneVuota = false;
			for(Partito p: c) {
				for(Partito partito: coalizione) {
					if(partito.equals(p))
						coalizioneVuota = c.rimuovi(p);
					if(coalizioneVuota) //Tali coalizioni, se in tal modo diventassero vuote, devono venire de-registrate (eliminate) dall’elezione.
						coalizioni.remove(c);
				}
			}
		}

		coalizioni.add(coalizione);


	}
	/**
	 * Registra un nuovo voto per il partito indicato (e quindi anche per la coalizione di cui esso fa parte).
	 * Se il partito non e’ registrato all’elezione, genera una PartitoMaiRegistratoException.
	 */
	public void registraVotoPer(Partito partito) { 

		boolean partitoRegistato = false;

		for(Coalizione c: coalizioni) {
			for(Partito p: c) {
				if(p.equals(partito)) {
					partitoRegistato = true;
				}
			}
		}

		if(!partitoRegistato)
			throw new PartitoMaiRegistratoException(partito);


		votiPerPartito.put(partito, votiPerPartito.getOrDefault(partito, 0)+1);
		numVoti++;

	}
	@Override public String toString() { 
		
		int numVotiCoalizione;
		String result = "";
		
		for(Coalizione c: coalizioni) {
			numVotiCoalizione = 0;
			result += "Coalizione" + " \"" + c.getNome() + "\":\n";
			for(Partito p: c) {
				//result += "  " + p.getNome() + ": voti " + votiPerPartito.get(p) + " (" +  (votiPerPartito.get(p)*100.00/numVoti)  +"%%)\n";
				result += String.format("  %s: voti %d (%.2f%%)\n", p.getNome(), votiPerPartito.get(p), votiPerPartito.get(p)*100.00/numVoti);
				numVotiCoalizione += votiPerPartito.get(p);
			}
			//result += "Totale voti alla coalizione: " + numVotiCoalizione + " (" +  (numVotiCoalizione*100.00/numVoti)  +"%%)\n\n";
			result += String.format("Totale voti alla coalizione: %d (%.2f%%)\n\n",numVotiCoalizione, numVotiCoalizione*100.00/numVoti);
		}


		return result;
	}
}