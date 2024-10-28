package listview;

import java.util.UUID;
import io.qt.QtInvokable;
import io.qt.QtPropertyNotify;
import io.qt.QtPropertyReader;
import io.qt.QtPropertyWriter;
import io.qt.core.QObject;

/**
 * Rappresenta un prodotto nel sistema.
 */

public class Prodotto extends QObject {
	@SuppressWarnings("unused")
	private String id;
    public String nome;
    public String prezzo;
    private String descrizione;

	@SuppressWarnings("unused")
	private Prodotto(io.qt.core.QObject.QDeclarativeConstructor dc) throws IllegalAccessException {
        super(dc);
    }
    
    @QtPropertyNotify
	public Signal1<String> nomeChanged = new Signal1<>();
    
    @QtPropertyNotify
	public Signal1<String> prezzoChanged = new Signal1<>();

    @QtInvokable
    public Prodotto(String nome,String prezzo, String descrizione) {
    	super();
    	this.id = UUID.randomUUID().toString(); 
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
    }
    
    @QtPropertyReader
	public String getNome() {
        return nome;
    }
    
    @QtInvokable
    @QtPropertyWriter
    public void setNome(String nome) {
        this.nome = nome;
        nomeChanged.emit(nome);
    }
    
    @QtPropertyReader
    public String getPrezzo() {
        return prezzo;
    }
    
    @QtInvokable
    @QtPropertyWriter
	public void setPrezzo(String prezzo) {
		this.prezzo = prezzo;
		prezzoChanged.emit(prezzo);
	}
    
    public String getDescrizione() {
        return descrizione;
    }
    
    @QtInvokable
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
    @Override
    public String toString() {
        return nome;
    }
    	
}