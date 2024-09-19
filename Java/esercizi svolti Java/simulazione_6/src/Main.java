import java.io.IOException;
import java.util.Scanner;

import it.univr.words.Words;

public class Main {
	public static void main(String[] args) {
		/*1. chieda all’utente di inserire da tastiera il nome del file di testo da processare;
		2. crei un oggetto Words a partire da tale file, stampi a video tale oggetto e quindi stampi
		a video la sua parola più frequente;
		3. crei un oggetto Words a partire da tale file, selezionando solo le parole che cominciano
		con il carattere J (maiuscolo), stampi a video tale oggetto e quindi stampi a video la sua
		parola più frequente;
		4. crei un oggetto Words a partire da tale file, selezionando solo le parole che siano più
		lunghe di quattro caratteri, stampi a video tale oggetto e quindi stampi a video la sua
		parola più frequente;
		5. se la creazione di uno di questi tre Words fallisse con un’eccezione perché non si riesce ad
		accedere al file, il Main dovrà stampare There was a problem accessing FILENAME e
		terminare;*/
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.print("Inserisci il nome del file di testo da processare: ");
		String fileName = "src/" + keyboard.nextLine();
		
		try {
			Words file = new Words(fileName);
			System.out.println(file);
			System.out.println("La parola più frequente è: " + file.mostFrequent());
		} catch (IOException e) {
			System.out.println("IOException");
		}
		
		try {
			Words file2 = new Words(fileName, s -> s.startsWith("J"));
			System.out.println(file2);
			System.out.println("La parola più frequente è: " + file2.mostFrequent());
		} catch (IOException e) {
			System.out.println("IOException");
		}
		
		try {
			Words file3 = new Words(fileName, s -> s.length()>4);
			System.out.println(file3);
			System.out.println("La parola più frequente è: " + file3.mostFrequent());
		} catch (IOException e) {
			System.out.println("IOException");
		}
		
		keyboard.close();
		
	}
}