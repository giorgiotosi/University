package it.univr.bank;

import java.util.HashMap;
import java.util.Map;

public class SimpleBank implements Bank {
	
	Map<String, Double> bank = new HashMap<>();

	/**
	 * Ritorna la quantita' di soldi depositati in un conto corrente.
	 * 
	 * @param account una stringa che identifica il conto corrente
	 * @return la quantita' di soldi
	 * @throws IllegalArgumentException se account e' null
	 * @throws BankException se account non esiste in questa banca 
	 */
	@Override
	public double balanceOf(String account) throws BankException {
		
		if(account == null) {
			throw new IllegalArgumentException();
		}
		if(!bank.containsKey(account)) {
			throw new BankException("l'account non esiste in questa banca");
		}
		return bank.get(account);
	}
	
	/**
	 * Aggiunge una quantita' di soldi a un conto corrente.
	 * 
	 * @param to il conto corrente; se non esisteva in questa banca, viene creato
	 * @param amount la quantita' di soldi da aggiungere
	 * @throws IllegalArgumentException se to e' null oppure amount e' negativo*/

	@Override
	public void deposit(String to, double amount) {
		
		if(to == null || amount <0) {
			throw new IllegalArgumentException();
		}
		
		bank.put(to, (bank.getOrDefault(to, 0.00) + amount));

	}
	
	/**
	 * Preleva una quantita' di soldi da un conto corrente.
	 * 
	 * @param from il conto corrente
	 * @param amount la quantita' di soldi da prelevare
	 * @throws IllegalArgumentException se from e' null oppure amount e' negativo
	 * @throws BankException se from non esiste in questa banca oppure
	 *         ha depositati meno di amount soldi
	 */

	@Override
	public void withdraw(String from, double amount) throws BankException {
		
		if(from == null || amount <0) {
			throw new IllegalArgumentException();
		}
		
		if(!bank.containsKey(from) || bank.get(from)< amount) {
			throw new BankException("l'account non esiste in questa banca");
		}
		
		bank.put(from, bank.get(from) - amount);


	}
	
	/**
	 * Sposta una quantita' di soldi da un conto corrente a un altro.
	 * 
	 * @param from il conto corrente di partenza
	 * @param to il conto corrente di destinazione; se non esisteva in questa banca, viene creato
	 * @param amount la quantita' di soldi da spostare
	 * @throws IllegalArgumentException se from e' null oppure to e' null oppure amount e' negativo
	 * @throws BankException se from non esiste in questa banca oppure
	 *         ha depositati meno di amount soldi
	 */

	@Override
	public void transfer(String from, String to, double amount) throws BankException {
		
		if(from == null || amount <0) {
			throw new IllegalArgumentException();
		}
		
		if(!bank.containsKey(from) || bank.get(from)< amount) {
			throw new BankException("l'account non esiste in questa banca");
		}
		
		this.withdraw(from, amount);
		this.deposit(to, amount);
		

	}

}
