package it.univr.rent;
// modello di veicolo che può essere preso a noleggio
// metodi per conoscerne il nome, il prezzo giornaliero di noleggio
// e uno che prende come parametro una patente e ritorna true o false 
// per dire se si può o meno guidare il veicolo con quella patente
/**
 * I modelli sono ordinati per prezzo di noleggio giornaliero.
 * A parita' di prezzo di noleggio, sono ordinati alfabeticamente per nome.
 */
public interface Model extends Comparable<Model> {
	// restituisce il nome del modello
	public String getName();

	// determina se il modello puo' essere guidato
	// con la patente (license) indicata
	public boolean canBeDrivenWith(License license);

	// restituisce il prezzo giornaliero di noleggio del modello
	public int pricePerDay();
}