package model;

import model.enums.Estado;

public class Habitacion {
    private int idHabitacion;
    private int numero;
    private String planta;
    private Estado estado;

    // Constructor vacío
    public Habitacion() {
    }

    // Constructor sin ID para hacer INSERT en la BBDD
    public Habitacion(int numero, String planta, Estado estado) {
        this.numero = numero;
        this.planta = planta;
        this.estado = estado;
    }

    // Constructor CON ID para hacer SELECT desde la BBDD
    public Habitacion(int idHabitacion, int numero, String planta, Estado estado) {
        this.idHabitacion = idHabitacion;
        this.numero = numero;
        this.planta = planta;
        this.estado = estado;
    }

    // Getters
    public int getIdHabitacion() { return idHabitacion; }
    public int getNumero() { return numero; }
    public String getPlanta() { return planta; }
    public Estado getEstado() { return estado; }

    // Setters
    public void setIdHabitacion(int idHabitacion) { this.idHabitacion = idHabitacion; }
    public void setNumero(int numero) { this.numero = numero; }
    public void setPlanta(String planta) { this.planta = planta; }
    public void setEstado(Estado estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "ID: " + idHabitacion + " | Num: " + numero + " | Planta: " + planta + " | Estado: " + estado;
    }
}