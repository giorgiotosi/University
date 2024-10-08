package it.univr.insurance;

import java.time.temporal.*;
import java.time.LocalDate;

// SI COMPLETI DOVE INDICATO CON TODO
// 25 minuti

//SI CONSEGNI Insurance.java ALLEGANDOLO IN CHAT O INVIANDOLO A fausto.spoto@univr.it

/**
 * Un'assicurazione contro la pioggia, che copre un periodo temporale
 * fra due giorni. Si faccia uso dell'enumerazione Season delle quattro stagioni.
 * 
 * La classe di libreria LocalDate e' documentata qui:
 * https://docs.oracle.com/en/java/javase/13/docs/api/java.base/java/time/LocalDate.html
 */
public class Insurance {
	private final LocalDate start, end;
	/**
	 * Crea un'assicurazione che copre i giorni da start (incluso)
	 * ad end (incluso).
	 * 
	 * @param start il giorno di partenza dell'assicuzione (incluso)
	 * @param end il giorno di fine dell'assicurazione (incluso)
	 * @throws IllegalArgumentException se end viene prima di start
	 */
	public Insurance(LocalDate start, LocalDate end) {
		if(end.isBefore(start))
			throw new IllegalArgumentException();
		this.start = start;
		this.end = end;
	}

	/**
	 * Restituisce il costo per assicurare un giorno indicato.
	 * 
	 * @param when il giorno di cui si vuole conoscere il costo di assicurazione
	 * @return 100 se when e' in inverno; 50 se when e' in primavera o autunno;
	 *         20 se when e' in estate
	 */
	private int costFor(LocalDate when) {
		
		Season season = Season.of(when);
		
		return switch(season) {
		case WINTER -> 100;
		case SUMMER -> 20;
		default -> 50;
		};
		
		
	}

	/**
	 * Restituisce il costo di questa assicurazione: cioe' la somma dei costi
	 * per assicurare i giorni coperti da questa assicurazione.
	 * 
	 * @return il costo di questa assicurazione
	 */
	public long cost() {
		int somma = 0;
		for(int i = 0; i < start.until(end, ChronoUnit.DAYS) +1; i++) {
			somma += costFor(start.plusDays(i));
		}
		
		return somma;
		}

	public static void main(String[] args) {
		LocalDate d1 = LocalDate.of(2022, 3, 18);
		LocalDate d2 = LocalDate.of(2022, 3, 30);

		// assicuro tre giorni invernali e dieci primaverili
		Insurance i1 = new Insurance(d1, d2);

		System.out.println(i1.cost()); // stampa 800 cioe' 3 * 100 + 10 * 50

		// genera un'eccezione
		new Insurance(d2, d1);
	}

	/**
	 * L'enumerazione delle quattro stagioni.
	 */
	public enum Season {
		SPRING, SUMMER, FALL, WINTER; // primavera, estate, autunno, inverno

		/**
		 * Restituisce la stagione del giorno indicato.
		 * 
		 * @param date il giorno
		 * @return la stagione a cui appartiene date
		 */
		public static Season of(LocalDate date) {
			int year = date.getYear();

			LocalDate startOfSpring = LocalDate.of(year, 3, 21);
			if (date.isBefore(startOfSpring))
				return WINTER;

			LocalDate startOfSummer = LocalDate.of(year, 6, 21);
			if (date.isBefore(startOfSummer))
				return SPRING;

			LocalDate startOfFall = LocalDate.of(year, 9, 23);
			if (date.isBefore(startOfFall))
				return SUMMER;

			LocalDate startOfWinter = LocalDate.of(year, 12, 21);
			if (date.isBefore(startOfWinter))
				return FALL;

			return WINTER;
		}
	}
}