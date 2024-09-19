package it.univr.library;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Un catalogo che stampa anche statistiche sui libri contenuti.
 */
public class CatalogWithStatistics extends Catalog {

	/**
	 * Costruisce un catalogo con statistiche. L'ordinamento sara' quello del
	 * compareTo definito in Book.
	 * 
	 * @param books i libri contenuti nel catalogo. Anche se ci fossero ripetizioni,
	 *              il catalogo dovra' contenere una sola istanza del libro ripetuto
	 */
	
	private int totPages;
	private int totMinutes;
	
	public CatalogWithStatistics(Book... books) {
		super(books);
		SortedSet<Book> setBooks = new TreeSet<>();
		for(Book book: books) {
			setBooks.add(book);
		}
		for(Book book: setBooks) {
			if (book instanceof PaperBook) {
				totPages += ((PaperBook) book).getPages();
			}
			else if (book instanceof AudioBook) {
				totMinutes += ((AudioBook) book).getMinutes();
			}
		}
	}

	/**
	 * Si comprta come il toString() di Catalog, ma alla fine aggiunge una riga del tipo:
	 * "This catalog contains paper books for a total of XXX pages and audiobooks for a total of YYY minutes"
	 */
	@Override
	public String toString() {
		return super.toString() + String.format(" This catalog contains paper books for a total of %d pages and audiobooks for a total of %d minutes", totPages, totMinutes); //TODO
	}
}