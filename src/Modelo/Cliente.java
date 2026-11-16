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

public abstract class Cliente implements Serializable{
    /*
     * Atributos
    */
    protected String numeroIdentificacion;
    protected String nombre;
    protected String nombreCiudad;

    /*
     * Constructor
    */
    public Cliente(String numeroIdentificacion, String nombre, String nombreCiudad) {
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombre = nombre;
        this.nombreCiudad = nombreCiudad;
    }

    /*
     * Getter
    */
    public String getNumeroIdentificacion(){
        return numeroIdentificacion;
    }
    public String getNombre(){
        return nombre;
    }
    public String getNombreCiudad(){
        return nombreCiudad;
    }

    /*
     * Setter
    */
    public void setNumeroIdentificacion(String numeroIdentificacion){
        this.numeroIdentificacion = numeroIdentificacion;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setNombreCiudad(String nombreCiudad){
        this.nombreCiudad = nombreCiudad;
    }

    /*
     * toString()
    */
        //Devuelve los datos comunes; cada subclase agrega su encabezado y sus datos propios
    @Override
    public String toString() {
        return "\n     Numero de identificacion: " + numeroIdentificacion
                + "\n     Nombre: " + nombre
                + "\n     Ciudad: " + nombreCiudad;
    }

}//fin class
