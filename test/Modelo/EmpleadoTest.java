package Modelo;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EmpleadoTest {

    private Empleado empleado;

    @Before
    public void setUp() {
        empleado = new Empleado("2001", "Luis Gomez", "Ingeniero de Sistemas", (byte) 8);
    }

    @Test
    public void testConstructorYGetters() {
        assertEquals("2001", empleado.getNumeroIdentificacion());
        assertEquals("Luis Gomez", empleado.getNombreCompleto());
        assertEquals("Ingeniero de Sistemas", empleado.getTituloProfesional());
        assertEquals(8, empleado.getAniosExperiencia());
    }

    @Test
    public void testSetters() {
        empleado.setNumeroIdentificacion("2050");
        empleado.setNombreCompleto("Luis Alberto Gomez");
        empleado.setTituloProfesional("Magister en Gestion de Proyectos");
        empleado.setAniosExperiencia((byte) 10);
        assertEquals("2050", empleado.getNumeroIdentificacion());
        assertEquals("Luis Alberto Gomez", empleado.getNombreCompleto());
        assertEquals("Magister en Gestion de Proyectos", empleado.getTituloProfesional());
        assertEquals(10, empleado.getAniosExperiencia());
    }

    @Test
    public void testToString() {
        String texto = empleado.toString();
        assertTrue(texto.contains("2001"));
        assertTrue(texto.contains("Luis Gomez"));
    }
}
