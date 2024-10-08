package it.univr.agenda;

// Ci sono 4 TODO

/**
 * Una data. Gli oggetti Date sono ordinati per ordine cronologico.
 */
public abstract class Date implements Comparable<Date> {

	/**
	 * I giorni passati dall'inizio del 1900. Quindi 0 indica
	 * il primo gennaio 1900, mentre 1 indica il 2 gennaio 1900 e cosi' via.
	 */
	protected final int daysAfterStartOf1900;

	/**
	 * Costruttore.
	 * 
	 * @param daysAfterStartOf1900 i giorni passati dall'inizio del 1900
	 */
	protected Date(int daysAfterStartOf1900) {
		this.daysAfterStartOf1900 = daysAfterStartOf1900;
	}

	/**
	 * Restituisce la data days giorni dopo questa.
	 * 
	 * @param days i giorni di differenza fra questa data e il risultato
	 * @return la data risultante aggiungendo days giorni a questa data
	 * @throws IllegalArgumentException se days fosse negativo
	 */
	public abstract Date after(int days);

	/**
	 * Determina se questa data viene prima o coincide con other.
	 * 
	 * @param other l'altra data da confrontare con questa
	 */
	public boolean isBeforeOrEqualTo(Date other) {
		return compareTo(other) <=0;
	}

	/**
	 * Due Date sono uguali se rappresentano lo stesso giorno.
	 */
	@Override
	public boolean equals(Object other) {
		if(other instanceof Date) {
			return this.daysAfterStartOf1900 == ((Date) other).daysAfterStartOf1900;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return daysAfterStartOf1900;// non banale!
	}

	@Override
	public int compareTo(Date other) {
		return this.daysAfterStartOf1900 - other.daysAfterStartOf1900;
	}

	public abstract String toString();
}