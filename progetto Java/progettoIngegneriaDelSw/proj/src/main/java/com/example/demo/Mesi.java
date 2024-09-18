package com.example.demo;

public enum Mesi {
    GENNAIO(0, "Gennaio"),
    FEBBRAIO(1, "Febbraio"),
    MARZO(2, "Marzo"),
    APRILE(3, "Aprile"),
    MAGGIO(4, "Maggio"),
    GIUGNO(5, "Giugno"),
    LUGLIO(6, "Luglio"),
    AGOSTO(7, "Agosto"),
    SETTEMBRE(8, "Settembre"),
    OTTOBRE(9, "Ottobre"),
    NOVEMBRE(10, "Novembre"),
    DICEMBRE(11, "Dicembre");

    private final int valore;
    private final String nome;

    Mesi(int valore, String nome) {
        this.valore = valore;
        this.nome = nome;
    }


    public int getValore() {
        return valore;
    }

    public String getNome() {
        return nome;
    }

    public static String getByValore(int valore) {
        for (Mesi mese : Mesi.values()) {
            if (mese.getValore() == valore) {
                return mese.getNome();
            }
        }
        throw new IllegalArgumentException("Valore non valido per un mese: " + valore);
    }
}
