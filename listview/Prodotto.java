package listview;
public class Prodotto {
    private String nome;
    private String prezzo;
    private String descrizione;

    public Prodotto(String nome,String prezzo, String descrizione) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }
    
    public String getPrezzo() {
        return prezzo;
    }

    public String getDescrizione() {
        return descrizione;
    }
    
    @Override
    public String toString() {
        return nome;
    }

	public void setPrezzo(String newPrezzo) {
		this.prezzo = newPrezzo;		
	}
}