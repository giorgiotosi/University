package listview;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.qt.core.QModelIndex;

class ListaTest {
	
	private List<Prodotto> prodotti;
	private Lista<Prodotto> lista; // una lista di prodotti

	@BeforeEach
	void setUp() throws Exception {
		prodotti = new ArrayList<>();
		prodotti.add(new Prodotto("Dell XPS 13","1299,90 €" ,"Ultrabook con processore Intel Core i7, 16 GB di RAM e 512 GB di SSD"));
        prodotti.add(new Prodotto("Apple iPad Air","749,00 €" ,"Tablet con display da 10,9 pollici, chip A14 Bionic, 64 GB di spazio"));
		lista = new Lista<>(prodotti);
	}

	 @Test
	    void testAddElement() {
	        // Arrange
	        Prodotto nuovoProdotto = new Prodotto("Samsung Galaxy S21", "899,00 €", "Smartphone con fotocamera da 64 MP");

	        // Act
	        lista.addElement(nuovoProdotto);

	        // Assert
	        assertEquals(3, lista.items.size()); // La dimensione dovrebbe essere 3
	        assertEquals(nuovoProdotto, lista.items.get(2)); // Verifica che il nuovo prodotto sia stato aggiunto
	    }

	    @Test
	    void testRemoveElement() {
	        // Arrange
	        Prodotto prodottoDaRimuovere = prodotti.get(0); // Prendi il primo prodotto

	        // Act
	        lista.removeElementAt(0);

	        // Assert
	        assertEquals(1, lista.items.size()); // La dimensione dovrebbe essere 1 dopo la rimozione
	        assertFalse(lista.items.contains(prodottoDaRimuovere)); // Verifica che il prodotto sia stato rimosso
	    }
	    
	    @Test
	    void testChangeDataValoreModificato() {
	        // Arrange
	        Prodotto prodottoIniziale = lista.items.get(0); // Recupera l'elemento prima della modifica
	        String prezzoIniziale = prodottoIniziale.getPrezzo();
	        
	        // Act
	        prodottoIniziale.setPrezzo("999,90 €");
	        lista.changeData(0); // Modifica il prezzo tramite changeData
	        
	        // Recupera il nuovo prezzo dalla vista
	        QModelIndex index = lista.index(0, 0); // Indice dell'elemento nella lista
	        int prezzoRole = lista.roleToPropertyMap.entrySet().stream()
	                  .filter(entry -> "prezzo".equals(entry.getValue()))
	                  .map(Map.Entry::getKey)
	                  .findFirst()
	                  .orElseThrow(() -> new IllegalArgumentException("Ruolo per 'prezzo' non trovato"));

	String prezzoModificatoDallaVista = lista.data(index, prezzoRole).toString();

	        
	        // Assert
	        assertNotEquals(prezzoIniziale, prezzoModificatoDallaVista, "Il prezzo dovrebbe essere stato modificato.");
	        assertEquals("999,90 €", prezzoModificatoDallaVista, "Il prezzo aggiornato non corrisponde al valore atteso.");
	    }


	    @Test
	    void testGetElement() {
	        // Act
	        Prodotto prodottoRecuperato = lista.items.get(0); // Recupera il primo prodotto

	        // Assert
	        assertEquals(prodotti.get(0), prodottoRecuperato); // Verifica che il prodotto recuperato sia corretto
	    }

}
