package it.univr.instructions;

import java.util.List;

public class PUSH implements Instruction {
	
	private final int value;
	
	public PUSH(int value) {
		this.value = value;
	}

	@Override
	public void execute(List<Integer> stack) throws IllegalProgramException {
		
		stack.add(value);

	}
	
	public String toString() {
		String result = "push(" + value + ")";
		
		return result;
	}

}
