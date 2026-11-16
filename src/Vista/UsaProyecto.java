/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Modelo.Cliente;
import Modelo.Contacto;
import Modelo.Empleado;
import Modelo.Empresa;
import Modelo.PersonaNatural;
import Modelo.Proyecto;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Santiago Castillo
 * @version 1.0 Created: 16 nov. 2026
 */
public class UsaProyecto extends javax.swing.JFrame {
    /*
     * Definir y crear las colecciones "ArrayList"
    */
    private static ArrayList<Proyecto> losProyectos = new ArrayList<>();
    private static ArrayList<Cliente> losClientes = new ArrayList<>();

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(UsaProyecto.class.getName());

    /**
     * Creates new form UsaProyecto
     */
    public UsaProyecto() {
        initComponents();
    }

    /*
     * ======================== Métodos: registrar proyectos ========================
    */

    //generarCodigoProyecto: conforma el código con el anio actual, un guion y el consecutivo del anio (3 dígitos), ej. 2026-001.
    public String generarCodigoProyecto(ArrayList<Proyecto> datos){
        String codigo;
        int anioActual = LocalDate.now().getYear();
        int consecutivo = 1;

        //Contar los proyectos registrados en el anio actual
        for(Proyecto objeto : datos){
            if(objeto.getCodigo().startsWith(anioActual + "-")){
                consecutivo++;
            }
        }

        //Conformar el código completando con ceros el consecutivo
        if(consecutivo<10){codigo = anioActual + "-00" + consecutivo;}
        else if(consecutivo<100){codigo = anioActual + "-0" + consecutivo;}
        else{codigo = anioActual + "-" + consecutivo;}
        return codigo;
    }

    //registrarProyecto: solicita los datos de un proyecto, crea el objeto, lo agrega al final de la colección y la devuelve.
    public ArrayList<Proyecto> registrarProyecto(ArrayList<Proyecto> datos){
        //----- 1. Obtener los datos del objeto Proyecto
        String codigo;
        String titulo;
        LocalDate fechaInicio;
        int opcionUnidad;
        String unidadDuracion;
        byte duracionEstimada;
        boolean duracionValida;
        String numeroIdeCliente;
        Cliente objCliente = null;
        String numeroIdeGerente;
        Empleado objGerente = null;
        String entrada;//texto digitado en la ventana, se verifica que no se haya cancelado antes de convertirlo

            //Generación del código del proyecto
        codigo = generarCodigoProyecto(datos);
        JOptionPane.showMessageDialog(null, "Código asignado al proyecto: " + codigo, "REGISTRAR PROYECTO: código", JOptionPane.INFORMATION_MESSAGE);

            //Validación por obligatoriedad y longitud
        do{
            titulo = JOptionPane.showInputDialog("Título del proyecto, obligatorio, entre 20 y 100 caracteres");
            if(titulo == null){
                JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.", "REGISTRAR PROYECTO", JOptionPane.WARNING_MESSAGE);
                return datos;
            }
            if(titulo.isEmpty() || titulo.isBlank() || titulo.length()<20 || titulo.length()>100){
                JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío o no cumple la longitud exigida.\n",
                                            "REGISTRAR PROYECTO: título", JOptionPane.ERROR_MESSAGE);
            }
        } while(titulo.isEmpty() || titulo.isBlank() || titulo.length()<20 || titulo.length()>100);

            //Gestión de excepción de formato de fecha
        try{
            entrada = JOptionPane.showInputDialog("Fecha de inicio (aaaa-mm-dd)");
            if(entrada == null){
                JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.", "REGISTRAR PROYECTO", JOptionPane.WARNING_MESSAGE);
                return datos;
            }
            fechaInicio = LocalDate.parse(entrada);
        } catch(DateTimeException nfex){
            JOptionPane.showMessageDialog(null, "Operación fallida: entrada inválida por formato incorrecto.\n"
                                        + nfex.toString(), "REGISTRAR PROYECTO: fecha de inicio", JOptionPane.ERROR_MESSAGE);
            return datos;
        }

