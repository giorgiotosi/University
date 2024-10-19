package it.univr.doodle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class Doodle {
	// per ogni persona, dice in quali slot temporali è disponibile
	private final Map<Person, Set<Slot>> availabilities = new HashMap<>();

	// aggiunge gli slot temporali "when" a quelli disponibili per person
	public void available(Person person, Slot... when) {
		//person è disponibile negli slot when e questa va inserito nella map
		SortedSet<Slot> slots = new TreeSet<>();
		for(Slot s : when) {
			slots.add(s);
		}
		availabilities.put(person, slots);
	}

	// aggiunge gli slot temporali "when" a quelli disponibili per person
	public void available(Person person, Iterable<Slot> when) {
		SortedSet<Slot> slots = new TreeSet<>();
		for(Slot s : when) {
			slots.add(s);
		}
		availabilities.put(person, slots);
	}

	// costruisce una tabella come negli esempi del compito:
	// nella prima riga tutti gli slot temporali in "availabilities",
	// in ordine crescente;
	// poi le disponibilità di ciascuna persona in "availabilities"
	// poi la priorità di ciascuno slot temporale (somma delle priorità di
	// chi può partecipare), con un asterisco a lato del primo slot
	// con piorità massima
	public String toString() {
		SortedMap<Slot, Integer> counter = new TreeMap<>();
		Set<Slot> disp = new TreeSet<>();
		String result = "";
		
		SortedSet<Slot> all = new TreeSet<>();
		for(Set<Slot> slots: availabilities.values()) {
			for(Slot slot: slots) {
				all.add(slot);
			}
		}
		
		for(Slot s: all) {
			result += s.toString() + "\t";
		}
		result += "\n";
		for(Person p: new TreeSet<>(availabilities.keySet())) {
			// slot in cui p è disponibile
			disp = availabilities.get(p);
			for(Slot s: all) {
				if(disp.contains(s)) {
					result += String.format("\t%s\t\t", "yes");
					counter.put(s,counter.getOrDefault(s, 0)+ priority(p));
				}
				else {
					result += String.format("\t%s\t\t", "no");
				}
			}
			result += String.format("%s", p.toString());
					 
			result += "\n";
		}
		
		int max = counter.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getValue).orElse(-1);
		
		for(int val : counter.values()) {
			if(val != max)
				result += String.format("\t%d\t\t", val);
			else
				result += String.format("\t%d*\t\t", val);
		}
		result += "\n";
		return result;
	}

	protected int priority(Person person) {
		return 1; // non modificate: per default i doodle danno a tutti la stessa priorità (1)
	}
}
