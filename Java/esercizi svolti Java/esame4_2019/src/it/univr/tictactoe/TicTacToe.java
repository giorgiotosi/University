package it.univr.tictactoe;


/**
 * Un gioco del tris. Due giocatori piazzano, alternativamente,
 * una croce e un cerchio su una tavola 3x3, inizialmente vuota.
 */
public abstract class TicTacToe {

	/**
	 * Un possibile contenuto per una casella del tris: vuota, croce o cerchio.
	 */
	public static enum Tile {
		EMPTY(" "), CROSS("X"), CIRCLE("O");

		private final String name;

		private Tile(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

		/**
		 * Calcola chi gioca dopo questo sinbolo: dopo croce
		 * gioca cerchio e dopo cerchio gioca croce.
		 */
		private Tile nextTurn() {
			return this == CROSS ? CIRCLE : CROSS;
		}
	}

	/**
	 * La tavola di 9 caselle.
	 */
	private final Tile[] board = { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY };

	/**
	 * Il primo e il secondo giocatore, che si alternano al gioco.
	 */
	private Player crossPlayer, circlePlayer;

	/**
	 * Chi fa la prossima mossa: croce o cerchio. Si inizia con la croce.
	 */
	private Tile turn = Tile.CROSS;

	/**
	 * Restituisce il contenuto della casella indicata (vuota/croce/cerchio).
	 * 
	 * @param x l'ascissa della casella, fra 1 e 3 inclusi
	 * @param y l'ordinata della casella, fra 1 e 3 inclusi
	 * @return il valore della casella (x,y)
	 * @throws java.lang.IllegalArgumentException se le coordinate non sono fra 1 e 3
	 */
	public Tile at(int x, int y) {
		if (1 <= x && x <= 3 && 1 <= y && y <= 3)
			return board[(y - 1) * 3 + x - 1];
		else {
			//System.out.print("le coordinate sono x = "); System.out.print(x);
			//System.out.print(" e y = "); System.out.print(y+"\n");
			throw new IllegalArgumentException("coordinates must be between 1 and 3");
		}
	}
	

	/**
	 * Scrive un contenuto nella casella indicata.
	 * 
	 * @param x l'ascissa della casella, fra 1 e 3
	 * @param y l'ordinata della casella, fra 1 e 3
	 * @param tile il contenuto da scrivere nella casella (vuota/croce/cerchio)
	 */
	private void set(int x, int y, Tile tile) {
		System.out.print("le coordinate sono x = "); System.out.print(x);
		System.out.print(" e y = "); System.out.print(y+"\n");
		board[(y-1)*3+x-1] = tile;
	}

	/**
	 * Effettua la mossa del giocatore indicato, che scrive il suo simbolo
	 * (croce o cerchio) nella casella indicata. Chi fa la prima mossa
	 * viene memorizzato in crossPlayer. Chi fa la seconda mossa viene memorizzato
	 * in circlePlayer. Tali giocatori devono essere oggetti Player distinti
	 * (non si puo' giocare contro se stessi).
	 * Dalla terza mossa in poi, si devono alternare crossPlayer e circlePlayer,
	 * finche' il gioco non sia finito.
	 * 
	 * @param player il giocatore che fa la mossa
	 * @param x l'ascissa della casella, fra 1 e 3
	 * @param y l'ordinata della casella, fra 1 e 3
	 * @throws java.lang.IllegalStateException se il gioco è già finito, oppure se non tocca
	 *         a player giocare, oppure se chi fa la prima mossa cerca di fare anche la seconda
	 * @throws java.lang.IllegalArgumentException se le coordinate non sono fra 1 e 3,
	 *         oppure se la casella (x,y) e' gia' occupata
	 */
	public void play(Player player, int x, int y) {
		
		if(isGameOver()) 
			throw new IllegalStateException();
		
		
		if(!(x >=1 && x<=3 && y>=1 && y<=3)) 
			throw new IllegalArgumentException();
		
		
		if(at(x, y) != Tile.EMPTY)
			throw new IllegalArgumentException();
		
		if(turn == Tile.CROSS) {
			if(crossPlayer == null)
				crossPlayer = player;
			else if(player != crossPlayer)
				throw new IllegalStateException();
		}
		
		else //turn == Tile.CIRCLE
			if (circlePlayer == null) {
				if(crossPlayer == player)
					throw new  IllegalStateException();
				
				circlePlayer = player;
			}
			else if(player != circlePlayer)
				throw new IllegalStateException();
		
		//System.out.print("le coordinate sono x = "); System.out.print(x);
		//System.out.print(" e y = "); System.out.print(y+"\n");
		set(x,y, turn);
		turn = turn.nextTurn();
		
	}

	/**
	 * Determina se il gioco e' gia' finito. L'implementazione di questo metodo
	 * dipende dalle sottoclassi di questa classe. Ogni sottoclasse puo' implementare
	 * regole diverse per dire quando il gioco e' finito.
	 * 
	 * @return true se e solo se il gioco va considerato finito
	 */
	public abstract boolean isGameOver();

	/**
	 * Restituisce una stringa che descrive di questo gioco: una tabella 3x3
	 * le cue caselle hanno i contenuti separati da barrette.
	 * 
	 * @return la descrizione
	 */
	@Override
	public String toString() {
		String result="";
		
		result += at(1,1) + "|" + at(2,1)  + "|" + at(3,1)  + "\n";
		result += "-----\n";
		result += at(1,2)  + "|" + at(2,2)  + "|" + at(3,2) + "\n";
		result += "-----\n";
		result += at(1,3)  + "|" + at(2,3)  + "|" + at(3,3)  + "\n";
		
		return result;
	}
}
