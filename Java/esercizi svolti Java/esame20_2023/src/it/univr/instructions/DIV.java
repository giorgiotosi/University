package it.univr.instructions;

import java.util.List;

public class DIV implements Instruction {
	
	public DIV() {
		
	}

	@Override
	public void execute(List<Integer> stack) throws IllegalProgramException {
		// TODO Auto-generated method stub
		if (stack.size() < 2)
			// lo stack deve avere almeno due operandi
			throw new IllegalProgramException("Operandi insufficienti per un’operazione binaria");
		int i1 = stack.remove(stack.size() - 1);
		int i2 = stack.remove(stack.size() - 1);
		if(i2 == 0) {
			throw new IllegalProgramException("non si può dividere per zero");
		}
		int divisione = i1/i2;
		stack.add(divisione);
	}
	
	@Override
	public String toString() {
		return "div";
	}

}
