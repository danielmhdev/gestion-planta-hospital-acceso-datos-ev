package model;

import model.enums.Categoria;
import model.enums.Turno;

public class Personal {
    private int idPersonal;
    private String nombre;
    private String apellidos;
    private Categoria categoria;
    private Turno turno;

    // Constructor vacío
    public Personal(){
    }

    // Constructor sin ID para hacer INSERT en la BBDD
    public Personal( String nombre, String apellidos, Categoria categoria, Turno turno) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.categoria = categoria;
        this.turno = turno;
    }

    // Constructor CON ID para hacer SELECT desde la BBDD
    public Personal(int idPersonal, String nombre, String apellidos, Categoria categoria, Turno turno) {
        this.idPersonal = idPersonal;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.categoria = categoria;
        this.turno = turno;
    }

    //Getters
    public int getIdPersonal() {return idPersonal;}
    public String getNombre() {return nombre;}
    public String getApellidos() {return apellidos;}
    public Categoria getCategoria() {return categoria;}
    public Turno getTurno() {return turno;}

    //Setters
    public void setIdPersonal(int idPersonal) {this.idPersonal = idPersonal;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setApellidos(String apellidos) {this.apellidos = apellidos;}
    public void setCategoria(Categoria categoria) {this.categoria = categoria;}
    public void setTurno(Turno turno) {this.turno = turno;}


    @Override
    public String toString() {
        return "ID: " + idPersonal + " | Nombre: " + nombre + " " + apellidos +
                " | Categoria: " + categoria + " | Turno: " + turno;
    }
}
