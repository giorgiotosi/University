package it.univr.instructions;

import java.util.List;

public class SUB implements Instruction {
	
	public SUB() {
		
	}

	@Override
	public void execute(List<Integer> stack) throws IllegalProgramException {
		// TODO Auto-generated method stub
		
		if (stack.size() < 2)
			// lo stack deve avere almeno due operandi
			throw new IllegalProgramException("Operandi insufficienti per un’operazione binaria");
		int i1 = stack.remove(stack.size() - 1);
		int i2 = stack.remove(stack.size() - 1);
		int sottrazione = i1 - i2;
		stack.add(sottrazione);
	}
	
	@Override
	public String toString() {
		return "sub";
	}

}