            //Gestión de excepción de formato numérico y Validación para restricción de dominio
        try{
            do{
                entrada = JOptionPane.showInputDialog("Unidad de duración\n"
                                                                        + "1. Semanas\n"
                                                                        + "2. Meses\n"
                                                                        + "3. Anios\n"
                                                                        + "\nSeleccione la opción deseada\n");
                if(entrada == null){
                    JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.", "REGISTRAR PROYECTO", JOptionPane.WARNING_MESSAGE);
                    return datos;
                }
                opcionUnidad = Integer.parseInt(entrada);
                if(opcionUnidad<1 || opcionUnidad>3){JOptionPane.showMessageDialog(null, "Operación fallida: entrada inválida por valor incorrecto.\n",
                                                            "REGISTRAR PROYECTO: unidad de duración", JOptionPane.ERROR_MESSAGE);}
            } while(opcionUnidad<1 || opcionUnidad>3);
        } catch(NumberFormatException nfex){
            JOptionPane.showMessageDialog(null, "Operación fallida: entrada inválida por formato incorrecto.\n"
                                        + nfex.getMessage(), "REGISTRAR PROYECTO: unidad de duración", JOptionPane.ERROR_MESSAGE);
            return datos;
        }
        if(opcionUnidad==1){unidadDuracion = "semanas";}
        else if(opcionUnidad==2){unidadDuracion = "meses";}
        else{unidadDuracion = "anios";}

            //Gestión de excepción de formato numérico y Validación para restricción de dominio
            //semanas: 1 a 3; meses: 1 a 11, 13 a 23, 25 a 35; anios: 1 a 3
        try{
            do{
                entrada = JOptionPane.showInputDialog("Duración estimada en " + unidadDuracion + "\n"
                                                                        + "(semanas: 1 a 3; meses: 1 a 11, 13 a 23, 25 a 35; anios: 1 a 3)");
                if(entrada == null){
                    JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.", "REGISTRAR PROYECTO", JOptionPane.WARNING_MESSAGE);
                    return datos;
                }
                duracionEstimada = Byte.parseByte(entrada);
                if(unidadDuracion.equals("meses")){
                    duracionValida = duracionEstimada>=1 && duracionEstimada<=35 && duracionEstimada!=12 && duracionEstimada!=24;
                } else{
                    duracionValida = duracionEstimada>=1 && duracionEstimada<=3;
                }
                if(!duracionValida){JOptionPane.showMessageDialog(null, "Operación fallida: entrada inválida por valor incorrecto.\n",
                                                            "REGISTRAR PROYECTO: duración estimada", JOptionPane.ERROR_MESSAGE);}
            } while(!duracionValida);
        } catch(NumberFormatException nfex){
            JOptionPane.showMessageDialog(null, "Operación fallida: entrada inválida por formato incorrecto.\n"
                                        + nfex.getMessage(), "REGISTRAR PROYECTO: duración estimada", JOptionPane.ERROR_MESSAGE);
            return datos;
        }

        //----- 1a. Obtener el cliente del proyecto

            //Validación por obligatoriedad
        do{
            numeroIdeCliente = JOptionPane.showInputDialog("Número de identificación del cliente, obligatorio");
            if(numeroIdeCliente == null){
                JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.", "REGISTRAR PROYECTO", JOptionPane.WARNING_MESSAGE);
                return datos;
            }
            if(numeroIdeCliente.isEmpty() || numeroIdeCliente.isBlank()){
                JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n",  "REGISTRAR PROYECTO: número identificación cliente", JOptionPane.ERROR_MESSAGE);
            }
        } while(numeroIdeCliente.isEmpty() || numeroIdeCliente.isBlank());

            //Buscar el cliente en los proyectos registrados para no solicitar nuevamente sus datos
        for(Proyecto objeto : datos){
            if(objeto.getSuCliente().getNumeroIdentificacion().equals(numeroIdeCliente)){
                objCliente = objeto.getSuCliente();
            }
        }

