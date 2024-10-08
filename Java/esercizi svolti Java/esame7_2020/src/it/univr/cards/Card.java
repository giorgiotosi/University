package it.univr.cards;
import java.util.Random;

public class Card implements Comparable<Card> {

	/**
	 * Il valore della carta.
	 */
	private final Value value;

	/**
	 * Il seme della carta.
	 */
	private final Suit suit;

	/**
	 * Genera una carta a caso.
	 */
	public Card() {
		
		this(RandomValue(), RandomSuit()); 
			
	}

	private static Suit RandomSuit() {
		Random random = new Random();
		//generare un seme casuale
		String suits = "♠♣♦♥";
		int randomSuit = random.nextInt(suits.length());
		Suit suit = Suit.values()[randomSuit];
		
		return suit;
	}

	private static Value RandomValue() {
		Random random = new Random();
		// generare un valore casuale 
		String values = "23456789?JQK1";
		int randomValue = random.nextInt(values.length());
		Value value = Value.values()[randomValue];
		
		return value;
	}

	/**
	 * Costruisce la carta con il valore e il seme indicato.
	 */
	public Card(Value value, Suit suit) {
		this.value = value;
		this.suit = suit;
	}

	/**
	 * Restituisce il valore della carta.
	 * 
	 * @return il valore
	 */
	public Value getValue() {
		return value;
	}

	/**
	 * Restituisce il seme della carta.
	 * 
	 * @return il seme
	 */
	public Suit getSuit() {
		return suit;
	}

	/**
	 * Ritorna una descrizione della carta sotto forma di stringa, del tipo 10♠ oppure J♥.
	 */
	public String toString() {
		char valueAsString = "23456789?JQK1".charAt(value.ordinal());
		char suitAsString = "♠♣♦♥".charAt(suit.ordinal());
		if (value == Value.TEN)
			return "10" + suitAsString;
		else
			return String.valueOf(valueAsString) + suitAsString;
	}

	/**
	 * Determina se questa carta è uguale ad other.
	 * 
	 * @param other l'altra carta con cui confrontarsi
	 * @return true se e solo se le due carte sono uguali (stesso valore e stesso seme)
	 */
	public boolean equals(Object other) {
		return other instanceof Card && value == ((Card)other).value && suit == ((Card)other).suit; 
	}

	/**
	 * Restituisce un hashcode della carta.
	 * 
	 * @return l'hashcode
	 */
	public int hashCode() {
		return value.hashCode() ^ suit.hashCode(); 
	}

	/**
	 * Determina chi vale di piu' fra questa carta e un'altra.
	 * 
	 * @param l'altra carta
	 * @return un numero negativo se vale di piu' l'altra carta, un numero positivo se vale di piu' questa carta,
	 *         zero se le due carte sono equals
	 */
	public int compareTo(Card other) {
		int diff = value.compareTo(other.value);
		if (diff != 0)
			return diff;
		else
			return suit.compareTo(other.suit);
	}
}