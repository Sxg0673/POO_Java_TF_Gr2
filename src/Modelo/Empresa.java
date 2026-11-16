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

public final class Empresa extends Cliente{
    /*
     * Atributos
    */
    private Contacto suContacto;

    /*
     * Constructor
    */
    public Empresa(String numeroIdentificacion, String nombre, String nombreCiudad, Contacto suContacto) {
        super(numeroIdentificacion, nombre, nombreCiudad);
        this.suContacto = suContacto;
    }

    /*
     * Getter
    */
    public Contacto getSuContacto(){
        return suContacto;
    }

    /*
     * Setter
    */
    public void setSuContacto(Contacto suContacto){
        this.suContacto = suContacto;
    }

    /*
     * toString()
    */
    @Override
    public String toString() {
        return "Empresa:"
                + super.toString()
                + "\n     " + suContacto.toString().replace("\n", "\n     ");
    }

}//fin class
