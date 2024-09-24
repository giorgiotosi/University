package it.univr.instructions;

import java.util.List;

public class SUB implements Instruction{

	@Override
	public void execute(List<Integer> stack) throws IllegalProgramException {
		int size = stack.size();
		if(size <2)
			throw new IllegalProgramException("Operandi insufficienti per un’operazione binaria");
		int i1 = stack.remove(size - 1);
		int i2 = stack.remove(size -2);
		int sottrazione = i1 - i2;
		stack.add(sottrazione);
	}
	
	public String toString() {
		String result = "sub";
		
		return result;
	}

}
