package listview;

import io.qt.QtInvokable;
import io.qt.QtPropertyNotify;
import io.qt.QtPropertyReader;
import io.qt.QtPropertyWriter;
import io.qt.core.QObject;


public class Prodotto extends QObject {
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
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizione() {
        return descrizione;
    }
    
    @Override
    public String toString() {
        return nome;
    }
    
    @QtInvokable
    @QtPropertyWriter
	public void setPrezzo(String prezzo) {
		this.prezzo = prezzo;
		prezzoChanged.emit(prezzo);
	}
	
}