package it.univr.cards;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Una mano di cinque carte.
 */
public class Deck {

	//servono campi?
	private final SortedSet<Card> deck = new TreeSet<>();
	private  Card[] carte = new Card[5];
	private  Card c1;
	private  Card c2;
	private  Card c3;
	private  Card c4;
	private  Card c5;

	/**
	 * Costruisce una mano di cinque carte, tutte diverse.
	 */
	public Deck() {
		
		this(new Card(),new Card(),new Card(),new Card(),new Card());
	}

	/**
	 * Costruisce una mano con le cinque carte indicate. Attenzione
	 * all'eccezione che dovete lanciare se ci fossero carte ripetute!
	 * 
	 * @throws IllegalArgumentException se ci fossero carte ripetute (cioe' equals)
	 */
	public Deck(Card c1, Card c2, Card c3, Card c4, Card c5) throws IllegalArgumentException {
		Card card = new Card();
		int maxTentativi = 10;
		int count = 0 ;
		
		if(deck.add(c1)== false) {
			do {
			card = new Card();
			
			count++;
			} while(deck.add(card) == false && count<=maxTentativi);
			this.c1= deck.last();
		}
		else {this.c1 = c1;}
		if(deck.add(c2)== false) {
			count = 0 ;
			do {
			card = new Card();
			
			count++;
			} while(deck.add(card) == false && count<=maxTentativi);
			this.c2= deck.last();
		}
		else {this.c2 = c2;}
		if(deck.add(c3)== false) {
			count = 0 ;
			do {
			card = new Card();
			
			count++;
			} while(deck.add(card) == false && count<=maxTentativi);
			this.c3= deck.last();
		}
		else {this.c3 = c3;}
		if(deck.add(c4)== false) {
			count = 0;
			do {
			card = new Card();
			
			count++;
			} while(deck.add(card) == false && count<=maxTentativi);
			this.c4= deck.last();
		}
		else {this.c4 = c4;}
		if(deck.add(c5)== false) {
			count =0;
			do {
			card = new Card();
			
			count++;
			} while(deck.add(card) == false && count<=maxTentativi);
			this.c5= deck.last();
		}
		else {this.c5 = c5;}
		if(deck.size()<5) {
			throw new IllegalArgumentException();
		}
		int i=0;
		for(Card c: deck) {
			carte[i] = c;
			i++;
		}
		this.c1 = carte[0];
		this.c2 = carte[1];
		this.c3 = carte[2];
		this.c4 = carte[3];
		this.c5 = carte[4];
	}
		
	

	/**
	 * Restituisce una stringa che descrive le carte in questa mano,
	 * costruita come le loro stampe, separate da virgola,
	 * con parentesi quadre all'inizio e alla fine.
	 * Per esempio: [2♠, 5♠, 8♠, 10♠, Q♠]
	 * 
	 * @return la stringa
	 */
	public String toString() {
		
		return deck.toString(); 
	}

	/**
	 * Determina se il ranking di questa mano e' "straight" (in italiano: "scala").
	 * Cioe' devono essere in sequenza ma non tutte dello stesso seme.
	 */
	public boolean isStraight() {
		Value currentValue = null;
		Suit currentSuit = null;
		boolean suitDifferent = false;
		for(Card c : deck) {
			if(currentValue == null)
				currentValue = c.getValue();
			else {
				if(currentValue.ordinal() +1 == c.getValue().ordinal()) {
					currentValue = c.getValue();
				}
				else {
					return false;
				}
			}
			if(suitDifferent == false) {
				if(currentSuit == null)
					currentSuit = c.getSuit();
				else {
					if(currentSuit != c.getSuit()) {
						suitDifferent = true;
					}
					else {
						currentSuit = c.getSuit();
					}
				}
			}


		}
		return true;
	}

	/**
	 * Determina se il ranking di questa mano e' "flush" (in italiano: "colore").
	 * Cioe' devono essere tutte dello stesso seme ma non in sequenza.
	 */
	public boolean isFlush() {
		boolean SuitEqual = true;
		Suit currentSuit = null;
		
		for(Card c : deck) {
			
			if(currentSuit == null)
				currentSuit = c.getSuit();
			else {
				if(currentSuit != c.getSuit()) {
						return false;
					}
					else {
						currentSuit = c.getSuit();
					}
				}
			
		}
		
		
		return !isStraight() && SuitEqual; 
	}

