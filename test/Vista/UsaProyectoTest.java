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

        Cliente cliNatural = new PersonaNatural("100", "Laura Díaz", "Cali", "3001234567", "laura@mail.com");
        Contacto cont = new Contacto("Mario Ruiz", "CTO", "3112223344", "mario@soft.com");
        Cliente cliEmpresa = new Empresa("900", "SoftCali SAS", "Cali", cont);

        Empleado gerente1 = new Empleado("501", "Roberto Gómez", "Ing. Software", (byte) 10);
        Empleado gerente2 = new Empleado("502", "Elena Vargas", "Ing. Industrial", (byte) 7);

        Proyecto p1 = new Proyecto("2026-001", "Desarrollo Portal Web Institucional",
                LocalDate.of(2026, 2, 1), "meses", (byte) 4, cliNatural, gerente1);
        Proyecto p2 = new Proyecto("2026-002", "Implementacion ERP Empresarial Cali",
                LocalDate.of(2026, 3, 15), "años", (byte) 1, cliEmpresa, gerente2);

        listaConDatos.add(p1);
        listaConDatos.add(p2);
    }

    @Test
    public void testGenerarCodigoProyectoListaVacia() {
        int anioActual = LocalDate.now().getYear();
        String esperado = anioActual + "-001";
        assertEquals(esperado, app.generarCodigoProyecto(listaVacia));
    }

    @Test
    public void testConsultasConListaVacia() {
        String resTodos = app.consultarTodosProyectos(listaVacia);
        String resEnCurso = app.consultarProyectosEnCurso(listaVacia);
        String resPrimero = app.consultarPrimerProyecto(listaVacia);
        String resUltimo = app.consultarUltimoProyecto(listaVacia);

        assertNotNull(resTodos);
        assertNotNull(resEnCurso);
        assertNotNull(resPrimero);
        assertNotNull(resUltimo);
        assertFalse(resTodos.contains("2026-"));
        assertFalse(resEnCurso.contains("2026-"));
    }

    @Test
    public void testConsultaConCodigoInexistente() {
        String resultado = app.consultarProyectoDadoCodigo(listaConDatos, "2026-999");
        assertNotNull(resultado);
        assertFalse(resultado.contains("Desarrollo Portal Web Institucional"));
        assertFalse(resultado.contains("Implementacion ERP Empresarial Cali"));
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
    }
}