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

public class Contacto implements Serializable{
    /*
     * Atributos
    */
    private String nombreCompleto;
    private String cargo;
    private String numeroTelefono;
    private String correoElectronico;

    /*
     * Constructor
    */
    public Contacto(String nombreCompleto, String cargo, String numeroTelefono, String correoElectronico) {
        this.nombreCompleto = nombreCompleto;
        this.cargo = cargo;
        this.numeroTelefono = numeroTelefono;
        this.correoElectronico = correoElectronico;
    }

    /*
     * Getter
    */
    public String getNombreCompleto(){
        return nombreCompleto;
    }
    public String getCargo(){
        return cargo;
    }
    public String getNumeroTelefono(){
        return numeroTelefono;
    }
    public String getCorreoElectronico(){
        return correoElectronico;
    }

    /*
     * Setter
    */
    public void setNombreCompleto(String nombreCompleto){
        this.nombreCompleto = nombreCompleto;
    }
    public void setCargo(String cargo){
        this.cargo = cargo;
    }
    public void setNumeroTelefono(String numeroTelefono){
        this.numeroTelefono = numeroTelefono;
    }
    public void setCorreoElectronico(String correoElectronico){
        this.correoElectronico = correoElectronico;
    }

    /*
     * toString()
    */
    @Override
    public String toString() {
        return "Contacto:"
                + "\n     Nombre completo: " + nombreCompleto
                + "\n     Cargo: " + cargo
                + "\n     Numero de telefono: " + numeroTelefono
                + "\n     Correo electronico: " + correoElectronico;
    }

}//fin class
