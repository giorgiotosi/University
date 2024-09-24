package it.univr.elezioni;

//import java.util.Map;

/**
 * Un tipo di elezione la cui stampa aggiunge l'indicazione
 * di quale partito ha vinto le elezioni.
 */
public class ElezioniVincitore extends Elezioni {

	/**
	 * Si comporta come il toString() della superclasse, ma in piu'
	 * aggiunge in fondo l'indicazione del partito che ha vinto le elezioni,
	 * del tipo "Vince X".
	 * In particolare, vince l'elezione il partito che ha ottenuto piu' voti.
	 * A parita' di voti, vince il partito che viene prima in ordine alfabetico.
	 * Se non ci fossero partiti (elezione vuota), aggiunge l'indicazione
	 * "Non ci sono vincitori".
	 */
	@Override
	public String toString() {
		String result = super.toString();
		
		VotiPerPartito vincitore = null;
		for (VotiPerPartito vpp: this)
			if (vincitore == null || vincitore.voti < vpp.voti)
				vincitore = vpp;

		if (vincitore == null)
			return result + "\n Non ci sono vincitori\n";
		else
			return result + "\n Vince " + vincitore.partito + "\n";
		//result += "\n Vince " + trovoMax();
		//return result; // modificare
	}

	/*private String trovoMax() {
		Partito p = voti.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElseGet(null);
		if(p==null) {
			return "Non ci sono vincitori";
		}
		return p.toString();
	}*/
}