package it.univr.bank;


//Realizzate una sottoclasse BankWithMinimum.java di SimpleBank.java che si comporta in
//maniera identica a SimpleBank.java ma che in piu' genera una IllegalArgumentException se si
//cerca di creare un conto corrente con meno di 100 euro o di lasciare meno di 100 euro
//su un conto corrente. SUGGERIMENTO: dovete ridefinire i metodi deposit, withdraw e transfer.


public class BankWithMinimum extends SimpleBank  {
	
	
	@Override
	public void deposit(String to, double amount) {
		
		if(to == null || amount <0) {
			throw new IllegalArgumentException();
		}
		
		if(bank.getOrDefault(to, 0.00) == 0.00 && amount<100) {
			throw new IllegalArgumentException();
		}
		
		bank.put(to, bank.getOrDefault(to, 0.00) + amount);

	}
	
	
	
	@Override
	public void withdraw(String from, double amount) throws BankException {
		
		
		if(from == null || amount <0) {
			throw new IllegalArgumentException();
		}
		
		if(!bank.containsKey(from) || bank.get(from)< amount) {
			throw new BankException("l'account non esiste in questa banca");
		}
		
		if(bank.get(from) - amount < 100) {
			throw new IllegalArgumentException();
		}
		
		bank.put(from, bank.get(from) - amount);
		

		



	}
	
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
