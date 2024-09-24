package it.univr.instructions;

import java.util.List;

public class ADD implements Instruction {
	
	public ADD() {
	}

	@Override
	public void execute(List<Integer> stack) throws IllegalProgramException {
		// TODO Auto-generated method stub
		if (stack.size() < 2)
			// lo stack deve avere almeno due operandi
			throw new IllegalProgramException("Operandi insufficienti per un’operazione binaria");
		int i1 = stack.remove(stack.size() - 1);
		int i2 = stack.remove(stack.size() - 1);
		int somma = i1 + i2;
		stack.add(somma);
	}
	
	@Override
	public String toString() {
		return "add";
	}

}
