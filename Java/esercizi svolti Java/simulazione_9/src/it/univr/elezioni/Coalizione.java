package it.univr.elezioni;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Coalizione implements Iterable<Partito> {

	private final String nome;
	private Set<Partito> partiti = new HashSet<>();
	// i partiti che fanno farte di questa coalizione


	public Coalizione(String nome, Partito... partiti) {
		this.nome = nome;
		if(partiti.length == 0)
			throw new IllegalArgumentException();
		for(Partito partito: partiti) {
			this.partiti.add(partito);
		} 
		
		
	}

	@Override
	public boolean equals(Object altro) {
		return altro instanceof Coalizione &&  nome.equals(((Coalizione) altro).nome);

	}

	@Override
	public int hashCode() {
		return nome.hashCode();
	}

	/**
	 * Rimuove il partito indicato da questa coalizione, se c'era.
	 *
	 * @param partito il partito da rimuovere
	 * @return true se e solo se la coalizione è diventata vuota
	 */

	public boolean rimuovi(Partito partito) {
		
		if(partiti.contains(partito))
			partiti.remove(partito);
		
		return partiti.isEmpty();

	}

	@Override
	public Iterator<Partito> iterator() {
		// deleghiamo all'insieme, che sa come iterare su se stesso
		return partiti.iterator();

	}

	public String getNome() {
		return nome;
	}
}