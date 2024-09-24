// CI SONO TRE METODI DA COMPLETARE

package it.univr.dates;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public interface Dates extends Iterable<Date> {

	/**
	 * Restituisce un oggetto Dates la cui iterazione sono le date
	 * dell'anno indicato, in ordine, dal primo gennaio al 31 dicembre.
	 * 
	 * @param year l'anno di cui voglio le date
	 * @return l'oggetto Dates restituito
	 */
	
	public static Dates of(boolean pippo, int year) {
		return new Dates() {

			@Override
			public Iterator<Date> iterator() {
				return new Iterator<Date>() {
					private Date cursor = new Date(1, 1, year);

					@Override
					public boolean hasNext() {
						return true;
					}

					@Override
					public Date next() {
						Date result = cursor;
						cursor = cursor.next();
						return result;
					}
				};
			}
		};
	}
	
	public static Dates of(int year) {
		return new Dates() {

			@Override
			public Iterator<Date> iterator() {
				return new Iterator<Date>() {
					private Date cursor = new Date(1, 1, year);
					private final Date end = new Date(31, 12, year);

					@Override
					public boolean hasNext() {
						return cursor.compareTo(end) <= 0;
					}

					@Override
					public Date next() {
						Date result = cursor;
						cursor = cursor.next();
						return result;
					}
				};
			}
		};
	}
	
	private static Dates of(List<Date> listDates) {
		return new Dates() {

			@Override
			public Iterator<Date> iterator() {
				return new Iterator<Date>() {
					private Date cursor = listDates.get(0);
					private final Date end = listDates.get(listDates.size()-1);

					@Override
					public boolean hasNext() {
						int nextIndex = listDates.indexOf(cursor) +1;
						
						return nextIndex <= listDates.indexOf(end);
					}

					@Override
					public Date next() {

						if(listDates.size() > 1) {
							return listDates.removeFirst();
						}
						int nextIndex = listDates.indexOf(cursor) + 1;
						if(nextIndex < listDates.size())
							cursor = listDates.get(nextIndex);
						
						return listDates.get(0);
						
					}
				};
			}
		};
	}

	/**
	 * Restituisce un oggetto Dates la cui iterazione sono le date
	 * dal primo gennaio dell'anno indicato in poi, in ordine.
	 * Si noti che sono in numero infinito.
	 * 
	 * @param year l'anno di cui voglio le date
	 * @return l'oggetto Dates restituito
	 */
	public static Dates from(int year) {
		while(true) {
			
				return of(true,year);
			
		}
	}

	/**
	 * Restituisce un oggetto Dates la cui iterazione sono howMany date
	 * casuali tra il 2000 e il 2100 (sono ammesse ripetizioni).
	 * 
	 * @param year l'anno di cui voglio le date
	 * @return l'oggetto Dates restituito
	 */
	public static Dates random(int howMany) {
		Date[] dates = new Date[howMany];
		
		for(int i =0; i<howMany; i++) {
			dates[i] = new Date();
		}
		
		List<Date> listDates = new ArrayList<>();
		for(Date d : dates) {
			listDates.add(d);
		}
		
		return  of(listDates);
	}

	/**
	 * Restituisce un oggetto Dates la cui iterazione sono howMany date
	 * casuali tra il 2000 e il 2100, con la garanzia che sono tutte diverse.
	 * 
	 * @param year l'anno di cui voglio le date
	 * @return l'oggetto Dates restituito
	 */
	public static Dates randomDistinct(int howMany) {
		Set<Date> setDates = new HashSet<>();
		while(setDates.size() != howMany) {
			setDates.add(new Date());
		}
		int i = 0;
		Date[] dates = new Date[howMany];
		for(Date date : setDates) {
			dates[i] = date;
			i++;
		}
		List<Date> listDates = new ArrayList<>();
		for(Date d : dates) {
			listDates.add(d);
		}
		
		return  of(listDates);
	}
}