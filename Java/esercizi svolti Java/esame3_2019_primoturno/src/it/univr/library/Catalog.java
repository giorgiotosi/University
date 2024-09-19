package it.univr.library;

//import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

//import java.util.stream.Collectors;


/**
 * Un catalogo contiene dei libri (senza ripetizioni).
 */
public class Catalog implements Iterable<Book> {

	/**
	 * Costruisce un catalogo. L'ordinamento sara' quello del
	 * compareTo definito in Book.
	 * 
	 * @param books i libri contenuti nel catalogo. Anche se ci fossero ripetizioni,
	 *              il catalogo dovra' contenere una sola istanza del libro ripetuto
	 */
	
	private final Set<Book> Setbooks = new LinkedHashSet<Book>();
	
	public Catalog(Book... books) {
		for(Book book: books) {
			Setbooks.add(book);
		}
	}

	/**
	 * Costruisce un catalogo. L'ordinamento sara' quello del comparatore fornito.
	 * 
	 * @param comparator l'oggetto che determina l'ordine fra due libri
	 * @param books i libri contenuti nel catalogo. Anche se ci fossero ripetizioni,
	 *              il catalogo dovra' contenere una sola istanza del libro ripetuto
	 */
	public Catalog(Comparator<Book> comparator, Book... books) {
		List<Book> Listbooks = new ArrayList<Book>();
		for(Book book: books) {
			Listbooks.add(book);
		}
		java.util.Collections.sort(Listbooks, comparator);
		for(Book book: Listbooks) {
			Setbooks.add(book);
		}
		
	}

	@Override
	public Iterator<Book> iterator() {
		return Setbooks.iterator();
	}

	/**
	 * Restituisce il toString dei libri contenuti in questo catalogo, nel loro ordine,
	 * andando a capo dopo ciascuno di essi.
	 */
	@Override
	public String toString() {
		String result="";
		
		for(Book book: Setbooks) {
			result += book.toString() + "\n";
		}
		
		return result;
	}
}