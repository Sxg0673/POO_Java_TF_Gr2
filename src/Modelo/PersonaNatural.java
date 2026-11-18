/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Santiago Castillo
 * @version 1.0 Created: 16 nov. 2026
 */

public final class PersonaNatural extends Cliente{
    /*
     * Atributos
    */
    private String numeroTelefono;
    private String correoElectronico;

    /*
     * Constructor
    */
    public PersonaNatural(String numeroIdentificacion, String nombre, String nombreCiudad, String numeroTelefono, String correoElectronico) {
        super(numeroIdentificacion, nombre, nombreCiudad);
        this.numeroTelefono = numeroTelefono;
        this.correoElectronico = correoElectronico;
    }

    /*
     * Getter
    */
    public String getNumeroTelefono(){
        return numeroTelefono;
    }
    public String getCorreoElectronico(){
        return correoElectronico;
    }

    /*
     * Setter
    */
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
        return "Persona natural:"
                + super.toString()
                + "\n     Numero de telefono: " + numeroTelefono
                + "\n     Correo electronico: " + correoElectronico;
    }

}//fin class
