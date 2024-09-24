package it.univr.instructions;

import java.util.List;

public class PUSH implements Instruction {
	
	private int val;
	
	public PUSH(int val) {
		this.val = val;
	}

	@Override
	public void execute(List<Integer> stack) throws IllegalProgramException {
		stack.add(val);

	}
	
	@Override
	public String toString() {
		return "push" + "(" + val + ")";
	}

}