        if(objCliente != null){
            JOptionPane.showMessageDialog(null, "El cliente ya se encuentra registrado, se asigna al proyecto.\n" + objCliente.toString(),
                                        "REGISTRAR PROYECTO: cliente", JOptionPane.INFORMATION_MESSAGE);
        } else{
            int opcionTipoCliente;
            String nombreCliente;
            String nombreCiudad;

                //Gestión de excepción de formato numérico y Validación para restricción de dominio
            try{
                do{
                    entrada = JOptionPane.showInputDialog("Tipo de cliente\n"
                                                                            + "1. Empresa\n"
                                                                            + "2. Persona natural\n"
                                                                            + "\nSeleccione la opción deseada\n");
                    if(entrada == null){
                        JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.", "REGISTRAR PROYECTO", JOptionPane.WARNING_MESSAGE);
                        return datos;
                    }
                    opcionTipoCliente = Integer.parseInt(entrada);
                    if(opcionTipoCliente<1 || opcionTipoCliente>2){JOptionPane.showMessageDialog(null, "Operación fallida: entrada inválida por valor incorrecto.\n",
                                                                "REGISTRAR PROYECTO: tipo de cliente", JOptionPane.ERROR_MESSAGE);}
                } while(opcionTipoCliente<1 || opcionTipoCliente>2);
            } catch(NumberFormatException nfex){
                JOptionPane.showMessageDialog(null, "Operación fallida: entrada inválida por formato incorrecto.\n"
                                            + nfex.getMessage(), "REGISTRAR PROYECTO: tipo de cliente", JOptionPane.ERROR_MESSAGE);
                return datos;
            }

                //Validación por obligatoriedad
            do{
                nombreCliente = JOptionPane.showInputDialog("Nombre del cliente, obligatorio");
                if(nombreCliente == null){
                    JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.", "REGISTRAR PROYECTO", JOptionPane.WARNING_MESSAGE);
                    return datos;
                }
                if(nombreCliente.isEmpty() || nombreCliente.isBlank()){
                    JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n",  "REGISTRAR PROYECTO: nombre cliente", JOptionPane.ERROR_MESSAGE);
                }
            } while(nombreCliente.isEmpty() || nombreCliente.isBlank());

                //Validación por obligatoriedad
            do{
                nombreCiudad = JOptionPane.showInputDialog("Ciudad del cliente, obligatorio");
                if(nombreCiudad == null){
                    JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.", "REGISTRAR PROYECTO", JOptionPane.WARNING_MESSAGE);
                    return datos;
                }
                if(nombreCiudad.isEmpty() || nombreCiudad.isBlank()){
                    JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n",  "REGISTRAR PROYECTO: ciudad cliente", JOptionPane.ERROR_MESSAGE);
                }
            } while(nombreCiudad.isEmpty() || nombreCiudad.isBlank());

            if(opcionTipoCliente==1){
                //Cliente Empresa: obtener los datos de su contacto
                String nombreContacto;
                String cargoContacto;
                String telefonoContacto;
                String correoContacto;

                    //Validación por obligatoriedad
                do{
                    nombreContacto = JOptionPane.showInputDialog("Nombre completo del contacto de la empresa, obligatorio");
                    if(nombreContacto == null){
                        JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.", "REGISTRAR PROYECTO", JOptionPane.WARNING_MESSAGE);
                        return datos;
                    }
                    if(nombreContacto.isEmpty() || nombreContacto.isBlank()){
                        JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n",  "REGISTRAR PROYECTO: nombre contacto", JOptionPane.ERROR_MESSAGE);
                    }
                } while(nombreContacto.isEmpty() || nombreContacto.isBlank());

                    //Validación por obligatoriedad
                do{
                    cargoContacto = JOptionPane.showInputDialog("Cargo del contacto de la empresa, obligatorio");
                    if(cargoContacto == null){
                        JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.", "REGISTRAR PROYECTO", JOptionPane.WARNING_MESSAGE);
                        return datos;
                    }
                    if(cargoContacto.isEmpty() || cargoContacto.isBlank()){
                        JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n",  "REGISTRAR PROYECTO: cargo contacto", JOptionPane.ERROR_MESSAGE);
                    }
                } while(cargoContacto.isEmpty() || cargoContacto.isBlank());

                    //Validación por obligatoriedad
                do{
                    telefonoContacto = JOptionPane.showInputDialog("Número de teléfono del contacto de la empresa, obligatorio");
                    if(telefonoContacto == null){
                        JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.", "REGISTRAR PROYECTO", JOptionPane.WARNING_MESSAGE);
                        return datos;
                    }
                    if(telefonoContacto.isEmpty() || telefonoContacto.isBlank()){
                        JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n",  "REGISTRAR PROYECTO: teléfono contacto", JOptionPane.ERROR_MESSAGE);
                    }
                } while(telefonoContacto.isEmpty() || telefonoContacto.isBlank());

                    //Validación por obligatoriedad
                do{
                    correoContacto = JOptionPane.showInputDialog("Correo electrónico del contacto de la empresa, obligatorio");
                    if(correoContacto == null){
                        JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.", "REGISTRAR PROYECTO", JOptionPane.WARNING_MESSAGE);
                        return datos;
                    }
                    if(correoContacto.isEmpty() || correoContacto.isBlank()){
                        JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n",  "REGISTRAR PROYECTO: correo contacto", JOptionPane.ERROR_MESSAGE);
                    }
                } while(correoContacto.isEmpty() || correoContacto.isBlank());

                Contacto objContacto;
                objContacto = new Contacto(nombreContacto,cargoContacto,telefonoContacto,correoContacto);
                objCliente = new Empresa(numeroIdeCliente,nombreCliente,nombreCiudad,objContacto);
            } else{
                //Cliente PersonaNatural: obtener sus datos propios
                String telefonoCliente;
                String correoCliente;

                    //Validación por obligatoriedad
                do{
                    telefonoCliente = JOptionPane.showInputDialog("Número de teléfono del cliente, obligatorio");
                    if(telefonoCliente == null){
                        JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.", "REGISTRAR PROYECTO", JOptionPane.WARNING_MESSAGE);
                        return datos;
                    }
                    if(telefonoCliente.isEmpty() || telefonoCliente.isBlank()){
                        JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n",  "REGISTRAR PROYECTO: teléfono cliente", JOptionPane.ERROR_MESSAGE);
                    }
                } while(telefonoCliente.isEmpty() || telefonoCliente.isBlank());

                    //Validación por obligatoriedad
                do{
                    correoCliente = JOptionPane.showInputDialog("Correo electrónico del cliente, obligatorio");
                    if(correoCliente == null){
                        JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.", "REGISTRAR PROYECTO", JOptionPane.WARNING_MESSAGE);
                        return datos;
                    }
                    if(correoCliente.isEmpty() || correoCliente.isBlank()){
                        JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n",  "REGISTRAR PROYECTO: correo cliente", JOptionPane.ERROR_MESSAGE);
                    }
                } while(correoCliente.isEmpty() || correoCliente.isBlank());

                objCliente = new PersonaNatural(numeroIdeCliente,nombreCliente,nombreCiudad,telefonoCliente,correoCliente);
            }
        }

