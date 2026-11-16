/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Santiago Castillo
 * @version 1.0 Created: 16 nov. 2026
 */

public class Proyecto implements Serializable{
    /*
     * Atributos
    */
    private String codigo;//8 caracteres: anio, guion y consecutivo anual, ej. 2026-001
    private String titulo;//entre 20 y 100 caracteres
    private LocalDate fechaInicio;//formato aaaa-mm-dd
    private String unidadDuracion;//semanas, meses, anios
    private byte duracionEstimada;//semanas: 1 a 3; meses: 1 a 11, 13 a 23, 25 a 35; anios: 1 a 3
    private Cliente suCliente;
    private Empleado suGerente;
    private ArrayList<Empleado> suEquipo;
    private String estado;//INICIADO (por defecto), EN CURSO, CANCELADO, FINALIZADO, SUSPENDIDO, APLAZADO

    /*
     * Constructor
    */
        //Constructor con equipo
    public Proyecto(String codigo, String titulo, LocalDate fechaInicio, String unidadDuracion, byte duracionEstimada,
                    Cliente suCliente, Empleado suGerente, ArrayList<Empleado> suEquipo) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.fechaInicio = fechaInicio;
        this.unidadDuracion = unidadDuracion;
        this.duracionEstimada = duracionEstimada;
        this.suCliente = suCliente;
        this.suGerente = suGerente;
        this.suEquipo = suEquipo;
        this.estado = "INICIADO";
    }

        //Constructor sin equipo: el equipo se crea vacío
    public Proyecto(String codigo, String titulo, LocalDate fechaInicio, String unidadDuracion, byte duracionEstimada,
                    Cliente suCliente, Empleado suGerente) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.fechaInicio = fechaInicio;
        this.unidadDuracion = unidadDuracion;
        this.duracionEstimada = duracionEstimada;
        this.suCliente = suCliente;
        this.suGerente = suGerente;
        this.suEquipo = new ArrayList<>();
        this.estado = "INICIADO";
    }

    /*
     * Getter
    */
    public String getCodigo(){
        return codigo;
    }
    public String getTitulo(){
        return titulo;
    }
    public LocalDate getFechaInicio(){
        return fechaInicio;
    }
    public String getUnidadDuracion(){
        return unidadDuracion;
    }
    public byte getDuracionEstimada(){
        return duracionEstimada;
    }
    public Cliente getSuCliente(){
        return suCliente;
    }
    public Empleado getSuGerente(){
        return suGerente;
    }
    public ArrayList<Empleado> getSuEquipo(){
        return suEquipo;
    }
    public String getEstado(){
        return estado;
    }

    /*
     * Setter
    */
    public void setCodigo(String codigo){
        this.codigo = codigo;
    }
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    public void setFechaInicio(LocalDate fechaInicio){
        this.fechaInicio = fechaInicio;
    }
    public void setUnidadDuracion(String unidadDuracion){
        this.unidadDuracion = unidadDuracion;
    }
    public void setDuracionEstimada(byte duracionEstimada){
        this.duracionEstimada = duracionEstimada;
    }
    public void setSuCliente(Cliente suCliente){
        this.suCliente = suCliente;
    }
    public void setSuGerente(Empleado suGerente){
        this.suGerente = suGerente;
    }
    public void setSuEquipo(ArrayList<Empleado> suEquipo){
        this.suEquipo = suEquipo;
    }
    public void setEstado(String estado){
        this.estado = estado;
    }

    /*
     * toString()
    */
    @Override
    public String toString() {
        return "Proyecto:"
                + "\n     Codigo: " + codigo
                + "\n     Titulo: " + titulo
                + "\n     Fecha de inicio: " + fechaInicio
                + "\n     Unidad de duracion: " + unidadDuracion
                + "\n     Duracion estimada: " + duracionEstimada
                + "\n     Cliente - " + suCliente.toString().replace("\n", "\n     ")
                + "\n     Gerente - " + suGerente.toString().replace("\n", "\n     ")
                + "\n     Integrantes del equipo: " + suEquipo.size()
                + "\n     Estado: " + estado;
    }

    /*
     * Métodos de negocio
    */
        //Fecha de finalización, equivale a la fecha de inicio más la duración estimada según la unidad de duración
    public LocalDate determinarFechaFinalizacion(){
        LocalDate fechaFinalizacion;
        if(unidadDuracion.equals("semanas")){fechaFinalizacion = fechaInicio.plusWeeks(duracionEstimada);}
        else if(unidadDuracion.equals("meses")){fechaFinalizacion = fechaInicio.plusMonths(duracionEstimada);}
        else{fechaFinalizacion = fechaInicio.plusYears(duracionEstimada);}
        return fechaFinalizacion;
    }

        //Agrega un integrante al final del equipo del proyecto
    public void agregarIntegrante(Empleado nuevoIntegrante){
        suEquipo.add(nuevoIntegrante);
    }

        //Elimina del equipo el integrante con el número de identificación indicado y lo devuelve; si no existe, devuelve null
    public Empleado eliminarIntegrante(String numeroIdentificacion){
        Empleado integranteEliminado = null;
        for(int i=0; i<suEquipo.size();i++){
            if(suEquipo.get(i).getNumeroIdentificacion().equals(numeroIdentificacion)){
                integranteEliminado = suEquipo.remove(i);
                break;
            }
        }
        return integranteEliminado;
    }

}//fin class
