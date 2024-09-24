package it.univr.instructions;

import java.util.List;

public class REPEAT implements Instruction {
	
	private int c;
	private Instruction ins;
	
	public REPEAT(int c, Instruction ins) {
		if(c <0) {
			throw new IllegalArgumentException();
		}
		this.c = c;
		this.ins = ins;
	}

	@Override
	public void execute(List<Integer> stack) throws IllegalProgramException {
		// TODO Auto-generated method stub
		
		
		for(int i = 0; i< c; i++) {
			
			ins.execute(stack);
			
				
		}

	}
	
	public String toString() {
		return "repeat"+"(" + c + ", " + ins.toString() + ")" ;
	}

}
