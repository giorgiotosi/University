package it.univr.doodle;

// uno slot temporale
public class Slot implements Comparable<Slot> {
	// giorno, mese, anno e momento del giorno
	private final int year;
	private final int month;
	private final int day;
	private final Moment moment;

	public Slot(int year, int month, int day, Moment moment) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.moment = moment;
	}

	public int compareTo(Slot other) {
		// deve comparare this e other in ordine temporale crescente
		int diff = year - other.year;
		if(diff != 0)
			return diff;
		diff = month - other.month;
		if(diff != 0)
			return diff;
		diff = day - other.day;
		if(diff != 0)
			return diff;
		
		return moment.compareTo(other.moment);
	}

	public boolean equals(Object other) {
		// this e other sono uguali se rappresentano lo stesso slot temporale
		return other instanceof Slot && year == ((Slot)other).year
				&& month == ((Slot)other).month && day == ((Slot)other).day
						&& moment.equals(((Slot)other).moment);
	}

	public int hashCode() {
		// deve essere non banale
		return year ^ month ^ day ^ moment.hashCode();
	}

	public String toString() { // completo, non modificatelo
		return day + "/" + month + "/" + year + " " + moment;
	}

	// si ricordi che le enum sono sempre comparabili, quindi
	// hanno un metodo compareTo, inserito dal compilatore;
	// inoltre hanno dei metodi equals e hashCode correttamente
	// definiti dal compilatore
	public enum Moment {
		MORNING,
		AFTERNOON,
		EVENING,
		NIGHT
	}
}
