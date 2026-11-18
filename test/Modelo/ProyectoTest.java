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
        clienteNatural = new PersonaNatural("1001", "Ana Perez", "Cali", "3001112233", "ana@mail.com");
        contactoEmpresa = new Contacto("Carlos Ruiz", "Director TI", "3104445566", "carlos@tech.com");
        clienteEmpresa = new Empresa("9001", "TechCali SAS", "Cali", contactoEmpresa);
        gerente = new Empleado("2001", "Luis Gomez", "Ingeniero de Sistemas", (byte) 8);
        dev1 = new Empleado("2002", "Sofia Mora", "Desarrolladora Backend", (byte) 3);
        dev2 = new Empleado("2003", "Mateo Silva", "Desarrollador Frontend", (byte) 2);

        proyecto = new Proyecto("2026-001", "Sistema de Gestion Academica",
                LocalDate.of(2026, 1, 15), "meses", (byte) 6, clienteNatural, gerente);
    }

    @Test
    public void testConstructorSinEquipoYGetters() {
        assertEquals("2026-001", proyecto.getCodigo());
        assertEquals("Sistema de Gestion Academica", proyecto.getTitulo());
        assertEquals(LocalDate.of(2026, 1, 15), proyecto.getFechaInicio());
        assertEquals("meses", proyecto.getUnidadDuracion());
        assertEquals(6, proyecto.getDuracionEstimada());
        assertSame(clienteNatural, proyecto.getSuCliente());
        assertSame(gerente, proyecto.getSuGerente());
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
                LocalDate.of(2026, 3, 1), "anios", (byte) 2, clienteEmpresa, gerente, equipoInicial);

        assertEquals(2, proyConEquipo.getSuEquipo().size());
        assertEquals("INICIADO", proyConEquipo.getEstado());
    }

    @Test
    public void testSetEstado() {
        proyecto.setEstado("EN CURSO");
        assertEquals("EN CURSO", proyecto.getEstado());
    }

    @Test
    public void testDeterminarFechaFinalizacionEnMeses() {
        // 2026-01-15 + 6 meses = 2026-07-15
        assertEquals(LocalDate.of(2026, 7, 15), proyecto.determinarFechaFinalizacion());
    }

    @Test
    public void testDeterminarFechaFinalizacionEnSemanas() {
        // 2026-01-15 + 2 semanas = 2026-01-29
        proyecto.setUnidadDuracion("semanas");
        proyecto.setDuracionEstimada((byte) 2);
        assertEquals(LocalDate.of(2026, 1, 29), proyecto.determinarFechaFinalizacion());
    }

    @Test
    public void testDeterminarFechaFinalizacionEnAnios() {
        // 2026-01-15 + 1 anio = 2027-01-15
        proyecto.setUnidadDuracion("anios");
        proyecto.setDuracionEstimada((byte) 1);
        assertEquals(LocalDate.of(2027, 1, 15), proyecto.determinarFechaFinalizacion());
    }

    @Test
    public void testAgregarIntegrante() {
        proyecto.agregarIntegrante(dev1);
        proyecto.agregarIntegrante(dev2);
        assertEquals(2, proyecto.getSuEquipo().size());
        assertSame(dev1, proyecto.getSuEquipo().getFirst());
        assertSame(dev2, proyecto.getSuEquipo().getLast());
    }

    @Test
    public void testEliminarIntegranteExistente() {
        proyecto.agregarIntegrante(dev1);
        proyecto.agregarIntegrante(dev2);

        Empleado eliminado = proyecto.eliminarIntegrante("2002");
        assertNotNull(eliminado);
        assertEquals("Sofia Mora", eliminado.getNombreCompleto());
        assertEquals(1, proyecto.getSuEquipo().size());
    }

    @Test
    public void testEliminarIntegranteInexistente() {
        proyecto.agregarIntegrante(dev1);

        // Eliminar un integrante que no existe debe devolver null y no cambiar el equipo
        Empleado noExiste = proyecto.eliminarIntegrante("9999");
        assertNull(noExiste);
        assertEquals(1, proyecto.getSuEquipo().size());
    }

    @Test
    public void testToStringIncluyeDatosDelProyecto() {
        String texto = proyecto.toString();
        assertTrue(texto.contains("2026-001"));
        assertTrue(texto.contains("Sistema de Gestion Academica"));
        assertTrue(texto.contains("INICIADO"));
        assertTrue(texto.contains("Luis Gomez"));
    }
}
