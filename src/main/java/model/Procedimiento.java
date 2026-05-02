package model;

import java.time.LocalDateTime;

public class Procedimiento {
    private int idProcedimiento;
    private LocalDateTime fechaHora;
    private String observaciones;
    private int idPaciente;
    private int idPersonal;

    // Constructor vacío
    public Procedimiento() {
    }

    // Constructor sin ID para hacer INSERT en la BBDD
    public Procedimiento(LocalDateTime fechaHora, String observaciones, int idPaciente, int idPersonal) {
        this.fechaHora = fechaHora;
        this.observaciones = observaciones;
        this.idPaciente = idPaciente;
        this.idPersonal = idPersonal;
    }

    // Constructor CON ID para hacer SELECT desde la BBDD
    public Procedimiento(int idProcedimiento, LocalDateTime fechaHora, String observaciones, int idPaciente, int idPersonal) {
        this.idProcedimiento = idProcedimiento;
        this.fechaHora = fechaHora;
        this.observaciones = observaciones;
        this.idPaciente = idPaciente;
        this.idPersonal = idPersonal;
    }

    // Getters
    public int getIdProcedimiento() { return idProcedimiento; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getObservaciones() { return observaciones; }
    public int getIdPaciente() { return idPaciente; }
    public int getIdPersonal() { return idPersonal; }

    // Setters
    public void setIdProcedimiento(int idProcedimiento) { this.idProcedimiento = idProcedimiento; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }
    public void setIdPersonal(int idPersonal) { this.idPersonal = idPersonal; }

    @Override
    public String toString() {
        return "ID: " + idProcedimiento + " | Fecha: " + fechaHora +
                " | Paciente: " + idPaciente + " | Personal: " + idPersonal +
                " | Obs: " + observaciones;
    }
}