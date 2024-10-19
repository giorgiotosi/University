package it.univr.letters;

public class Main {

	public static void main(String[] args) {
		Letters l1 = new LowerCase(5);
		System.out.println("l1 = " + l1);
		System.out.println("I suoi caratteri sono:");
		l1.forEach(System.out::println);
		Letters l2 = new Vulcanian(20);
		System.out.println("\nl2 = " + l2);
		System.out.println("I suoi caratteri non italiani sono:");
		l2.forEach(c -> {
			if (c == 'j' || c == 'k' || c == 'w' || c == 'x' || c == 'y')
				System.out.println(c);
		});
		System.out.println();
		createLowerCase("tmwdmghbonqlwbwpiqkv");
		createLowerCase("tmwd(ghbonqlwbwpiqkv");
		createLowerCase("tmwDmghbonqlwbwpiqkv");
		createVulcanian("iobcchjjknnnnnprsstx");
		createVulcanian("ioBcchjjknnnnnprsstx");
		createVulcanian("io(cchjjknnnnnprsstx");
		createVulcanian("ibocchjjknnnnnprsstx");
		createVulcanian("oibcchjjknnnnnprsstx");
		createVulcanian("iocbchjjknnnnnprsstx");
	}

	/**
	 * Prova a creare un oggetto LowerCase a partire dalla stringa s.
	 * Se ci riesce, stampa: new LowerCase(s) => successo
	 * Se invece la creazione va in eccezione,
	 * stampa: new LowerCase(s) => eccezione
	 */
	private static void createLowerCase(String s) {
		boolean flag = true;
		try {
			new LowerCase(s);
		}
		catch(IllegalArgumentException e) {
			flag = false;
			System.out.println(String.format("new LowerCase("+s+") => eccezione"));
		}
		if(flag) {
			System.out.println("new LowerCase("+s+") => successo");
		}
		
	}

	/**
	 * Prova a creare un oggetto Vulcanian a partire dalla stringa s.
	 * Se ci riesce, stampa: new Vulcanian(s) => successo
	 * Se invece la creazione va in eccezione,
	 * stampa: new Vulcanian(s) => eccezione
	 */
	private static void createVulcanian(String s) {
		boolean flag = true;
		try {
			new Vulcanian(s);
		}
		catch(IllegalArgumentException e) {
			flag = false;
			System.out.println("new Vulcanian("+s+") => eccezione");
		}
		if(flag) {
			System.out.println("new Vulcanian("+s+") => successo");
		}
	}
}