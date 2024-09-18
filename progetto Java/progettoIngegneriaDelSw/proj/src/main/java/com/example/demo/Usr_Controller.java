package com.example.demo;

public abstract class Usr_Controller {

    public static String cod_fiscale;
    public static String sede;
    public static String servizio;

    public abstract void setCod_fiscale(String cod_fiscale);
    public abstract String getCod_fiscale();

    public abstract void setSede(String sede);
    public abstract String getSede();

    public abstract void setServizio(String servizio);
    public abstract String getServizio();

}
