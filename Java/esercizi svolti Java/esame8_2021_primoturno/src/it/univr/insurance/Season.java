package it.univr.insurance;

import java.time.LocalDate;


// SI COMPLETI DOVE INDICATO CON TODO
// 25 minuti

// SI CONSEGNI Season.java ALLEGANDOLO IN CHAT O INVIANDOLO A fausto.spoto@univr.it

/**
 * L'enumerazione delle quattro stagioni.
 * 
 * La classe di libreria LocalDate e' documentata qui:
 * https://docs.oracle.com/en/java/javase/13/docs/api/java.base/java/time/LocalDate.html
 * 
 * Considerate le stagioni in questo modo:
 * 
 * Primavera: dal 21 marzo fino a 20 giugno
 * Estate: dal 21 giugno fino a 22 settembre
 * Autunno: dal 23 settembre fino a 20 dicembre
 * Inverno: dal 21 dicembre fino a 20 marzo
 */
public enum Season {
	// TODO
	// primavera, estate, autunno, inverno
	PRIMAVERA,
	ESTATE,
	AUTUNNO,
	INVERNO
	// un punto e virgola separa gli elementi dell'enumerazione
	// dai metodi dell'enumerazione
	;

	public static void main(String[] args) {
		System.out.println(Season.now()); // stampa la stagione attuale
		System.out.println(Season.of(LocalDate.of(2021, 6, 21))); // stampa SUMMER
	}

	/**
	 * Restituisce la stagione attuale.
	 */
	public static Season now() {
		return of(LocalDate.now()); 
	}

	/**
	 * Restituisce la stagione a cui appartiene il giorno indicato.
	 * 
	 * @param date il giorno
	 * @return la stagione a cui appartiene date
	 */
	public static Season of(LocalDate date) {
		int year = date.getYear();
		LocalDate solstizioPrimavera = LocalDate.of(year, 3,21);
		LocalDate solstizioEstate = LocalDate.of(year, 6,21);
		LocalDate solstizioAutunno = LocalDate.of(year, 9,23);
		LocalDate solstizioInverno = LocalDate.of(year, 12,21);
		
		if(date.isAfter(solstizioPrimavera) && date.isBefore(solstizioEstate) || date.isEqual(solstizioPrimavera)) {return PRIMAVERA;}
		if(date.isAfter(solstizioEstate) && date.isBefore(solstizioAutunno)|| date.isEqual(solstizioEstate)) {return ESTATE;}
		if(date.isAfter(solstizioAutunno) && date.isBefore(solstizioInverno) || date.isEqual(solstizioAutunno)) {return AUTUNNO;}
		if(date.isAfter(solstizioInverno) && date.isBefore(solstizioPrimavera) || date.isEqual(solstizioInverno)) {return INVERNO;}
		
		return null; 
	}
}