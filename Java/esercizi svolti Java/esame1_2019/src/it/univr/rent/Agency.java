package it.univr.rent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

// Ci sono 5 TODO

public class Agency {
	
	String name; //nome agenzia
	SortedSet<Model> modelliAgenzia = new TreeSet<>();
	Map<Model, Integer> noleggi = new HashMap<>();

	/**
	 * Crea un'agenzia di noleggio col nome indicato,
	 * che fornisce in noleggio i modelli indicati.
	 * 
	 * @param fleet i modelli noleggiabili con l'agenzia
	 * @throws IllegalArgumentException se fleet e' vuoto
	 */
	public Agency(String name, Model... fleet) {
		// TODO
		this.name = name;
		if(fleet.length != 0) {
			for (Model modello: fleet) {
				modelliAgenzia.add(modello);
				noleggi.put(modello, 0);
			}
		}
		else {
			throw new IllegalArgumentException("non ci sono modelli");
		}
	}

	/**
	 * Restituisce l'insieme dei modelli noleggiabili con questa agenzia
	 * e con la patente indicata.
	 * 
	 * @param license la patente
	 */
	public Set<Model> modelsAvailableForLicense(License license) {
		Set<Model> modelliNoleggiabili = new HashSet<>();
		
		
		if(license == License.valueOf("A")){
			
			for (Model modello: modelliAgenzia)
				if(modello instanceof Motorbike)
					modelliNoleggiabili.add(modello);
			
		}
		
		if(license == License.valueOf("B")){
			
			for (Model modello: modelliAgenzia)
				if(modello instanceof Motorbike || modello instanceof Car)
					modelliNoleggiabili.add(modello);
			
		}
		
		if(license == License.valueOf("C")){
			
			for (Model modello: modelliAgenzia)
				if(modello instanceof Motorbike  || modello instanceof Car  || modello instanceof Truck)
					modelliNoleggiabili.add(modello);
			
		}
		
		if(license == License.valueOf("D")){
			
			for (Model modello: modelliAgenzia)
				if(modello instanceof Motorbike || modello instanceof Car  || modello instanceof Bus)
					modelliNoleggiabili.add(modello);
			
		}
		
		return modelliNoleggiabili;
	}

	/**
	 * Pende nota che qualcuno ha noleggiato con questa agenzia un dato modello,
	 * per una certa quantita' di giorni, usando la patente indicata.
	 * 
	 * @param model il modello noleggiato
	 * @param license la patente
	 * @param days il numero di giorni di noleggio
	 * @throws IllegalLicenseException se il modello non si puo' guidare
	 *                                 con la patente indicata
	 * @throws ModelNotAvailableException se il modello non e' fra quelli
	 *                                    noleggiabili con questa agenzia
	 */
	public void rent(Model model, License license, int days)  {
		// TODO
		if(!modelliAgenzia.contains(model) ) {
			throw new ModelNotAvailableException();
		}
		if(!modelsAvailableForLicense(license).contains(model) ) {
			throw new IllegalLicenseException();
		}
		noleggi.put(model, noleggi.getOrDefault(model, 0) + days);
	}

	/**
	 * Restituisce il modello che e' stato noleggiato in totale per piu' giorni
	 * con questa agenzia.
	 */
	public Model mostRented() {
		
	    return noleggi
			    		.entrySet() // trasforma mappa in un insieme(set) di coppie chiave-valore
			            .stream() // converte in stream, una sequenza di elementi che supporta operazioni aggregate su questi elementi.
			            .max(Map.Entry.comparingByValue()) // trovo la coppia che ha il valore più alto (il modello che è stato noleggiato per più giorni)
			            .map(Map.Entry::getKey)  // estraggo la chiave della coppia trovata prima (ovvero solo il modello)
			            .orElse(null); // se max non ha restituito una entry ritorno null
	    
	}

	/**
	 * Restituisce una stringa con in prima riga il nome dell'agenzia e,
	 * sotto di essa, la lista dei modelli noleggiabili con questa agenzia,
	 * ordinati secondo l'ordinamento fra i modelli.
	 */
	@Override
	public String toString() {
		String result;
		result =  "Agency " + this.name +  ". Available models:";
		int length = result.length();
		result += "\n";
		while (result.length() <= length * 2)
			result += "-";
		result += "\n";
		for (Model modello: modelliAgenzia)
			result += modello.toString() + "\t" + modello.pricePerDay() + " euros per day\n";
		return result; // TODO
	}
}