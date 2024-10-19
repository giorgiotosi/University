package listview;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.qt.NonNull;
import io.qt.core.QAbstractListModel;
import io.qt.core.QByteArray;
import io.qt.core.QHash;
import io.qt.core.QModelIndex;
import io.qt.core.QVariant;
import io.qt.core.Qt;

public class Lista<T> extends QAbstractListModel {

	private final List<T> items; // Lista generica di oggetti T
	private final Map<Integer, String> roleToPropertyMap = new HashMap<>(); // Mappa tra ruolo e nome proprietà

	public  Lista (List<T> items) {
		this.items = items;
		 generateRoleNames(); // Genera dinamicamente i ruoli basati sulle proprietà di T
	}

	@Override
	public int rowCount(@NonNull QModelIndex arg0) {
		return items.size();
	}

	@Override
	public Object data(@NonNull QModelIndex index, int role) {

		if (index.row() < 0 || index.row() >= items.size()) {
			return null;
		}

		T item = items.get(index.row());

		String propertyName = roleToPropertyMap.get(role);
		
        if (propertyName != null) {
            try {
                Field field = item.getClass().getDeclaredField(propertyName);
                field.setAccessible(true); // Rende accessibile il campo, anche se privato
                Object value = field.get(item);
                return new QVariant(value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
		
	}
	
	@Override
    public QHash<Integer, QByteArray> roleNames() {
		Map<Integer, QByteArray> map = new HashMap<Integer, QByteArray>();
        
        // Mappa i nomi delle proprietà ai ruoli Qt
        for (Map.Entry<Integer, String> entry : roleToPropertyMap.entrySet()) {
            map.put(entry.getKey(), new QByteArray(entry.getValue().getBytes()));
        }
        
        QHash<Integer, QByteArray> roles = new QHash<Integer, QByteArray>(map);
        
        return roles;
    }
	
    // Metodo per generare i ruoli dinamicamente
    private void generateRoleNames() {
        int role = Qt.ItemDataRole.UserRole; // Inizia a mappare i ruoli da UserRole in poi
        Field[] fields = items.get(0).getClass().getDeclaredFields(); // Ottieni i campi della classe del primo oggetto

        // Mappa ogni campo a un ruolo
        for (Field field : fields) {
            roleToPropertyMap.put(role++, field.getName()); // Associa un ruolo al nome della proprietà
        }
    }

	public void applyDiscount(int index) {
		if (index >= 0 && index < items.size()) {
			Prodotto product = (Prodotto) items.get(index);
			double newPrice = Double.parseDouble(product.getPrezzo().substring(0, product.getPrezzo().length() - 2).replace(",", ".")) * 0.9; // Applica il 10% di sconto
			product.setPrezzo(String.valueOf(String.format("%.2f €", newPrice)));
			//Per aggiornare dati esistenti si usa dataChanged()
			QModelIndex itemIndex = createIndex(index, 0); // Crea un QModelIndex per la riga specifica
	        dataChanged.emit(itemIndex, itemIndex); // Notifica che solo quell'elemento è cambiato
		}
	}
	
	@SuppressWarnings("unchecked")
	public void addProduct(String newName, String newPrice, String newDescription) {
	    Prodotto newProduct = new Prodotto(newName, newPrice, newDescription);
	    int newIndex = items.size();
	    //Quando si aggiungono nuove righe al modello si usano beginInsertRows() ed endInsertRows()  
	    beginInsertRows(new QModelIndex(), newIndex, newIndex); 
	    items.add((T) newProduct); 
	    endInsertRows(); 
	}



}
