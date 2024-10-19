package it.univr.quindici;

public class FattoriaDiTessereNumeriche extends FattoriaDiTessere<Integer> {

	private int max;
	@Override
	public Tessera<Integer> get() {
		// TODO Auto-generated method stub
		return new Tessera<Integer>(1 + random.nextInt(max));
	}
	
	FattoriaDiTessereNumeriche(int max) {
		this.max = max;
	}

}
