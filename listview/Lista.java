package listview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.qt.NonNull;
import io.qt.core.QAbstractListModel;
import io.qt.core.QByteArray;
import io.qt.core.QHash;
import io.qt.core.QMetaObject;
import io.qt.core.QMetaProperty;
import io.qt.core.QModelIndex;
import io.qt.core.QVariant;
import io.qt.core.Qt;

/**
 * Classe generica Lista{@code <T>} che estende QAbstractListModel
 * per gestire una lista di oggetti di tipo T che devono estendere QObject.
 *
 * @param <T> il tipo di oggetto gestito dalla lista, deve estendere QObject
 */

public class Lista<T extends io.qt.core.QObject> extends QAbstractListModel {

	public final List<T> items; 
	
	public final Map<Integer, String> roleToPropertyMap = new HashMap<>(); 
	
	/** Mappa che associa i ruoli ai nomi delle proprietà */
	private QHash<Integer, QByteArray> roles;
	
	private static QMetaObject metaObject;
	
	
	/**
	 * Il costruttore Lista (List{@code <T>} items) costruisce la lista e inizializza metaObject
	 * infine chiama il metodo generateRoleNames()
	 * 
	 * @param items la lista di oggetti di tipo T da gestire
	 * @throws IllegalArgumentException se la lista degli elementi è vuota
	 */
	
	public  Lista (List<T> items) {
		if(items.isEmpty())
			throw new IllegalArgumentException("non posso costruire una lista se non ho elementi");
		
		this.items = items;
		Lista.metaObject = items.get(0).metaObject();
		generateRoleNames(); // Genera dinamicamente i ruoli basati sulle proprietà di T
	}

    /**
     * rowCount(arg0) ritorna il numero di elementi
     *
     * @param arg0 parametro non utilizzato ma richiesto dall'override
     * @return il numero di elementi nella lista
     */
	
	@Override
	public int rowCount(@NonNull QModelIndex arg0) {
		return items.size();
	}

    /**
     * data(index, role) restituisce il valore della proprietà associata al ruolo
     *
     * @param index l'indice della riga
     * @param role il ruolo specifico associato alla proprietà
     * @return il valore della proprietà come QVariant o null se non valido
     */
	
	@Override
	public Object data(@NonNull QModelIndex index, int role) {
		if (index.row() < 0 || index.row() >= items.size()) {
			return null; //indice non valido
		}

		T item = items.get(index.row());

		// Ottieni il nome della proprietà associata al ruolo
		String propertyName = roleToPropertyMap.get(role);

		// Controlla se il nome della proprietà è valido
		if (propertyName != null) {
			QMetaProperty property = metaObject.property(propertyName); // Ottieni la QMetaProperty
			return new QVariant(property.read(item));
		}

		return null; //nome della proprietà non valido
	}
	
    /**
     * generateRoleNames() genera i nomi dei ruoli per le proprietà di T.
     * 
     */
	
	private void generateRoleNames() {

		int role = Qt.ItemDataRole.UserRole; // Inizia a mappare i ruoli da UserRole in poi

		// Scorri tutte le proprietà esposte tramite il metaObject
		for (int i = 0; i < metaObject.propertyCount(); i++) {
			QMetaProperty property = metaObject.property(i);
			String propertyName = property.name();
			roleToPropertyMap.put(role++, propertyName); // Associa un ruolo al nome della proprietà
		}
		
		Map<Integer, QByteArray> map = new HashMap<Integer, QByteArray>();

		// Mappa i nomi delle proprietà ai ruoli Qt
		for (Map.Entry<Integer, String> entry : roleToPropertyMap.entrySet()) {
			map.put(entry.getKey(), new QByteArray(entry.getValue().getBytes()));
		}

		roles = new QHash<Integer, QByteArray>(map);

	}
	
