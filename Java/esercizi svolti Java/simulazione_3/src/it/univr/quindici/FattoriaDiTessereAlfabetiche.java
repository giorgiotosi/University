package it.univr.quindici;

public class FattoriaDiTessereAlfabetiche extends FattoriaDiTessere<String> {

	@Override
	public Tessera<String> get() {
		String value = "";
		int numCaratteri = random.nextInt(5) + 1;
		for(int i = 0; i< numCaratteri; i++) {
			value += (char)('a' +  random.nextInt(26));
		}
		return new Tessera<>(value);
	}
}
