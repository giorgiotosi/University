package listview;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
//import java.util.Arrays;
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
import java.lang.reflect.Modifier;

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

	    // Ottieni il nome della proprietà associata al ruolo
	    String propertyName = roleToPropertyMap.get(role);

	    // Controlla se il nome della proprietà è valido
	    if (propertyName != null) {
	        try {
	            // Verifica se la classe ha un metodo getter per la proprietà
	            Method getter = item.getClass().getMethod("get" + capitalize(propertyName));

	            // Rendi accessibile il metodo se è privato
	            getter.setAccessible(true);

	            // Ottieni il valore tramite il metodo getter
	            Object value = getter.invoke(item);
	            return new QVariant(value);
	        } catch (NoSuchMethodException e) {
	            // Ignora, poiché non è una proprietà Qt valida
	        } catch (IllegalAccessException | InvocationTargetException e) {
	            e.printStackTrace();
	        }
	    }
	    return null;
	}

	// Metodo per capitalizzare la prima lettera
	private String capitalize(String str) {
	    if (str == null || str.isEmpty()) {
	        return str;
	    }
	    return Character.toUpperCase(str.charAt(0)) + str.substring(1);
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
	    Class<?> itemClass = items.get(0).getClass(); // Ottieni la classe del primo oggetto

	    // Ottieni tutti i metodi della classe
	    Method[] methods = itemClass.getDeclaredMethods();

	    // Mappa solo i metodi getter pubblici
	    for (Method method : methods) {
	        if (method.getName().startsWith("get") && method.getParameterCount() == 0) {
	            // Verifica che il metodo sia pubblico
	            if (Modifier.isPublic(method.getModifiers())) {
	                // Estrai il nome della proprietà rimuovendo "get" e capitalizzando la prima lettera
	                String propertyName = method.getName().substring(3);
	                propertyName = Character.toLowerCase(propertyName.charAt(0)) + propertyName.substring(1);
	                
	                roleToPropertyMap.put(role++, propertyName); // Associa un ruolo al nome della proprietà
	            }
	        }
	    }

	}

	
	public void removeElementAt(int index) {
		if(items.size()> 1) {
			beginRemoveRows(new QModelIndex(), index, index);
			items.remove(index);
			endRemoveRows();
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

	
	

	public void addElement(T newElement) throws ClassNotFoundException {
		
		int newIndex = items.size();
		// Quando si aggiungono nuove righe al modello si usano beginInsertRows() ed endInsertRows()  
		beginInsertRows(new QModelIndex(), newIndex, newIndex);
		
			items.add(newElement); 
		
		endInsertRows(); 

	}

	
}
