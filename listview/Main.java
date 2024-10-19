package listview;
import java.util.ArrayList;
import java.util.List;
import io.qt.core.QObject;
import io.qt.core.QUrl;
import io.qt.qml.QQmlApplicationEngine;
import io.qt.widgets.QApplication;

public class Main extends QApplication{
	
	private QQmlApplicationEngine engine; 

	private QObject rootObject;

	public static void main(String[] args) {
		QApplication.initialize(args, Main::new); 
		QApplication.exec(); 
		QApplication.shutdown(); 
	}
	
	private Main(String[] args) {
		super(args);
		initializeUI();
	}
	
	private void initializeUI() {

		engine = new QQmlApplicationEngine();
		
        // Creo una lista come esempio da passare al modello
        List<Prodotto> prodotti = new ArrayList<>();
        prodotti.add(new Prodotto("Dell XPS 13","1299,90 €" ,"Ultrabook con processore Intel Core i7, 16 GB di RAM e 512 GB di SSD"));
        prodotti.add(new Prodotto("Apple iPad Air","749,00 €" ,"Tablet con display da 10,9 pollici, chip A14 Bionic, 64 GB di spazio"));
        prodotti.add(new Prodotto("Samsung Galaxy S21","699,99 €" ,"Smartphone da 6,2 pollici, processore Exynos 2100 e fotocamera tripla"));

        // Crea un'istanza del modello con la lista di prodotti
        Lista<Prodotto> model = new Lista<>(prodotti);
        
        engine.rootContext().setContextProperty("listaModel", model);

		engine.load(QUrl.fromLocalFile("C:\\Users\\asusl\\eclipse-workspace\\Liste\\src\\listview.qml"));

		rootObject = engine.rootObjects().isEmpty() ? null : engine.rootObjects().get(0);
		if (rootObject == null) {
			System.err.println("Errore: l'oggetto radice non è stato creato.");
			return;
		}
		
	}
	
}
