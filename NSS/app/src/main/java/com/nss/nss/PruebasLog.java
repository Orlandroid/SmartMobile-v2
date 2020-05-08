package com.nss.nss;

public class PruebasLog {

    private String fecha;
    private String tag;
    private String valor;

    public PruebasLog(String fecha, String tag, String valor) {
        this.fecha = fecha;
        this.tag = tag;
        this.valor = valor;
    }

    public String getFecha() {
        return fecha;
    }

    public String getTag() {
        return tag;
    }

    public String getValor() {
        return valor;
    }

}
