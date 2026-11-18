package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProyectoTest {

    private Cliente clienteNatural;
    private Cliente clienteEmpresa;
    private Contacto contactoEmpresa;
    private Empleado gerente;
    private Empleado dev1;
    private Empleado dev2;
    private Proyecto proyecto;

    @Before
    public void setUp() {
        clienteNatural = new PersonaNatural("1001", "Ana Pérez", "Cali", "3001112233", "ana@mail.com");
        contactoEmpresa = new Contacto("Carlos Ruiz", "Director TI", "3104445566", "carlos@tech.com");
        clienteEmpresa = new Empresa("9001", "TechCali SAS", "Cali", contactoEmpresa);
        gerente = new Empleado("2001", "Luis Gómez", "Ingeniero de Sistemas", (byte) 8);
        dev1 = new Empleado("2002", "Sofía Mora", "Desarrolladora Backend", (byte) 3);
        dev2 = new Empleado("2003", "Mateo Silva", "Desarrollador Frontend", (byte) 2);

        proyecto = new Proyecto("2026-001", "Sistema de Gestion Academica",
                LocalDate.of(2026, 1, 15), "meses", (byte) 6, clienteNatural, gerente);
    }

    @Test
    public void testConstructorPorDefectoYGetters() {
        assertEquals("2026-001", proyecto.getCodigo());
        assertEquals("Sistema de Gestion Academica", proyecto.getTitulo());
        assertEquals("INICIADO", proyecto.getEstado());
        assertNotNull(proyecto.getSuEquipo());
        assertEquals(0, proyecto.getSuEquipo().size());
    }

    @Test
    public void testConstructorConEquipoInicial() {
        ArrayList<Empleado> equipoInicial = new ArrayList<>();
        equipoInicial.add(dev1);
        equipoInicial.add(dev2);

        Proyecto proyConEquipo = new Proyecto("2026-002", "Plataforma Web Empresarial Cali",
                LocalDate.of(2026, 3, 1), "años", (byte) 2, clienteEmpresa, gerente, equipoInicial);

        assertEquals(2, proyConEquipo.getSuEquipo().size());
        assertEquals("INICIADO", proyConEquipo.getEstado());
    }

    @Test
    public void testDeterminarFechaFinalizacion() {
        // 2026-01-15 + 6 meses = 2026-07-15
        assertEquals(LocalDate.of(2026, 7, 15), proyecto.determinarFechaFinalizacion());

        // Prueba en semanas: 2026-01-15 + 2 semanas = 2026-01-29
        proyecto.setUnidadDuracion("semanas");
        proyecto.setDuracionEstimada((byte) 2);
        assertEquals(LocalDate.of(2026, 1, 29), proyecto.determinarFechaFinalizacion());

        // Prueba en años: 2026-01-15 + 1 año = 2027-01-15
        proyecto.setUnidadDuracion("años");
        proyecto.setDuracionEstimada((byte) 1);
        assertEquals(LocalDate.of(2027, 1, 15), proyecto.determinarFechaFinalizacion());
    }

    @Test
    public void testAgregarYEliminarIntegrante() {
        proyecto.agregarIntegrante(dev1);
        proyecto.agregarIntegrante(dev2);
        assertEquals(2, proyecto.getSuEquipo().size());

        Empleado eliminado = proyecto.eliminarIntegrante("2002");
        assertNotNull(eliminado);
        assertEquals("Sofía Mora", eliminado.getNombreCompleto());
        assertEquals(1, proyecto.getSuEquipo().size());

        // Eliminar integrante que no existe debe devolver null
        Empleado noExiste = proyecto.eliminarIntegrante("9999");
        assertNull(noExiste);
        assertEquals(1, proyecto.getSuEquipo().size());
    }

    @Test
    public void testEntidadesClienteYContacto() {
        assertEquals("Ana Pérez", clienteNatural.getNombre());
        assertEquals("TechCali SAS", clienteEmpresa.getNombre());
        assertEquals("Carlos Ruiz", contactoEmpresa.getNombreCompleto());
        assertEquals("Director TI", contactoEmpresa.getCargo());
        assertTrue(clienteEmpresa.toString().contains("TechCali SAS"));
        assertTrue(clienteNatural.toString().contains("Ana Pérez"));
    }
}