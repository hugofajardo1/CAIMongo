package com.example.mongo.model;

public class Punto {

    private String id;

    private Float latitud;

    private Float longitud;

    private Integer cont;

    public Punto(String id, Integer cont) {
        this.setId(id);
        String cadena = id.substring(34, id.length()-2);
        int posicion = cadena.indexOf(",");
        this.setLatitud(Float.parseFloat(cadena.substring(0, posicion-1)));
        this.setLongitud(Float.parseFloat(cadena.substring(posicion+1, cadena.length()-1)));

        this.setCont(cont);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public Integer getCont() {
        return cont;
    }

    public void setCont(Integer cont) {
        this.cont = cont;
    }
}
