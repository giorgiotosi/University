package it.univr.instructions;

import java.util.List;

public class MUL implements Instruction {
	
	public MUL() {
		
	}

	@Override
	public void execute(List<Integer> stack) throws IllegalProgramException {
		// TODO Auto-generated method stub
		if (stack.size() < 2)
			// lo stack deve avere almeno due operandi
			throw new IllegalProgramException("Operandi insufficienti per un’operazione binaria");
		int i1 = stack.remove(stack.size() - 1);
		int i2 = stack.remove(stack.size() - 1);
		int moltiplicazione = i1 * i2;
		stack.add(moltiplicazione);
	}
	
	@Override
	public String toString() {
		return "mul";
	}

}