	/**
	 * Determina se il ranking di questa mano e' "straight flush" (in italiano: "scala reale").
	 * Cioe' devono essere tutte dello stesso seme e in sequenza.
	 */
	public boolean isStraightFlush() {
		Value currentValue = null;
		Suit currentSuit = null;
		for(Card c : deck) {
			if(currentValue == null)
				currentValue = c.getValue();
			else {
				if(currentValue.ordinal() +1  == c.getValue().ordinal()) {
					currentValue = c.getValue();
				}
				else {
					return false;
				}
			}
			
			if(currentSuit == null)
				currentSuit = c.getSuit();
			else {
				if(currentSuit != c.getSuit()) {
						return false;
					}
					else {
						currentSuit = c.getSuit();
					}
				}
			


		}
		return true;
	}

	/**
	 * Determina se il ranking di questa mano e' "four of a kind" (in italiano: "poker").
	 * Cioe' quattro dello stesso valore e una di valore diverso.
	 */
	public boolean isFourOfKind() {
		// sono ordinate le carte percio'
		if(c1.getValue() == c2.getValue() && c2.getValue()  == c3.getValue() && c3.getValue() == c4.getValue() || 
				c2.getValue()  == c3.getValue() && c3.getValue() == c4.getValue() && c4.getValue() == c5.getValue())
		return true;
		
		return false;
	}

	/**
	 * Determina se il ranking di questa mano e' "full house" (in italiano: "full").
	 * Cioe' tre dello stesso valore e le altre due dello stesso valore.
	 */
	public boolean isFullHouse() {
		if(c1.getValue() == c2.getValue() && c3.getValue() == c4.getValue() && c4.getValue() ==c5.getValue() && c1.getValue() != c5.getValue() ||
				c1.getValue() == c2.getValue() && c2.getValue() == c3.getValue() && c4.getValue() ==c5.getValue() && c1.getValue() != c5.getValue()){
			return true;
		}
		return false;
	}

	/**
	 * Determina se il ranking di questa mano e' "three of kind" (in italiano: "tris").
	 * Cioe' non deve essere four of kind, non deve essere full house e ci devono essere
	 * tre carte con lo stesso valore.
	 */
	public boolean isThreeOfKind() {
		if((c1.getValue() == c2.getValue() && c2.getValue() == c3.getValue()  && c3.getValue() !=c4.getValue() && c4.getValue() != c5.getValue() )||
				(c2.getValue() == c3.getValue() && c3.getValue() == c4.getValue() && c1.getValue() !=c2.getValue() && c4.getValue() != c5.getValue() && c1.getValue() != c5.getValue())||
				(c3.getValue() == c4.getValue() && c4.getValue() == c5.getValue() && c2.getValue() !=c3.getValue() && c1.getValue() != c2.getValue())){
			return true;
		}
		return false;
	}

	/**
	 * Restituisce il ranking di questa mano. Per esempio,
	 * se questa mano isStraight() allora restituisce Ranking.STRAIGHT;
	 */
	public Ranking getRanking() {
		
		if(isStraightFlush()) {
			return Ranking.STRAIGHT_FLUSH;
		}
		else if(isFourOfKind()) {
			return Ranking.FOUR_OF_KIND;
		}
		else if(isFullHouse()) {
			return Ranking.FULL_HOUSE;
		}
		else if(isFlush()) {
			return Ranking.FLUSH;
		}
		else if(isStraight()) {
			return Ranking.STRAIGHT;
		}
		else if(isThreeOfKind()) {
			return Ranking.THREE_OF_KIND;
		}
		else
		return Ranking.NONE;
	}

	/**
	 * Determina se il ranking di questa mano e' il minimo indicato
	 * o e' superiore al minimo indicato.
	 * 
	 * @param minimum il minimo ranking
	 */
	public boolean hasRankingFrom(Ranking minimum) {
		boolean result = false;
		if(getRanking().compareTo(minimum)>=0) {
			result = true;
		}
		return result;
	}
}