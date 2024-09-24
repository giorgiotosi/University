package it.univr.doodle;

// si renda questa classe comparabile con un'altra Person:
// ordinando prima per priorita' crescente e poi alfabeticamente per nome
// nota: dovrete aggiungere un metodo public: quale?
public abstract class Person implements Comparable<Person>{
	private final String name;
	
	protected Person(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name; // deve restituire il nome
	}
	
	public abstract int priority();

	public boolean equals(Object other) {
		return other instanceof Person && name.equals(((Person)other).name)
				&& this.priority() == ((Person)other).priority(); // devono avere stesso nome e stessa priorità
	}
	
	public int compareTo(Person other) {
		int diff = this.priority() - other.priority();
		if(diff != 0)
			return diff;
		else
			return name.compareTo(other.name);
	}
	
}