	/**
	 * roleNames() restituisce una mappa QHash che associa ruoli alle proprietà.
	 * 
	 * QHash è il formato richiesto da Qt per gestire i ruoli nei modelli.
	 * 
	 * @return QHash che associa i ruoli (interi) ai nomi delle proprietà (come QByteArray).
	 */

	@Override
	public QHash<Integer, QByteArray> roleNames() {
		
		return roles;
	}
	
	//Operazioni di modifica degli elementi

	/**
	 * addElement(T newElement) aggiunge un nuovo elemento alla lista e aggiorna il modello.
	 * 
	 * @param newElement l'elemento di tipo T da aggiungere alla lista
	 */
	
	public void addElement(T newElement) {
		
	    if (newElement == null) {
	        throw new IllegalArgumentException("L'elemento da aggiungere non può essere null");
	    }
	    
		int newIndex = items.size();
		
		beginInsertRows(new QModelIndex(), newIndex, newIndex);
		items.add(newElement); 
		endInsertRows(); 
	}
	
	/**
     * removeElementAt(int index) rimuove un elemento dalla lista e aggiorna il modello.
     * 
     * @param index l'indice dell'elemento da rimuovere
     */
	
	public void removeElementAt(int index) {
		
	    if (index < 0 || index >= items.size()) {
	        throw new IndexOutOfBoundsException("Indice non valido: " + index);
	    }
		
			beginRemoveRows(new QModelIndex(), index, index);
			items.remove(index);
			endRemoveRows();
		
	}
	
	/**
	 * changeData(int index) aggiorna i dati di un elemento nel modello.
	 *
	 * @param index L'indice dell'elemento il cui dato deve essere aggiornato.
	 * @throws IndexOutOfBoundsException se l'indice è negativo o maggiore o uguale 
	 *                                    alla dimensione della lista.
	 */
	
	public void changeData(int index) {
		
	    if (index < 0 || index >= items.size()) {
	        throw new IndexOutOfBoundsException("Indice non valido: " + index);
	    }
	    
        QModelIndex itemIndex = createIndex(index, 0); // Crea un QModelIndex per la riga specifica
        dataChanged.emit(itemIndex, itemIndex); // Notifica che solo quell'elemento è cambiato
		
	}

    /**
     * applyDiscount(int index) applica uno sconto sul prodotto.
     * 
     * @param index l'indice dell'elemento a cui applicare lo sconto
     */
	
	public void applyDiscount(int index) {

	    if (!(items.get(index) instanceof Prodotto)) {
	        throw new IllegalArgumentException("L'elemento all'indice " + index + " non è un Prodotto.");
	    }

	    Prodotto product = (Prodotto) items.get(index);
	    try {
	    	
	    	applyDiscountToProduct(product, 10);
	        changeData(index);
	        
	    } catch (NumberFormatException e) {
	        throw new IllegalStateException("Errore nella conversione del prezzo per il prodotto: " + product.getPrezzo(), e);
	    }
	    
	}
	
	/**
	 * applyDiscountToProduct(Prodotto product, int discountPercentage) calcola prezzo scontato
	 * in base alla percentuale fornita.
	 *
	 * @param product l'oggetto Prodotto a cui applicare lo sconto
	 * @param discountPercentage la percentuale di sconto da applicare
	 */
	
	private void applyDiscountToProduct(Prodotto product, int discountPercentage) {
	    
	    double currentPrice = Double.parseDouble(product.getPrezzo().substring(0, product.getPrezzo().length() - 2).replace(",", "."));
	    
	    if (currentPrice <= 0) {
	        throw new IllegalArgumentException("Il prezzo attuale deve essere maggiore di zero.");
	    }
	    
	    // Calcolo il nuovo prezzo in base alla percentuale di sconto
	    double discountFactor = discountPercentage / 100.0; // Convertire la percentuale in frazione
	    double newPrice = currentPrice * (1 - discountFactor); // Applica lo sconto
	    
	    // Imposta il nuovo prezzo
	    product.setPrezzo(String.format("%.2f €", newPrice));
	}
	
}
