package com.example.aplicacionsumativa.models;

public class Sismos {

    private String Fecha;
    private String Profundidad;
    private Float Magnitud;
    private String RefGeografica;
    private String FechaUpdate;

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getProfundidad() {
        return Profundidad;
    }

    public void setProfundidad(String profundidad) {
        Profundidad = profundidad;
    }

    public Float getMagnitud() {
        return Magnitud;
    }

    public void setMagnitud(Float magnitud) {
        Magnitud = magnitud;
    }

    public String getRefGeografica() {
        return RefGeografica;
    }

    public void setRefGeografica(String refGeografica) {
        RefGeografica = refGeografica;
    }

    public String getFechaUpdate() {
        return FechaUpdate;
    }

    public void setFechaUpdate(String fechaUpdate) {
        FechaUpdate = fechaUpdate;
    }

}
