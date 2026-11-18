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
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

        /*
         * Invocaciones iniciales: cargar datos de prueba (solo si la colección de proyectos está vacía)
        */
        if(losProyectos.isEmpty()){
            //Clientes
            Contacto objContacto1 = new Contacto("Ana Maria Paz", "Gerente comercial", "3001234567", "ana.paz@solucionesvalle.com");
            Contacto objContacto2 = new Contacto("Jorge Ivan Mora", "Director administrativo", "3157654321", "jorge.mora@constructorapacifico.com");
            Cliente objCliente1 = new Empresa("900123456", "Soluciones del Valle SAS", "Cali", objContacto1);
            Cliente objCliente2 = new Empresa("800765432", "Constructora Pacifico Ltda", "Buenaventura", objContacto2);
            Cliente objCliente3 = new PersonaNatural("31456789", "Laura Gomez Salazar", "Palmira", "3109876543", "laura.gomez@correo.com");

            //Empleados (gerentes e integrantes)
            Empleado objGerente1 = new Empleado("1107123456", "Carlos Andres Ruiz", "Ingeniero de sistemas", (byte)8);
            Empleado objGerente2 = new Empleado("66778899", "Diana Marcela Rios", "Administradora de empresas", (byte)12);
            Empleado objEmpleado1 = new Empleado("1144556677", "Maria Fernanda Lopez", "Disenadora grafica", (byte)3);
            Empleado objEmpleado2 = new Empleado("1130987654", "Pedro Antonio Perez", "Ingeniero industrial", (byte)5);
            Empleado objEmpleado3 = new Empleado("1061234567", "Sofia Valencia Arango", "Ingeniera de software", (byte)2);
            Empleado objEmpleado4 = new Empleado("94321456", "Andres Felipe Castano", "Contador publico", (byte)10);

            //Proyecto 1: con equipo (constructor con equipo), finalizado
            ArrayList<Empleado> equipo1 = new ArrayList<>();
            equipo1.add(objEmpleado1);
            equipo1.add(objEmpleado2);
            Proyecto objProyecto1 = new Proyecto("2025-001", "Implementacion de sistema de facturacion", LocalDate.parse("2025-01-20"), "anios", (byte)1, objCliente1, objGerente1, equipo1);
            objProyecto1.setEstado("FINALIZADO");
            losProyectos.add(objProyecto1);

            //Proyecto 2: sin equipo inicial (constructor sin equipo), se agrega un integrante, en curso
            Proyecto objProyecto2 = new Proyecto("2026-001", "Sistema de inventarios para bodega", LocalDate.parse("2026-02-15"), "meses", (byte)3, objCliente1, objGerente1);
            objProyecto2.agregarIntegrante(objEmpleado3);
            objProyecto2.setEstado("EN CURSO");
            losProyectos.add(objProyecto2);

            //Proyecto 3: sin equipo, estado por defecto (INICIADO)
            Proyecto objProyecto3 = new Proyecto("2026-002", "Portal web de atencion al ciudadano", LocalDate.parse("2026-03-01"), "semanas", (byte)2, objCliente3, objGerente2);
            losProyectos.add(objProyecto3);

            //Proyecto 4: con equipo, suspendido
            ArrayList<Empleado> equipo4 = new ArrayList<>();
            equipo4.add(objEmpleado4);
            equipo4.add(objEmpleado2);
            Proyecto objProyecto4 = new Proyecto("2026-003", "Migracion de plataforma contable", LocalDate.parse("2026-01-10"), "meses", (byte)13, objCliente2, objGerente2, equipo4);
            objProyecto4.setEstado("SUSPENDIDO");
            losProyectos.add(objProyecto4);
        }
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

    /*
     * ======================== Métodos: gestionar equipo y estado de proyectos ========================
    */

    //registrarIntegranteProyectoDadoCodigo: solicita los datos de un integrante (empleado) y lo agrega al equipo del proyecto que cumple con el código indicado.
    public String registrarIntegranteProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto){
        //----- 1. Definir e inicializar la cadena a devolver
        String resultado = "Registrar integrante de un proyecto dado codigo\n";
        Proyecto objProyecto = null;
        String numeroIdeIntegrante;
        Empleado objIntegrante = null;
        boolean repetido;
        String entrada;//texto digitado en la ventana, se verifica que no se haya cancelado antes de convertirlo

        //----- 2. Manejo de la validación por restricción de lista vacía

        //2a. Si la lista está vacía: agregar mensaje indicando la situación al resultado y devolverlo
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }

        //2b. Si la lista está llena: buscar el proyecto que cumple con el código indicado
        for(Proyecto objeto : datos){
            if(objeto.getCodigo().equals(codigoProyecto)){
                objProyecto = objeto;
            }
        }
        if(objProyecto == null){
            resultado += "No existe un proyecto con codigo " + codigoProyecto + ".";
            return resultado;
        }

        //----- 3. Obtener los datos del integrante (empleado)

            //Validación por obligatoriedad y por integrante repetido en el equipo
        do{
            numeroIdeIntegrante = JOptionPane.showInputDialog("Número de identificación del integrante, obligatorio");
            if(numeroIdeIntegrante == null){
                resultado += "Operacion cancelada por el usuario.";
                return resultado;
            }
            repetido = false;
            for(Empleado integrante : objProyecto.getSuEquipo()){
                if(integrante.getNumeroIdentificacion().equals(numeroIdeIntegrante)){
                    repetido = true;
                }
            }
            if(numeroIdeIntegrante.isEmpty() || numeroIdeIntegrante.isBlank()){
                JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n",  "REGISTRAR INTEGRANTE: número identificación", JOptionPane.ERROR_MESSAGE);
            } else if(repetido){
                JOptionPane.showMessageDialog(null, "Entrada inválida: el empleado ya es integrante del equipo del proyecto.\n",  "REGISTRAR INTEGRANTE: número identificación", JOptionPane.ERROR_MESSAGE);
            }
        } while(numeroIdeIntegrante.isEmpty() || numeroIdeIntegrante.isBlank() || repetido);

            //Buscar el empleado en los gerentes y equipos de los proyectos registrados para no solicitar nuevamente sus datos
        for(Proyecto objeto : datos){
            if(objeto.getSuGerente().getNumeroIdentificacion().equals(numeroIdeIntegrante)){
                objIntegrante = objeto.getSuGerente();
            }
            for(Empleado integrante : objeto.getSuEquipo()){
                if(integrante.getNumeroIdentificacion().equals(numeroIdeIntegrante)){
                    objIntegrante = integrante;
                }
            }
        }

        if(objIntegrante != null){
            JOptionPane.showMessageDialog(null, "El empleado ya se encuentra registrado, se asigna como integrante del proyecto.\n" + objIntegrante.toString(),
                                        "REGISTRAR INTEGRANTE", JOptionPane.INFORMATION_MESSAGE);
        } else{
            String nombreIntegrante;
            String tituloIntegrante;
            byte aniosIntegrante;

                //Validación por obligatoriedad
            do{
                nombreIntegrante = JOptionPane.showInputDialog("Nombre completo del integrante, obligatorio");
                if(nombreIntegrante == null){
                    resultado += "Operacion cancelada por el usuario.";
                    return resultado;
                }
                if(nombreIntegrante.isEmpty() || nombreIntegrante.isBlank()){
                    JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n",  "REGISTRAR INTEGRANTE: nombre", JOptionPane.ERROR_MESSAGE);
                }
            } while(nombreIntegrante.isEmpty() || nombreIntegrante.isBlank());

                //Validación por obligatoriedad
            do{
                tituloIntegrante = JOptionPane.showInputDialog("Título profesional del integrante, obligatorio");
                if(tituloIntegrante == null){
                    resultado += "Operacion cancelada por el usuario.";
                    return resultado;
                }
                if(tituloIntegrante.isEmpty() || tituloIntegrante.isBlank()){
                    JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n",  "REGISTRAR INTEGRANTE: título profesional", JOptionPane.ERROR_MESSAGE);
                }
            } while(tituloIntegrante.isEmpty() || tituloIntegrante.isBlank());

                //Gestión de excepción de formato numérico y Validación para restricción de dominio
            try{
                do{
                    entrada = JOptionPane.showInputDialog("Anios de experiencia del integrante, valor entero desde 0");
                    if(entrada == null){
                        resultado += "Operacion cancelada por el usuario.";
                        return resultado;
                    }
                    aniosIntegrante = Byte.parseByte(entrada);
                    if(aniosIntegrante<0){JOptionPane.showMessageDialog(null, "Operación fallida: entrada inválida por valor incorrecto.\n",
                                                                "REGISTRAR INTEGRANTE: anios de experiencia", JOptionPane.ERROR_MESSAGE);}
                } while(aniosIntegrante<0);
            } catch(NumberFormatException nfex){
                JOptionPane.showMessageDialog(null, "Operación fallida: entrada inválida por formato incorrecto.\n"
                                            + nfex.getMessage(), "REGISTRAR INTEGRANTE: anios de experiencia", JOptionPane.ERROR_MESSAGE);
                resultado += "Operacion fallida: integrante no registrado.";
                return resultado;
            }

            objIntegrante = new Empleado(numeroIdeIntegrante,nombreIntegrante,tituloIntegrante,aniosIntegrante);
        }

        //----- 4. Agregar el integrante al equipo del proyecto y devolver el resultado
        objProyecto.agregarIntegrante(objIntegrante);
        resultado += "Operacion exitosa, integrante registrado en el proyecto " + codigoProyecto + ".\n";
        resultado += "Integrante - " + objIntegrante.toString();
        return resultado;
    }

    //registrarEquipoProyectoDadoCodigo: solicita la cantidad y los datos de los integrantes y actualiza el equipo del proyecto que cumple con el código indicado.
    public String registrarEquipoProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto){
        //----- 1. Definir e inicializar la cadena a devolver
        String resultado = "Registrar equipo de un proyecto dado codigo\n";
        Proyecto objProyecto = null;
        int cantidadIntegrantes;
        ArrayList<Empleado> equipoNuevo = new ArrayList<>();
        String entrada;//texto digitado en la ventana, se verifica que no se haya cancelado antes de convertirlo

        //----- 2. Manejo de la validación por restricción de lista vacía

        //2a. Si la lista está vacía: agregar mensaje indicando la situación al resultado y devolverlo
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }

        //2b. Si la lista está llena: buscar el proyecto que cumple con el código indicado
        for(Proyecto objeto : datos){
            if(objeto.getCodigo().equals(codigoProyecto)){
                objProyecto = objeto;
            }
        }
        if(objProyecto == null){
            resultado += "No existe un proyecto con codigo " + codigoProyecto + ".";
            return resultado;
        }
        if(!objProyecto.getSuEquipo().isEmpty()){
            JOptionPane.showMessageDialog(null, "El proyecto ya tiene " + objProyecto.getSuEquipo().size() + " integrantes.\n"
                                        + "El equipo que se registre reemplazará el equipo actual.", "REGISTRAR EQUIPO", JOptionPane.WARNING_MESSAGE);
        }

        //----- 3. Solicitar la cantidad de integrantes a registrar

            //Gestión de excepción de formato numérico y Validación para restricción de dominio
        try{
            do{
                entrada = JOptionPane.showInputDialog("Cantidad de integrantes del equipo, valor entero desde 1");
                if(entrada == null){
                    resultado += "Operacion cancelada por el usuario.";
                    return resultado;
                }
                cantidadIntegrantes = Integer.parseInt(entrada);
                if(cantidadIntegrantes<1){JOptionPane.showMessageDialog(null, "Operación fallida: entrada inválida por valor incorrecto.\n",
                                                            "REGISTRAR EQUIPO: cantidad de integrantes", JOptionPane.ERROR_MESSAGE);}
            } while(cantidadIntegrantes<1);
        } catch(NumberFormatException nfex){
            JOptionPane.showMessageDialog(null, "Operación fallida: entrada inválida por formato incorrecto.\n"
                                        + nfex.getMessage(), "REGISTRAR EQUIPO: cantidad de integrantes", JOptionPane.ERROR_MESSAGE);
            resultado += "Operacion fallida: equipo no registrado.";
            return resultado;
        }

        //----- 4. Ciclo para llenar la lista de integrantes (equipo)
        for(int i=1; i<=cantidadIntegrantes; i++){
            String numeroIdeIntegrante;
            Empleado objIntegrante = null;
            boolean repetido;

                //Validación por obligatoriedad y por integrante repetido en el equipo nuevo
            do{
                numeroIdeIntegrante = JOptionPane.showInputDialog("Integrante " + i + " de " + cantidadIntegrantes + "\n"
                                                                + "Número de identificación del integrante, obligatorio");
                if(numeroIdeIntegrante == null){
                    resultado += "Operacion cancelada por el usuario.";
                    return resultado;
                }
                repetido = false;
                for(Empleado integrante : equipoNuevo){
                    if(integrante.getNumeroIdentificacion().equals(numeroIdeIntegrante)){
                        repetido = true;
                    }
                }
                if(numeroIdeIntegrante.isEmpty() || numeroIdeIntegrante.isBlank()){
                    JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n",  "REGISTRAR EQUIPO: número identificación", JOptionPane.ERROR_MESSAGE);
                } else if(repetido){
                    JOptionPane.showMessageDialog(null, "Entrada inválida: el empleado ya fue agregado al equipo.\n",  "REGISTRAR EQUIPO: número identificación", JOptionPane.ERROR_MESSAGE);
                }
            } while(numeroIdeIntegrante.isEmpty() || numeroIdeIntegrante.isBlank() || repetido);

                //Buscar el empleado en los gerentes y equipos de los proyectos registrados para no solicitar nuevamente sus datos
            for(Proyecto objeto : datos){
                if(objeto.getSuGerente().getNumeroIdentificacion().equals(numeroIdeIntegrante)){
                    objIntegrante = objeto.getSuGerente();
                }
                for(Empleado integrante : objeto.getSuEquipo()){
                    if(integrante.getNumeroIdentificacion().equals(numeroIdeIntegrante)){
                        objIntegrante = integrante;
                    }
                }
            }

            if(objIntegrante != null){
                JOptionPane.showMessageDialog(null, "El empleado ya se encuentra registrado, se asigna como integrante del equipo.\n" + objIntegrante.toString(),
                                            "REGISTRAR EQUIPO", JOptionPane.INFORMATION_MESSAGE);
            } else{
                String nombreIntegrante;
                String tituloIntegrante;
                byte aniosIntegrante;

                    //Validación por obligatoriedad
                do{
                    nombreIntegrante = JOptionPane.showInputDialog("Integrante " + i + " de " + cantidadIntegrantes + "\n"
                                                                + "Nombre completo del integrante, obligatorio");
                    if(nombreIntegrante == null){
                        resultado += "Operacion cancelada por el usuario.";
                        return resultado;
                    }
                    if(nombreIntegrante.isEmpty() || nombreIntegrante.isBlank()){
                        JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n",  "REGISTRAR EQUIPO: nombre", JOptionPane.ERROR_MESSAGE);
                    }
                } while(nombreIntegrante.isEmpty() || nombreIntegrante.isBlank());

                    //Validación por obligatoriedad
                do{
                    tituloIntegrante = JOptionPane.showInputDialog("Integrante " + i + " de " + cantidadIntegrantes + "\n"
                                                                + "Título profesional del integrante, obligatorio");
                    if(tituloIntegrante == null){
                        resultado += "Operacion cancelada por el usuario.";
                        return resultado;
                    }
                    if(tituloIntegrante.isEmpty() || tituloIntegrante.isBlank()){
                        JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n",  "REGISTRAR EQUIPO: título profesional", JOptionPane.ERROR_MESSAGE);
                    }
                } while(tituloIntegrante.isEmpty() || tituloIntegrante.isBlank());

                    //Gestión de excepción de formato numérico y Validación para restricción de dominio
                try{
                    do{
                        entrada = JOptionPane.showInputDialog("Integrante " + i + " de " + cantidadIntegrantes + "\n"
                                                            + "Anios de experiencia del integrante, valor entero desde 0");
                        if(entrada == null){
                            resultado += "Operacion cancelada por el usuario.";
                            return resultado;
                        }
                        aniosIntegrante = Byte.parseByte(entrada);
                        if(aniosIntegrante<0){JOptionPane.showMessageDialog(null, "Operación fallida: entrada inválida por valor incorrecto.\n",
                                                                    "REGISTRAR EQUIPO: anios de experiencia", JOptionPane.ERROR_MESSAGE);}
                    } while(aniosIntegrante<0);
                } catch(NumberFormatException nfex){
                    JOptionPane.showMessageDialog(null, "Operación fallida: entrada inválida por formato incorrecto.\n"
                                                + nfex.getMessage(), "REGISTRAR EQUIPO: anios de experiencia", JOptionPane.ERROR_MESSAGE);
                    resultado += "Operacion fallida: equipo no registrado.";
                    return resultado;
                }

                objIntegrante = new Empleado(numeroIdeIntegrante,nombreIntegrante,tituloIntegrante,aniosIntegrante);
            }

            //Actualizar la lista de integrantes
            equipoNuevo.add(objIntegrante);
        }

        //----- 5. Actualizar el equipo del proyecto y devolver el resultado
        objProyecto.setSuEquipo(equipoNuevo);
        resultado += "Operacion exitosa, equipo registrado en el proyecto " + codigoProyecto + " con " + equipoNuevo.size() + " integrantes.\n";
        for(Empleado integrante : equipoNuevo){
            resultado += "Integrante - " + integrante.toString();
            resultado += "\n";
        }
        return resultado;
    }

    //actualizarEstadoProyectoDadoCodigo: solicita el nuevo estado y lo actualiza en el proyecto que cumple con el código indicado.
    public String actualizarEstadoProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto){
        //----- 1. Definir e inicializar la cadena a devolver
        String resultado = "Actualizar estado de un proyecto dado codigo\n";
        Proyecto objProyecto = null;
        int opcionEstado;
        String estadoAnterior;
        String estadoNuevo;
        String entrada;//texto digitado en la ventana, se verifica que no se haya cancelado antes de convertirlo

        //----- 2. Manejo de la validación por restricción de lista vacía

        //2a. Si la lista está vacía: agregar mensaje indicando la situación al resultado y devolverlo
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }

        //2b. Si la lista está llena: buscar el proyecto que cumple con el código indicado
        for(Proyecto objeto : datos){
            if(objeto.getCodigo().equals(codigoProyecto)){
                objProyecto = objeto;
            }
        }
        if(objProyecto == null){
            resultado += "No existe un proyecto con codigo " + codigoProyecto + ".";
            return resultado;
        }

        //----- 3. Solicitar el nuevo estado

            //Gestión de excepción de formato numérico y Validación para restricción de dominio
        try{
            do{
                entrada = JOptionPane.showInputDialog("Estado actual del proyecto " + codigoProyecto + ": " + objProyecto.getEstado() + "\n"
                                                    + "\nNuevo estado\n"
                                                    + "1. INICIADO\n"
                                                    + "2. EN CURSO\n"
                                                    + "3. CANCELADO\n"
                                                    + "4. FINALIZADO\n"
                                                    + "5. SUSPENDIDO\n"
                                                    + "6. APLAZADO\n"
                                                    + "\nSeleccione la opción deseada\n");
                if(entrada == null){
                    resultado += "Operacion cancelada por el usuario.";
                    return resultado;
                }
                opcionEstado = Integer.parseInt(entrada);
                if(opcionEstado<1 || opcionEstado>6){JOptionPane.showMessageDialog(null, "Operación fallida: entrada inválida por valor incorrecto.\n",
                                                            "ACTUALIZAR ESTADO: estado", JOptionPane.ERROR_MESSAGE);}
            } while(opcionEstado<1 || opcionEstado>6);
        } catch(NumberFormatException nfex){
            JOptionPane.showMessageDialog(null, "Operación fallida: entrada inválida por formato incorrecto.\n"
                                        + nfex.getMessage(), "ACTUALIZAR ESTADO: estado", JOptionPane.ERROR_MESSAGE);
            resultado += "Operacion fallida: estado no actualizado.";
            return resultado;
        }
        if(opcionEstado==1){estadoNuevo = "INICIADO";}
        else if(opcionEstado==2){estadoNuevo = "EN CURSO";}
        else if(opcionEstado==3){estadoNuevo = "CANCELADO";}
        else if(opcionEstado==4){estadoNuevo = "FINALIZADO";}
        else if(opcionEstado==5){estadoNuevo = "SUSPENDIDO";}
        else{estadoNuevo = "APLAZADO";}

        //----- 4. Actualizar el estado del proyecto y devolver el resultado
        estadoAnterior = objProyecto.getEstado();
        objProyecto.setEstado(estadoNuevo);
        resultado += "Operacion exitosa, estado del proyecto " + codigoProyecto + " actualizado de " + estadoAnterior + " a " + estadoNuevo + ".";
        return resultado;
    }

    //eliminarIntegranteProyectoDadoCodigo: solicita el número de identificación de un integrante y lo elimina del equipo del proyecto que cumple con el código indicado.
    public String eliminarIntegranteProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto){
        //----- 1. Definir e inicializar la cadena a devolver
        String resultado = "Eliminar integrante de un proyecto dado codigo\n";
        Proyecto objProyecto = null;
        String numeroIdeIntegrante;
        Empleado integranteEliminado;

        //----- 2. Manejo de la validación por restricción de lista vacía

        //2a. Si la lista está vacía: agregar mensaje indicando la situación al resultado y devolverlo
        if(datos.isEmpty()){
            resultado += "No hay proyectos registrados.";
            return resultado;
        }

        //2b. Si la lista está llena: buscar el proyecto que cumple con el código indicado
        for(Proyecto objeto : datos){
            if(objeto.getCodigo().equals(codigoProyecto)){
                objProyecto = objeto;
            }
        }
        if(objProyecto == null){
            resultado += "No existe un proyecto con codigo " + codigoProyecto + ".";
            return resultado;
        }
        if(objProyecto.getSuEquipo().isEmpty()){
            resultado += "El proyecto no tiene integrantes registrados en su equipo.";
            return resultado;
        }

        //----- 3. Solicitar el número de identificación del integrante

            //Validación por obligatoriedad
        do{
            numeroIdeIntegrante = JOptionPane.showInputDialog("Número de identificación del integrante a eliminar, obligatorio");
            if(numeroIdeIntegrante == null){
                resultado += "Operacion cancelada por el usuario.";
                return resultado;
            }
            if(numeroIdeIntegrante.isEmpty() || numeroIdeIntegrante.isBlank()){
                JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n",  "ELIMINAR INTEGRANTE: número identificación", JOptionPane.ERROR_MESSAGE);
            }
        } while(numeroIdeIntegrante.isEmpty() || numeroIdeIntegrante.isBlank());

        //----- 4. Eliminar el integrante del equipo del proyecto y devolver el resultado
        integranteEliminado = objProyecto.eliminarIntegrante(numeroIdeIntegrante);
        if(integranteEliminado == null){
            resultado += "No existe un integrante con numero de identificacion " + numeroIdeIntegrante + " en el equipo del proyecto " + codigoProyecto + ".";
        } else{
            resultado += "Operacion exitosa, integrante eliminado del proyecto " + codigoProyecto + ".\n";
            resultado += "Integrante - " + integranteEliminado.toString();
        }
        return resultado;
    }

    /*
     * ======================== Métodos: gestión de archivos de objetos ========================
    */

    //generarArchivoDeObjetosProyectos: recorre la colección y guarda cada uno de los proyectos en el archivo "datosProyectos".
    public void generarArchivoDeObjetosProyectos(ArrayList<Proyecto> datos){
        String nombreArchivo = "datosProyectos.obj";
        ObjectOutputStream salida = null;

        //Manejo de la validación por restricción de lista vacía
        if(datos.isEmpty()){
            JOptionPane.showMessageDialog(null, "Operación fallida, lista vacía. No hay proyectos registrados.", "GENERACIÓN DE ARCHIVO DE PROYECTOS", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try{
            salida = new ObjectOutputStream(new FileOutputStream(nombreArchivo));
            for(Proyecto objeto : datos){
                salida.writeObject(objeto);
            }
            JOptionPane.showMessageDialog(null, "Operación exitosa, archivo generado correctamente.\n"
                                        + "Proyectos guardados: " + datos.size(), "GENERACIÓN DE ARCHIVO DE PROYECTOS", JOptionPane.INFORMATION_MESSAGE);
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Operación fallida: error almacenando datos en archivo.\n "
                                            + "Revisar serialización de clases.", "GENERACIÓN DE ARCHIVO DE PROYECTOS", JOptionPane.ERROR_MESSAGE);
        } finally{
            try{
                if(salida != null){salida.close();}
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Operación fallida: error cerrando archivo.", "GENERACIÓN DE ARCHIVO DE PROYECTOS", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //leerArchivoDeObjetosProyectos: recorre el archivo "datosProyectos" y guarda cada registro en la lista que devuelve.
    public ArrayList<Proyecto> leerArchivoDeObjetosProyectos(){
        String nombreArchivo = "datosProyectos.obj";
        ObjectInputStream entrada = null;
        ArrayList<Proyecto> datos = new ArrayList<>();
        boolean finArchivo = false;

        try{
            entrada = new ObjectInputStream(new FileInputStream(nombreArchivo));
            //Recorrer el archivo hasta su fin: al no haber más objetos se genera EOFException
            while(!finArchivo){
                try{
                    datos.add((Proyecto) entrada.readObject());
                } catch(EOFException ex){
                    finArchivo = true;
                }
            }
            JOptionPane.showMessageDialog(null, "Operación exitosa, archivo leído correctamente.\n"
                                        + "Proyectos recuperados: " + datos.size(), "LECTURA DE ARCHIVO DE PROYECTOS", JOptionPane.INFORMATION_MESSAGE);
        } catch(FileNotFoundException ex){
            JOptionPane.showMessageDialog(null,"Operación fallida: no existe el archivo " + nombreArchivo + ".\n"
                                            + "Primero se debe generar el archivo de proyectos.", "LECTURA DE ARCHIVO DE PROYECTOS", JOptionPane.ERROR_MESSAGE);
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Operación fallida: error recuperando datos desde archivo.", "LECTURA DE ARCHIVO DE PROYECTOS", JOptionPane.ERROR_MESSAGE);
        } finally{
            try{
                if(entrada != null){entrada.close();}
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Operación fallida: error cerrando archivo.", "LECTURA DE ARCHIVO DE PROYECTOS", JOptionPane.ERROR_MESSAGE);
            }
        }
        return datos;
    }

    //generarArchivoDeObjetosClientes: recorre la colección de proyectos y guarda el cliente de cada uno de ellos en el archivo "datosClientes".
    public void generarArchivoDeObjetosClientes(ArrayList<Proyecto> datos){
        String nombreArchivo = "datosClientes.obj";
        ObjectOutputStream salida = null;

        //Manejo de la validación por restricción de lista vacía
        if(datos.isEmpty()){
            JOptionPane.showMessageDialog(null, "Operación fallida, lista vacía. No hay proyectos registrados.", "GENERACIÓN DE ARCHIVO DE CLIENTES", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try{
            salida = new ObjectOutputStream(new FileOutputStream(nombreArchivo));
            for(Proyecto objeto : datos){
                salida.writeObject(objeto.getSuCliente());
            }
            JOptionPane.showMessageDialog(null, "Operación exitosa, archivo generado correctamente.\n"
                                        + "Clientes guardados (uno por proyecto): " + datos.size(), "GENERACIÓN DE ARCHIVO DE CLIENTES", JOptionPane.INFORMATION_MESSAGE);
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Operación fallida: error almacenando datos en archivo.\n "
                                            + "Revisar serialización de clases.", "GENERACIÓN DE ARCHIVO DE CLIENTES", JOptionPane.ERROR_MESSAGE);
        } finally{
            try{
                if(salida != null){salida.close();}
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Operación fallida: error cerrando archivo.", "GENERACIÓN DE ARCHIVO DE CLIENTES", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //leerArchivoDeObjetosClientes: recorre el archivo "datosClientes" y guarda cada registro en la lista que devuelve, verificando que no se repita.
    public ArrayList<Cliente> leerArchivoDeObjetosClientes(){
        String nombreArchivo = "datosClientes.obj";
        ObjectInputStream entrada = null;
        ArrayList<Cliente> datos = new ArrayList<>();
        boolean finArchivo = false;

        try{
            entrada = new ObjectInputStream(new FileInputStream(nombreArchivo));
            //Recorrer el archivo hasta su fin: al no haber más objetos se genera EOFException
            while(!finArchivo){
                try{
                    Cliente objCliente = (Cliente) entrada.readObject();
                    //Verificar que el cliente no se encuentre ya en la lista
                    boolean repetido = false;
                    for(Cliente objeto : datos){
                        if(objeto.getNumeroIdentificacion().equals(objCliente.getNumeroIdentificacion())){
                            repetido = true;
                        }
                    }
                    if(!repetido){
                        datos.add(objCliente);
                    }
                } catch(EOFException ex){
                    finArchivo = true;
                }
            }
            JOptionPane.showMessageDialog(null, "Operación exitosa, archivo leído correctamente.\n"
                                        + "Clientes recuperados (sin repetir): " + datos.size(), "LECTURA DE ARCHIVO DE CLIENTES", JOptionPane.INFORMATION_MESSAGE);
        } catch(FileNotFoundException ex){
            JOptionPane.showMessageDialog(null,"Operación fallida: no existe el archivo " + nombreArchivo + ".\n"
                                            + "Primero se debe generar el archivo de clientes.", "LECTURA DE ARCHIVO DE CLIENTES", JOptionPane.ERROR_MESSAGE);
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Operación fallida: error recuperando datos desde archivo.", "LECTURA DE ARCHIVO DE CLIENTES", JOptionPane.ERROR_MESSAGE);
        } finally{
            try{
                if(entrada != null){entrada.close();}
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Operación fallida: error cerrando archivo.", "LECTURA DE ARCHIVO DE CLIENTES", JOptionPane.ERROR_MESSAGE);
            }
        }
        return datos;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonRegistrarProyecto = new javax.swing.JButton();
        jButtonConsultarTodos = new javax.swing.JButton();
        jButtonConsultarEnCurso = new javax.swing.JButton();
        jButtonConsultarPrimero = new javax.swing.JButton();
        jButtonConsultarUltimo = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldCodigo = new javax.swing.JTextField();
        jButtonConsultarProyecto = new javax.swing.JButton();
        jButtonConsultarGerente = new javax.swing.JButton();
        jButtonConsultarEquipo = new javax.swing.JButton();
        jButtonRegistrarIntegrante = new javax.swing.JButton();
        jButtonRegistrarEquipo = new javax.swing.JButton();
        jButtonEliminarIntegrante = new javax.swing.JButton();
        jButtonActualizarEstado = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldNumIdeCliente = new javax.swing.JTextField();
        jButtonConsultarPorCliente = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxTipoCliente = new javax.swing.JComboBox<>();
        jButtonConsultarPorTipoCliente = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldNumIdeGerente = new javax.swing.JTextField();
        jButtonConsultarPorGerente = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButtonGenerarArchivoProyectos = new javax.swing.JButton();
        jButtonLeerArchivoProyectos = new javax.swing.JButton();
        jButtonGenerarArchivoClientes = new javax.swing.JButton();
        jButtonLeerArchivoClientes = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaResultados = new javax.swing.JTextArea();
        jButtonSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestión de proyectos");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Proyectos"));

        jButtonRegistrarProyecto.setText("Registrar proyecto");
        jButtonRegistrarProyecto.addActionListener(this::jButtonRegistrarProyectoActionPerformed);

        jButtonConsultarTodos.setText("Consultar todos");
        jButtonConsultarTodos.addActionListener(this::jButtonConsultarTodosActionPerformed);

        jButtonConsultarEnCurso.setText("Proyectos en curso");
        jButtonConsultarEnCurso.addActionListener(this::jButtonConsultarEnCursoActionPerformed);

        jButtonConsultarPrimero.setText("Primer proyecto");
        jButtonConsultarPrimero.addActionListener(this::jButtonConsultarPrimeroActionPerformed);

        jButtonConsultarUltimo.setText("Último proyecto");
        jButtonConsultarUltimo.addActionListener(this::jButtonConsultarUltimoActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonRegistrarProyecto)
                    .addComponent(jButtonConsultarTodos)
                    .addComponent(jButtonConsultarEnCurso)
                    .addComponent(jButtonConsultarPrimero)
                    .addComponent(jButtonConsultarUltimo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jButtonRegistrarProyecto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonConsultarTodos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonConsultarEnCurso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonConsultarPrimero)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonConsultarUltimo)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Operaciones por código de proyecto"));

        jLabel1.setText("Código del proyecto (ej. 2026-001)");

        jButtonConsultarProyecto.setText("Consultar proyecto");
        jButtonConsultarProyecto.addActionListener(this::jButtonConsultarProyectoActionPerformed);

        jButtonConsultarGerente.setText("Consultar gerente");
        jButtonConsultarGerente.addActionListener(this::jButtonConsultarGerenteActionPerformed);

        jButtonConsultarEquipo.setText("Consultar equipo");
        jButtonConsultarEquipo.addActionListener(this::jButtonConsultarEquipoActionPerformed);

        jButtonRegistrarIntegrante.setText("Registrar integrante");
        jButtonRegistrarIntegrante.addActionListener(this::jButtonRegistrarIntegranteActionPerformed);

        jButtonRegistrarEquipo.setText("Registrar equipo");
        jButtonRegistrarEquipo.addActionListener(this::jButtonRegistrarEquipoActionPerformed);

        jButtonEliminarIntegrante.setText("Eliminar integrante");
        jButtonEliminarIntegrante.addActionListener(this::jButtonEliminarIntegranteActionPerformed);

        jButtonActualizarEstado.setText("Actualizar estado");
        jButtonActualizarEstado.addActionListener(this::jButtonActualizarEstadoActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldCodigo)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jButtonConsultarProyecto)
                            .addComponent(jButtonConsultarGerente)
                            .addComponent(jButtonConsultarEquipo)
                            .addComponent(jButtonRegistrarIntegrante)
                            .addComponent(jButtonRegistrarEquipo)
                            .addComponent(jButtonEliminarIntegrante)
                            .addComponent(jButtonActualizarEstado))
                        .addGap(0, 15, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonConsultarProyecto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonConsultarGerente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonConsultarEquipo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonRegistrarIntegrante)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonRegistrarEquipo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonEliminarIntegrante)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonActualizarEstado)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Consultas por gerente y cliente"));

        jLabel2.setText("Número de identificación del cliente");

        jButtonConsultarPorCliente.setText("Proyectos del cliente");
        jButtonConsultarPorCliente.addActionListener(this::jButtonConsultarPorClienteActionPerformed);

        jLabel4.setText("Tipo de cliente");

        jComboBoxTipoCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Empresa", "PersonaNatural" }));

        jButtonConsultarPorTipoCliente.setText("Proyectos por tipo de cliente");
        jButtonConsultarPorTipoCliente.addActionListener(this::jButtonConsultarPorTipoClienteActionPerformed);

        jLabel3.setText("Número de identificación del gerente");

        jButtonConsultarPorGerente.setText("Proyectos del gerente");
        jButtonConsultarPorGerente.addActionListener(this::jButtonConsultarPorGerenteActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonConsultarPorCliente)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxTipoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonConsultarPorTipoCliente)
                    .addComponent(jLabel3)
                    .addComponent(jButtonConsultarPorGerente)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTextFieldNumIdeCliente, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextFieldNumIdeGerente, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNumIdeGerente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonConsultarPorGerente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNumIdeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonConsultarPorCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxTipoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonConsultarPorTipoCliente)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Archivos"));

        jButtonGenerarArchivoProyectos.setText("Generar archivo de proyectos");
        jButtonGenerarArchivoProyectos.addActionListener(this::jButtonGenerarArchivoProyectosActionPerformed);

        jButtonLeerArchivoProyectos.setText("Leer archivo de proyectos");
        jButtonLeerArchivoProyectos.addActionListener(this::jButtonLeerArchivoProyectosActionPerformed);

        jButtonGenerarArchivoClientes.setText("Generar archivo de clientes");
        jButtonGenerarArchivoClientes.addActionListener(this::jButtonGenerarArchivoClientesActionPerformed);

        jButtonLeerArchivoClientes.setText("Leer archivo de clientes");
        jButtonLeerArchivoClientes.addActionListener(this::jButtonLeerArchivoClientesActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonGenerarArchivoProyectos)
                    .addComponent(jButtonLeerArchivoProyectos)
                    .addComponent(jButtonGenerarArchivoClientes)
                    .addComponent(jButtonLeerArchivoClientes))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonGenerarArchivoProyectos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonLeerArchivoProyectos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonGenerarArchivoClientes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonLeerArchivoClientes)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jTextAreaResultados.setEditable(false);
        jTextAreaResultados.setColumns(20);
        jTextAreaResultados.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        jTextAreaResultados.setRows(15);
        jScrollPane1.setViewportView(jTextAreaResultados);

        jButtonSalir.setText("Salir");
        jButtonSalir.addActionListener(this::jButtonSalirActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonSalir, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSalir)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRegistrarProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarProyectoActionPerformed
        losProyectos = registrarProyecto(losProyectos);
    }//GEN-LAST:event_jButtonRegistrarProyectoActionPerformed

    private void jButtonConsultarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarTodosActionPerformed
        jTextAreaResultados.setText(consultarTodosProyectos(losProyectos));
    }//GEN-LAST:event_jButtonConsultarTodosActionPerformed

    private void jButtonConsultarEnCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarEnCursoActionPerformed
        jTextAreaResultados.setText(consultarProyectosEnCurso(losProyectos));
    }//GEN-LAST:event_jButtonConsultarEnCursoActionPerformed

    private void jButtonConsultarPrimeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarPrimeroActionPerformed
        jTextAreaResultados.setText(consultarPrimerProyecto(losProyectos));
    }//GEN-LAST:event_jButtonConsultarPrimeroActionPerformed

    private void jButtonConsultarUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarUltimoActionPerformed
        jTextAreaResultados.setText(consultarUltimoProyecto(losProyectos));
    }//GEN-LAST:event_jButtonConsultarUltimoActionPerformed

    private void jButtonConsultarProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarProyectoActionPerformed
        String codigoProyecto = jTextFieldCodigo.getText();
        if(codigoProyecto.isEmpty() || codigoProyecto.isBlank()){
            JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n", "CONSULTAR PROYECTO: código", JOptionPane.ERROR_MESSAGE);
        } else{
            jTextAreaResultados.setText(consultarProyectoDadoCodigo(losProyectos, codigoProyecto));
        }
    }//GEN-LAST:event_jButtonConsultarProyectoActionPerformed

    private void jButtonConsultarGerenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarGerenteActionPerformed
        String codigoProyecto = jTextFieldCodigo.getText();
        if(codigoProyecto.isEmpty() || codigoProyecto.isBlank()){
            JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n", "CONSULTAR GERENTE: código", JOptionPane.ERROR_MESSAGE);
        } else{
            jTextAreaResultados.setText(consultarGerenteProyectoDadoCodigo(losProyectos, codigoProyecto));
        }
    }//GEN-LAST:event_jButtonConsultarGerenteActionPerformed

    private void jButtonConsultarEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarEquipoActionPerformed
        String codigoProyecto = jTextFieldCodigo.getText();
        if(codigoProyecto.isEmpty() || codigoProyecto.isBlank()){
            JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n", "CONSULTAR EQUIPO: código", JOptionPane.ERROR_MESSAGE);
        } else{
            jTextAreaResultados.setText(consultarEquipoProyectoDadoCodigo(losProyectos, codigoProyecto));
        }
    }//GEN-LAST:event_jButtonConsultarEquipoActionPerformed

    private void jButtonRegistrarIntegranteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarIntegranteActionPerformed
        String codigoProyecto = jTextFieldCodigo.getText();
        if(codigoProyecto.isEmpty() || codigoProyecto.isBlank()){
            JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n", "REGISTRAR INTEGRANTE: código", JOptionPane.ERROR_MESSAGE);
        } else{
            jTextAreaResultados.setText(registrarIntegranteProyectoDadoCodigo(losProyectos, codigoProyecto));
        }
    }//GEN-LAST:event_jButtonRegistrarIntegranteActionPerformed

    private void jButtonRegistrarEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarEquipoActionPerformed
        String codigoProyecto = jTextFieldCodigo.getText();
        if(codigoProyecto.isEmpty() || codigoProyecto.isBlank()){
            JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n", "REGISTRAR EQUIPO: código", JOptionPane.ERROR_MESSAGE);
        } else{
            jTextAreaResultados.setText(registrarEquipoProyectoDadoCodigo(losProyectos, codigoProyecto));
        }
    }//GEN-LAST:event_jButtonRegistrarEquipoActionPerformed

    private void jButtonEliminarIntegranteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarIntegranteActionPerformed
        String codigoProyecto = jTextFieldCodigo.getText();
        if(codigoProyecto.isEmpty() || codigoProyecto.isBlank()){
            JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n", "ELIMINAR INTEGRANTE: código", JOptionPane.ERROR_MESSAGE);
        } else{
            jTextAreaResultados.setText(eliminarIntegranteProyectoDadoCodigo(losProyectos, codigoProyecto));
        }
    }//GEN-LAST:event_jButtonEliminarIntegranteActionPerformed

    private void jButtonActualizarEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualizarEstadoActionPerformed
        String codigoProyecto = jTextFieldCodigo.getText();
        if(codigoProyecto.isEmpty() || codigoProyecto.isBlank()){
            JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n", "ACTUALIZAR ESTADO: código", JOptionPane.ERROR_MESSAGE);
        } else{
            jTextAreaResultados.setText(actualizarEstadoProyectoDadoCodigo(losProyectos, codigoProyecto));
        }
    }//GEN-LAST:event_jButtonActualizarEstadoActionPerformed

    private void jButtonConsultarPorGerenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarPorGerenteActionPerformed
        String numIDEGerente = jTextFieldNumIdeGerente.getText();
        if(numIDEGerente.isEmpty() || numIDEGerente.isBlank()){
            JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n", "CONSULTAR PROYECTOS DEL GERENTE: número de identificación", JOptionPane.ERROR_MESSAGE);
        } else{
            jTextAreaResultados.setText(consultarProyectosDadoNumIDEGerente(losProyectos, numIDEGerente));
        }
    }//GEN-LAST:event_jButtonConsultarPorGerenteActionPerformed

    private void jButtonConsultarPorClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarPorClienteActionPerformed
        String numIDECliente = jTextFieldNumIdeCliente.getText();
        if(numIDECliente.isEmpty() || numIDECliente.isBlank()){
            JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n", "CONSULTAR PROYECTOS DEL CLIENTE: número de identificación", JOptionPane.ERROR_MESSAGE);
        } else{
            jTextAreaResultados.setText(consultarProyectosDadoNumIDECliente(losProyectos, numIDECliente));
        }
    }//GEN-LAST:event_jButtonConsultarPorClienteActionPerformed

    private void jButtonConsultarPorTipoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarPorTipoClienteActionPerformed
        String tipoCliente = (String) jComboBoxTipoCliente.getSelectedItem();
        jTextAreaResultados.setText(consultarProyectosDadoTipoCliente(losProyectos, tipoCliente));
    }//GEN-LAST:event_jButtonConsultarPorTipoClienteActionPerformed

    private void jButtonGenerarArchivoProyectosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGenerarArchivoProyectosActionPerformed
        generarArchivoDeObjetosProyectos(losProyectos);
    }//GEN-LAST:event_jButtonGenerarArchivoProyectosActionPerformed

    private void jButtonLeerArchivoProyectosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLeerArchivoProyectosActionPerformed
        ArrayList<Proyecto> proyectosLeidos = leerArchivoDeObjetosProyectos();
        //Solo se reemplaza la colección si se leyeron proyectos (si el archivo no existe se conservan los actuales)
        if(!proyectosLeidos.isEmpty()){
            losProyectos = proyectosLeidos;
            jTextAreaResultados.setText(consultarTodosProyectos(losProyectos));
        }
    }//GEN-LAST:event_jButtonLeerArchivoProyectosActionPerformed

    private void jButtonGenerarArchivoClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGenerarArchivoClientesActionPerformed
        generarArchivoDeObjetosClientes(losProyectos);
    }//GEN-LAST:event_jButtonGenerarArchivoClientesActionPerformed

    private void jButtonLeerArchivoClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLeerArchivoClientesActionPerformed
        losClientes = leerArchivoDeObjetosClientes();
        String resultado = "Clientes leidos del archivo\n";
        for(Cliente objeto : losClientes){
            resultado += objeto.toString();
            resultado += "\n";
        }
        jTextAreaResultados.setText(resultado);
    }//GEN-LAST:event_jButtonLeerArchivoClientesActionPerformed

    private void jButtonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirActionPerformed
        JOptionPane.showMessageDialog(null, "El programa se cerrará. Gracias por utilizar nuestros servicios.");
        System.exit(0);
    }//GEN-LAST:event_jButtonSalirActionPerformed

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
    private javax.swing.JButton jButtonActualizarEstado;
    private javax.swing.JButton jButtonConsultarEnCurso;
    private javax.swing.JButton jButtonConsultarEquipo;
    private javax.swing.JButton jButtonConsultarGerente;
    private javax.swing.JButton jButtonConsultarPorCliente;
    private javax.swing.JButton jButtonConsultarPorGerente;
    private javax.swing.JButton jButtonConsultarPorTipoCliente;
    private javax.swing.JButton jButtonConsultarPrimero;
    private javax.swing.JButton jButtonConsultarProyecto;
    private javax.swing.JButton jButtonConsultarTodos;
    private javax.swing.JButton jButtonConsultarUltimo;
    private javax.swing.JButton jButtonEliminarIntegrante;
    private javax.swing.JButton jButtonGenerarArchivoClientes;
    private javax.swing.JButton jButtonGenerarArchivoProyectos;
    private javax.swing.JButton jButtonLeerArchivoClientes;
    private javax.swing.JButton jButtonLeerArchivoProyectos;
    private javax.swing.JButton jButtonRegistrarEquipo;
    private javax.swing.JButton jButtonRegistrarIntegrante;
    private javax.swing.JButton jButtonRegistrarProyecto;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JComboBox<String> jComboBoxTipoCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaResultados;
    private javax.swing.JTextField jTextFieldCodigo;
    private javax.swing.JTextField jTextFieldNumIdeCliente;
    private javax.swing.JTextField jTextFieldNumIdeGerente;
    // End of variables declaration//GEN-END:variables
}
