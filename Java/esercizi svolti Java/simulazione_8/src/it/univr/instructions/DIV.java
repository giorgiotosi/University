package it.univr.instructions;

import java.util.List;

public class DIV implements Instruction {

	@Override
	public void execute(List<Integer> stack) throws IllegalProgramException {
		int size = stack.size();
		if(size <2)
			throw new IllegalProgramException("Operandi insufficienti per un’operazione binaria");
		int i1 = stack.remove(size - 1);
		int i2 = stack.remove(size -2);
		if(i2 == 0)
			throw new IllegalProgramException("non si può dividere per 0");
		int divisione = i1/i2;
		stack.add(divisione);
	}
	
	public String toString() {
		String result = "div";
		
		return result;
	}

}
