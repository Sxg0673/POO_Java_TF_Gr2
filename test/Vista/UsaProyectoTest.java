package Vista;

import Modelo.Cliente;
import Modelo.Contacto;
import Modelo.Empleado;
import Modelo.Empresa;
import Modelo.PersonaNatural;
import Modelo.Proyecto;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UsaProyectoTest {

    private UsaProyecto app;
    private ArrayList<Proyecto> listaVacia;
    private ArrayList<Proyecto> listaConDatos;

    @Before
    public void setUp() {
        app = new UsaProyecto();
        listaVacia = new ArrayList<>();
        listaConDatos = new ArrayList<>();

        Cliente cliNatural = new PersonaNatural("100", "Laura Diaz", "Cali", "3001234567", "laura@mail.com");
        Contacto cont = new Contacto("Mario Ruiz", "CTO", "3112223344", "mario@soft.com");
        Cliente cliEmpresa = new Empresa("900", "SoftCali SAS", "Cali", cont);

        Empleado gerente1 = new Empleado("501", "Roberto Gomez", "Ing. Software", (byte) 10);
        Empleado gerente2 = new Empleado("502", "Elena Vargas", "Ing. Industrial", (byte) 7);
        Empleado integrante = new Empleado("601", "Pedro Rios", "Tecnologo en Sistemas", (byte) 2);

        Proyecto p1 = new Proyecto("2026-001", "Desarrollo Portal Web Institucional",
                LocalDate.of(2026, 2, 1), "meses", (byte) 4, cliNatural, gerente1);
        Proyecto p2 = new Proyecto("2026-002", "Implementacion ERP Empresarial Cali",
                LocalDate.of(2026, 3, 15), "anios", (byte) 1, cliEmpresa, gerente2);
        p2.setEstado("EN CURSO");
        p2.agregarIntegrante(integrante);

        listaConDatos.add(p1);
        listaConDatos.add(p2);
    }

    @Test
    public void testGenerarCodigoProyectoListaVacia() {
        int anioActual = LocalDate.now().getYear();
        assertEquals(anioActual + "-001", app.generarCodigoProyecto(listaVacia));
    }

    @Test
    public void testGenerarCodigoProyectoConsecutivo() {
        int anioActual = LocalDate.now().getYear();
        ArrayList<Proyecto> lista = new ArrayList<>();
        Empleado gerente = new Empleado("501", "Roberto Gomez", "Ing. Software", (byte) 10);
        Cliente cliente = new PersonaNatural("100", "Laura Diaz", "Cali", "3001234567", "laura@mail.com");
        lista.add(new Proyecto(anioActual + "-001", "Proyecto A", LocalDate.now(), "semanas", (byte) 3, cliente, gerente));
        assertEquals(anioActual + "-002", app.generarCodigoProyecto(lista));
    }

    @Test
    public void testConsultasConListaVacia() {
        assertTrue(app.consultarTodosProyectos(listaVacia).contains("No hay proyectos registrados."));
        assertTrue(app.consultarProyectosEnCurso(listaVacia).contains("No hay proyectos registrados."));
        assertTrue(app.consultarPrimerProyecto(listaVacia).contains("No hay proyectos registrados."));
        assertTrue(app.consultarUltimoProyecto(listaVacia).contains("No hay proyectos registrados."));
    }

    @Test
    public void testConsultarTodosYEnCurso() {
        String resTodos = app.consultarTodosProyectos(listaConDatos);
        assertTrue(resTodos.contains("2026-001"));
        assertTrue(resTodos.contains("2026-002"));

        String resEnCurso = app.consultarProyectosEnCurso(listaConDatos);
        assertTrue(resEnCurso.contains("2026-002"));
        assertFalse(resEnCurso.contains("2026-001"));
    }

    @Test
    public void testConsultarPrimerYUltimoProyecto() {
        assertTrue(app.consultarPrimerProyecto(listaConDatos).contains("2026-001"));
        assertTrue(app.consultarUltimoProyecto(listaConDatos).contains("2026-002"));
    }

    @Test
    public void testConsultaProyectoDadoCodigo() {
        assertTrue(app.consultarProyectoDadoCodigo(listaConDatos, "2026-001").contains("Desarrollo Portal Web Institucional"));
        assertTrue(app.consultarProyectoDadoCodigo(listaConDatos, "2026-999").contains("No existe un proyecto con codigo 2026-999."));
    }

    @Test
    public void testConsultarGerenteYEquipoDadoCodigo() {
        assertTrue(app.consultarGerenteProyectoDadoCodigo(listaConDatos, "2026-002").contains("Elena Vargas"));
        assertTrue(app.consultarEquipoProyectoDadoCodigo(listaConDatos, "2026-002").contains("Pedro Rios"));
    }

    @Test
    public void testFiltrosPorGerenteClienteYTipoCliente() {
        String resGerente = app.consultarProyectosDadoNumIDEGerente(listaConDatos, "501");
        assertTrue(resGerente.contains("2026-001"));
        assertFalse(resGerente.contains("2026-002"));

        String resCliente = app.consultarProyectosDadoNumIDECliente(listaConDatos, "900");
        assertTrue(resCliente.contains("2026-002"));
        assertFalse(resCliente.contains("2026-001"));

        String resTipoEmpresa = app.consultarProyectosDadoTipoCliente(listaConDatos, "Empresa");
        assertTrue(resTipoEmpresa.contains("2026-002"));
        assertFalse(resTipoEmpresa.contains("2026-001"));

        String resTipoPersona = app.consultarProyectosDadoTipoCliente(listaConDatos, "PersonaNatural");
        assertTrue(resTipoPersona.contains("2026-001"));
        assertFalse(resTipoPersona.contains("2026-002"));
    }

    @Test
    public void testFiltrosSinResultados() {
        assertTrue(app.consultarProyectosDadoNumIDEGerente(listaConDatos, "999").contains("No existen proyectos con gerente identificado con 999."));
        assertTrue(app.consultarProyectosDadoNumIDECliente(listaConDatos, "999").contains("No existen proyectos con cliente identificado con 999."));
    }
}