        //----- 1b. Obtener el gerente del proyecto

            //Validación por obligatoriedad
        do{
            numeroIdeGerente = JOptionPane.showInputDialog("Número de identificación del gerente, obligatorio");
            if(numeroIdeGerente == null){
                JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.", "REGISTRAR PROYECTO", JOptionPane.WARNING_MESSAGE);
                return datos;
            }
            if(numeroIdeGerente.isEmpty() || numeroIdeGerente.isBlank()){
                JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n",  "REGISTRAR PROYECTO: número identificación gerente", JOptionPane.ERROR_MESSAGE);
            }
        } while(numeroIdeGerente.isEmpty() || numeroIdeGerente.isBlank());

            //Buscar el empleado en los gerentes y equipos de los proyectos registrados para no solicitar nuevamente sus datos
        for(Proyecto objeto : datos){
            if(objeto.getSuGerente().getNumeroIdentificacion().equals(numeroIdeGerente)){
                objGerente = objeto.getSuGerente();
            }
            for(Empleado integrante : objeto.getSuEquipo()){
                if(integrante.getNumeroIdentificacion().equals(numeroIdeGerente)){
                    objGerente = integrante;
                }
            }
        }

        if(objGerente != null){
            JOptionPane.showMessageDialog(null, "El empleado ya se encuentra registrado, se asigna como gerente del proyecto.\n" + objGerente.toString(),
                                        "REGISTRAR PROYECTO: gerente", JOptionPane.INFORMATION_MESSAGE);
        } else{
            String nombreGerente;
            String tituloGerente;
            byte aniosGerente;

                //Validación por obligatoriedad
            do{
                nombreGerente = JOptionPane.showInputDialog("Nombre completo del gerente, obligatorio");
                if(nombreGerente == null){
                    JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.", "REGISTRAR PROYECTO", JOptionPane.WARNING_MESSAGE);
                    return datos;
                }
                if(nombreGerente.isEmpty() || nombreGerente.isBlank()){
                    JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n",  "REGISTRAR PROYECTO: nombre gerente", JOptionPane.ERROR_MESSAGE);
                }
            } while(nombreGerente.isEmpty() || nombreGerente.isBlank());

                //Validación por obligatoriedad
            do{
                tituloGerente = JOptionPane.showInputDialog("Título profesional del gerente, obligatorio");
                if(tituloGerente == null){
                    JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.", "REGISTRAR PROYECTO", JOptionPane.WARNING_MESSAGE);
                    return datos;
                }
                if(tituloGerente.isEmpty() || tituloGerente.isBlank()){
                    JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n",  "REGISTRAR PROYECTO: título profesional gerente", JOptionPane.ERROR_MESSAGE);
                }
            } while(tituloGerente.isEmpty() || tituloGerente.isBlank());

                //Gestión de excepción de formato numérico y Validación para restricción de dominio
            try{
                do{
                    entrada = JOptionPane.showInputDialog("Anios de experiencia del gerente, valor entero desde 0");
                    if(entrada == null){
                        JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.", "REGISTRAR PROYECTO", JOptionPane.WARNING_MESSAGE);
                        return datos;
                    }
                    aniosGerente = Byte.parseByte(entrada);
                    if(aniosGerente<0){JOptionPane.showMessageDialog(null, "Operación fallida: entrada inválida por valor incorrecto.\n",
                                                                "REGISTRAR PROYECTO: anios de experiencia gerente", JOptionPane.ERROR_MESSAGE);}
                } while(aniosGerente<0);
            } catch(NumberFormatException nfex){
                JOptionPane.showMessageDialog(null, "Operación fallida: entrada inválida por formato incorrecto.\n"
                                            + nfex.getMessage(), "REGISTRAR PROYECTO: anios de experiencia gerente", JOptionPane.ERROR_MESSAGE);
                return datos;
            }

            objGerente = new Empleado(numeroIdeGerente,nombreGerente,tituloGerente,aniosGerente);
        }

