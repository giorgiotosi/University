package it.univr.tictactoe;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		Player alessandra = new Player("Alessandra");
		Player giovanni = new Player("Giovanni");
		
		TicTacToe gioco = new FullTicTacToe();
		

		Player currentPlayer = alessandra;
		do{
		int x;
		int y;
		
		System.out.println("Inserisci la coordinata x: ");
		x= keyboard.nextInt();
		System.out.println("Inserisci la coordinata y: ");
		y= keyboard.nextInt();
		
		gioco.play(currentPlayer, x, y);
		
		System.out.println(gioco.toString());
		
		currentPlayer = currentPlayer == alessandra? giovanni : alessandra;

		}while(!gioco.isGameOver());
		
		System.out.println("Il gioco è finito");
		
		keyboard.close();
	}
}