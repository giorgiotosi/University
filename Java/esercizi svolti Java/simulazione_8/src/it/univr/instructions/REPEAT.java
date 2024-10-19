package it.univr.instructions;

import java.util.List;

public class REPEAT implements Instruction {
	
	private final int volte;
	private final Instruction ins;
	
	public REPEAT(int volte, Instruction ins) {
		if(volte<0)
			throw new IllegalArgumentException();
		this.ins = ins;
		this.volte = volte;
	}

	@Override
	public void execute(List<Integer> stack) throws IllegalProgramException {
		for(int i = 0; i< volte; i++) {
			ins.execute(stack);
		}

	}
	
	public String toString() {
		String result = "repeat(" + volte + ", " + ins + ") ";
		
		return result;
	}

}
