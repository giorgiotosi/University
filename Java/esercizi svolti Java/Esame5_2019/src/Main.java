import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import it.univr.words.Words;

public class Main {
	public static void main(String[] args) throws IOException {
		Scanner keyboard = new Scanner(System.in);
		String filename;
		
		System.out.print("Inserisci il nome del file di testo da processare: ");
		filename = keyboard.nextLine();
		
		try {
			Words words = new Words(filename);
			System.out.println(words);
			try {
				System.out.println(String.format("The most frequent word is \"%s\"", words.mostFrequent()));
			}catch(NoSuchElementException e) {
				System.out.print("I have selected zero words");
			}
		
		}catch(FileNotFoundException e) {
				System.out.println("There was a problem accessing " + filename);		
		}
		
		try {
			Words words2 = new Words(filename, t -> t.startsWith("J"));
			
			System.out.println(words2);
			try {
				System.out.println(String.format("The most frequent word is \"%s\"", words2.mostFrequent()));
			}catch(NoSuchElementException e) {
				System.out.print("I have selected zero words");
			}
			
		}catch(FileNotFoundException e) {
			System.out.println("There was a problem accessing " + filename);		
		}
		
		try {
			Words words3 = new Words(filename, t -> t.length() > 4);
			
			System.out.println(words3);
			try {
				System.out.println(String.format("The most frequent word is \"%s\"", words3.mostFrequent()));
			}catch(NoSuchElementException e) {
				System.out.print("I have selected zero words");
			}
		
		}catch(FileNotFoundException e) {
			System.out.println("There was a problem accessing " + filename);		
		}
		
		keyboard.close();
	}
}