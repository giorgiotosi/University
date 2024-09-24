package it.univr.quindici;

public class FattoriaDiTessereAlfabetiche extends FattoriaDiTessere<String> {

	@Override
	public Tessera<String> get() {
		// TODO Auto-generated method stub
		String result = "";
		for(int i = 0; i<(1+random.nextInt(5)); i++) {
			result += (char) ( 'a' + random.nextInt(26));
		}
		return new Tessera<String>(result);
	}

}
