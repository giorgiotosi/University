package it.univr.quindici;

public class FattoriaDiTessereNumeriche extends FattoriaDiTessere<Integer> {
	
	private final int max;
	
	@Override
	public Tessera<Integer> get() {
		return new Tessera<>(random.nextInt(max)+1);
	}
	
	FattoriaDiTessereNumeriche(int max){
		this.max = max;
	}

}
