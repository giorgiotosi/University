package it.univr.instructions;

import java.util.List;

public class PrintingMachine extends SimpleMachine {

	public PrintingMachine(List<Instruction> program) throws IllegalProgramException {
		super(program);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void execute(Instruction ins, List<Integer> stack) throws IllegalProgramException {
		try {
		ins.execute(stack);
		} catch(IllegalProgramException e) {
			System.out.print(ins.toString() + ": " );
			throw new IllegalProgramException( e.getMessage());
			
		
		}
		System.out.print(ins.toString() + ": ");
		System.out.println(stack.toString());
	}

}