        //----- 2. Crear el objeto Proyecto (sin equipo, estado INICIADO)

        Proyecto objProyecto;
        objProyecto = new Proyecto(codigo,titulo,fechaInicio,unidadDuracion,duracionEstimada,objCliente,objGerente);

        //----- 3. Almacenar el objeto Proyecto en la colección de proyectos

        boolean registroExitoso = datos.add(objProyecto);
        if(registroExitoso){
             JOptionPane.showMessageDialog(null, "Operación exitosa, objeto registrado.", "REGISTRAR PROYECTO", JOptionPane.INFORMATION_MESSAGE);
        } else{
             JOptionPane.showMessageDialog(null, "Operación fallida, objeto no registrado.", "REGISTRAR PROYECTO", JOptionPane.ERROR_MESSAGE);
        }

        //------ 4. Devolver la colección de proyectos
        return datos;
    }

    /*
     * ======================== Métodos: consultar proyectos ========================
    */

    //consultarTodosProyectos: conforma una cadena con la información (atributos y fecha de finalización) de cada uno de los proyectos de la colección.
    public String consultarTodosProyectos(ArrayList<Proyecto> datos){
        //----- 1. Definir e inicializar la cadena a devolver
        String resultado = "Consultar todos los proyectos\n";

        //----- 2. Manejo de la validación por restricción de lista vacía

        //2a. Si la lista está vacía: agregar mensaje indicando la situación al resultado y devolverlo
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }

        //2b. Si la lista está llena: agregar la información solicitada al resultado y devolverlo
        for(Proyecto objeto : datos){
           resultado += objeto.toString();
           resultado += "\n     Fecha de finalizacion: " + objeto.determinarFechaFinalizacion();
           resultado += "\n";
        }
        return resultado;
    }

    //consultarProyectosEnCurso: conforma una cadena con la información de cada uno de los proyectos de la colección con estado "EN CURSO".
    public String consultarProyectosEnCurso(ArrayList<Proyecto> datos){
        //----- 1. Definir e inicializar la cadena a devolver
        String resultado = "Consultar proyectos en curso\n";
        boolean encontrado = false;

        //----- 2. Manejo de la validación por restricción de lista vacía

        //2a. Si la lista está vacía: agregar mensaje indicando la situación al resultado y devolverlo
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }

        //2b. Si la lista está llena: agregar la información solicitada al resultado y devolverlo
        for(int i=0; i<datos.size();i++){
            if(datos.get(i).getEstado().equals("EN CURSO")){
               resultado += datos.get(i).toString();
               resultado += "\n     Fecha de finalizacion: " + datos.get(i).determinarFechaFinalizacion();
               resultado += "\n";
               encontrado = true;
            }
        }
        if(!encontrado){
            resultado += "No hay proyectos en curso.";
        }
        return resultado;
    }

    //consultarPrimerProyecto: conforma una cadena con la información del primer proyecto de la colección.
    public String consultarPrimerProyecto(ArrayList<Proyecto> datos){
        //----- 1. Definir e inicializar la cadena a devolver
        String resultado = "Consultar primer proyecto\n";

        //----- 2. Manejo de la validación por restricción de lista vacía

        //2a. Si la lista está vacía: agregar mensaje indicando la situación al resultado y devolverlo
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }

        //2b. Si la lista está llena: agregar la información solicitada del primer elemento al resultado y devolverlo
        Proyecto objeto = datos.getFirst();
        resultado += objeto.toString();
        resultado += "\n     Fecha de finalizacion: " + objeto.determinarFechaFinalizacion();
        resultado += "\n";
        return resultado;
    }

    //consultarUltimoProyecto: conforma una cadena con la información del último proyecto de la colección.
    public String consultarUltimoProyecto(ArrayList<Proyecto> datos){
        //----- 1. Definir e inicializar la cadena a devolver
        String resultado = "Consultar ultimo proyecto\n";

        //----- 2. Manejo de la validación por restricción de lista vacía

        //2a. Si la lista está vacía: agregar mensaje indicando la situación al resultado y devolverlo
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }

        //2b. Si la lista está llena: agregar la información solicitada del último elemento al resultado y devolverlo
        Proyecto objeto = datos.getLast();
        resultado += objeto.toString();
        resultado += "\n     Fecha de finalizacion: " + objeto.determinarFechaFinalizacion();
        resultado += "\n";
        return resultado;
    }

    //consultarProyectoDadoCodigo: conforma una cadena con la información del proyecto de la colección que cumple con el código indicado.
    public String consultarProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto){
        //----- 1. Definir e inicializar la cadena a devolver
        String resultado = "Consultar proyecto dado codigo\n";
        boolean encontrado = false;

        //----- 2. Manejo de la validación por restricción de lista vacía

        //2a. Si la lista está vacía: agregar mensaje indicando la situación al resultado y devolverlo
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }

        //2b. Si la lista está llena: agregar la información solicitada al resultado y devolverlo
        for(int i=0; i<datos.size();i++){
            if(datos.get(i).getCodigo().equals(codigoProyecto)){
               resultado += datos.get(i).toString();
               resultado += "\n     Fecha de finalizacion: " + datos.get(i).determinarFechaFinalizacion();
               resultado += "\n";
               encontrado = true;
            }
        }
        if(!encontrado){
            resultado += "No existe un proyecto con codigo " + codigoProyecto + ".";
        }
        return resultado;
    }

    //consultarGerenteProyectoDadoCodigo: conforma una cadena con el título y la información del gerente del proyecto que cumple con el código indicado.
    public String consultarGerenteProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto){
        //----- 1. Definir e inicializar la cadena a devolver
        String resultado = "Consultar gerente de un proyecto dado codigo\n";
        boolean encontrado = false;

        //----- 2. Manejo de la validación por restricción de lista vacía

        //2a. Si la lista está vacía: agregar mensaje indicando la situación al resultado y devolverlo
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }

        //2b. Si la lista está llena: agregar la información solicitada al resultado y devolverlo
        for(int i=0; i<datos.size();i++){
            if(datos.get(i).getCodigo().equals(codigoProyecto)){
               resultado += "Proyecto: " + datos.get(i).getCodigo() + " - " + datos.get(i).getTitulo() + "\n";
               resultado += "Gerente - " + datos.get(i).getSuGerente().toString();
               resultado += "\n";
               encontrado = true;
            }
        }
        if(!encontrado){
            resultado += "No existe un proyecto con codigo " + codigoProyecto + ".";
        }
        return resultado;
    }

    //consultarEquipoProyectoDadoCodigo: conforma una cadena con el título y la información de cada integrante del equipo del proyecto que cumple con el código indicado.
    public String consultarEquipoProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto){
        //----- 1. Definir e inicializar la cadena a devolver
        String resultado = "Consultar equipo de un proyecto dado codigo\n";
        boolean encontrado = false;

        //----- 2. Manejo de la validación por restricción de lista vacía

        //2a. Si la lista está vacía: agregar mensaje indicando la situación al resultado y devolverlo
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }

        //2b. Si la lista está llena: agregar la información solicitada al resultado y devolverlo
        for(int i=0; i<datos.size();i++){
            if(datos.get(i).getCodigo().equals(codigoProyecto)){
               resultado += "Proyecto: " + datos.get(i).getCodigo() + " - " + datos.get(i).getTitulo() + "\n";
               if(datos.get(i).getSuEquipo().isEmpty()){
                   resultado += "El proyecto no tiene integrantes registrados en su equipo.";
               }
               for(Empleado integrante : datos.get(i).getSuEquipo()){
                   resultado += "Integrante - " + integrante.toString();
                   resultado += "\n";
               }
               encontrado = true;
            }
        }
        if(!encontrado){
            resultado += "No existe un proyecto con codigo " + codigoProyecto + ".";
        }
        return resultado;
    }

    //consultarProyectosDadoNumIDEGerente: conforma una cadena con la información de cada uno de los proyectos cuyo gerente cumple con el número de identificación indicado.
    public String consultarProyectosDadoNumIDEGerente(ArrayList<Proyecto> datos, String numIDEGerente){
        //----- 1. Definir e inicializar la cadena a devolver
        String resultado = "Consultar proyectos dado numero de identificacion del gerente\n";
        boolean encontrado = false;

        //----- 2. Manejo de la validación por restricción de lista vacía

        //2a. Si la lista está vacía: agregar mensaje indicando la situación al resultado y devolverlo
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }

        //2b. Si la lista está llena: agregar la información solicitada al resultado y devolverlo
        for(int i=0; i<datos.size();i++){
            if(datos.get(i).getSuGerente().getNumeroIdentificacion().equals(numIDEGerente)){
               resultado += datos.get(i).toString();
               resultado += "\n     Fecha de finalizacion: " + datos.get(i).determinarFechaFinalizacion();
               resultado += "\n";
               encontrado = true;
            }
        }
        if(!encontrado){
            resultado += "No existen proyectos con gerente identificado con " + numIDEGerente + ".";
        }
        return resultado;
    }

    //consultarProyectosDadoNumIDECliente: conforma una cadena con la información de cada uno de los proyectos cuyo cliente cumple con el número de identificación indicado.
    public String consultarProyectosDadoNumIDECliente(ArrayList<Proyecto> datos, String numIDECliente){
        //----- 1. Definir e inicializar la cadena a devolver
        String resultado = "Consultar proyectos dado numero de identificacion del cliente\n";
        boolean encontrado = false;

        //----- 2. Manejo de la validación por restricción de lista vacía

        //2a. Si la lista está vacía: agregar mensaje indicando la situación al resultado y devolverlo
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }

        //2b. Si la lista está llena: agregar la información solicitada al resultado y devolverlo
        for(int i=0; i<datos.size();i++){
            if(datos.get(i).getSuCliente().getNumeroIdentificacion().equals(numIDECliente)){
               resultado += datos.get(i).toString();
               resultado += "\n     Fecha de finalizacion: " + datos.get(i).determinarFechaFinalizacion();
               resultado += "\n";
               encontrado = true;
            }
        }
        if(!encontrado){
            resultado += "No existen proyectos con cliente identificado con " + numIDECliente + ".";
        }
        return resultado;
    }

    //consultarProyectosDadoTipoCliente: conforma una cadena con la información de cada uno de los proyectos cuyo cliente es del tipo indicado (Empresa, PersonaNatural).
    public String consultarProyectosDadoTipoCliente(ArrayList<Proyecto> datos, String tipoCliente){
        //----- 1. Definir e inicializar la cadena a devolver
        String resultado = "Consultar proyectos dado tipo de cliente\n";
        boolean encontrado = false;

        //----- 2. Manejo de la validación por restricción de lista vacía

        //2a. Si la lista está vacía: agregar mensaje indicando la situación al resultado y devolverlo
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }

        //2b. Si la lista está llena: agregar la información solicitada al resultado y devolverlo
        for(int i=0; i<datos.size();i++){
            if((tipoCliente.equals("Empresa") && datos.get(i).getSuCliente() instanceof Empresa)
                    || (tipoCliente.equals("PersonaNatural") && datos.get(i).getSuCliente() instanceof PersonaNatural)){
               resultado += datos.get(i).toString();
               resultado += "\n     Fecha de finalizacion: " + datos.get(i).determinarFechaFinalizacion();
               resultado += "\n";
               encontrado = true;
            }
        }
        if(!encontrado){
            resultado += "No existen proyectos con cliente de tipo " + tipoCliente + ".";
        }
        return resultado;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new UsaProyecto().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
