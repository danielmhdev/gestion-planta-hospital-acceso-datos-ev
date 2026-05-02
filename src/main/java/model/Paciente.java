package model;

import model.enums.Sexo;
import java.time.LocalDate;

public class Paciente {
    private int idPaciente;
    private String nhc;
    private String nombre;
    private String apellidos;
    private Sexo sexo;
    private LocalDate fechaNacimiento;
    private int idHabitacion;

    // Constructor vacío
    public Paciente() {
    }

    // Constructor sin ID para hacer INSERT en la BBDD
    public Paciente(String nhc, String nombre, String apellidos, Sexo sexo, LocalDate fechaNacimiento, int idHabitacion) {
        this.nhc = nhc;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.idHabitacion = idHabitacion;
    }

    // Constructor CON ID para hacer SELECT desde la BBDD
    public Paciente(int idPaciente, String nhc, String nombre, String apellidos, Sexo sexo, LocalDate fechaNacimiento, int idHabitacion) {
        this.idPaciente = idPaciente;
        this.nhc = nhc;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.idHabitacion = idHabitacion;
    }

    // Getters
    public int getIdPaciente() { return idPaciente; }
    public String getNhc() { return nhc; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public Sexo getSexo() { return sexo; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public int getIdHabitacion() { return idHabitacion; }

    // Setters
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }
    public void setNhc(String nhc) { this.nhc = nhc; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setSexo(Sexo sexo) { this.sexo = sexo; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setIdHabitacion(int idHabitacion) { this.idHabitacion = idHabitacion; }

    @Override
    public String toString() {
        return "ID: " + idPaciente + " | NHC: " + nhc + " | Paciente: " + nombre + " " + apellidos +
                " | Sexo: " + sexo + " | Fecha Nac: " + fechaNacimiento + " | Hab: " + idHabitacion;
    }
}