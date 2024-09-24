package it.univr.doodle;

public class WeightedDoodle extends Doodle{

	
	protected int priority(Person person) {
		return person.priority(); // non modificate: per default i doodle danno a tutti la stessa priorità (1)
	}
}
