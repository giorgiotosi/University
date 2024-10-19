package it.univr.instructions;

public class IllegalProgramException extends RuntimeException {
	IllegalProgramException(String message){
		super(message);
	}
}
