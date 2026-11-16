/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;

/**
 *
 * @author Santiago Castillo
 * @version 1.0 Created: 16 nov. 2026
 */

public class Empleado implements Serializable{
    /*
     * Atributos
    */
    private String numeroIdentificacion;
    private String nombreCompleto;
    private String tituloProfesional;
    private byte aniosExperiencia;

    /*
     * Constructor
    */
    public Empleado(String numeroIdentificacion, String nombreCompleto, String tituloProfesional, byte aniosExperiencia) {
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombreCompleto = nombreCompleto;
        this.tituloProfesional = tituloProfesional;
        this.aniosExperiencia = aniosExperiencia;
    }

    /*
     * Getter
    */
    public String getNumeroIdentificacion(){
        return numeroIdentificacion;
    }
    public String getNombreCompleto(){
        return nombreCompleto;
    }
    public String getTituloProfesional(){
        return tituloProfesional;
    }
    public byte getAniosExperiencia(){
        return aniosExperiencia;
    }

    /*
     * Setter
    */
    public void setNumeroIdentificacion(String numeroIdentificacion){
        this.numeroIdentificacion = numeroIdentificacion;
    }
    public void setNombreCompleto(String nombreCompleto){
        this.nombreCompleto = nombreCompleto;
    }
    public void setTituloProfesional(String tituloProfesional){
        this.tituloProfesional = tituloProfesional;
    }
    public void setAniosExperiencia(byte aniosExperiencia){
        this.aniosExperiencia = aniosExperiencia;
    }

    /*
     * toString()
    */
    @Override
    public String toString() {
        return "Empleado:"
                + "\n     Numero de identificacion: " + numeroIdentificacion
                + "\n     Nombre completo: " + nombreCompleto
                + "\n     Titulo profesional: " + tituloProfesional
                + "\n     Anios de experiencia: " + aniosExperiencia;
    }

}//fin class
