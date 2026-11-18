package Modelo;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ContactoTest {

    private Contacto contacto;

    @Before
    public void setUp() {
        contacto = new Contacto("Carlos Ruiz", "Director TI", "3104445566", "carlos@tech.com");
    }

    @Test
    public void testConstructorYGetters() {
        assertEquals("Carlos Ruiz", contacto.getNombreCompleto());
        assertEquals("Director TI", contacto.getCargo());
        assertEquals("3104445566", contacto.getNumeroTelefono());
        assertEquals("carlos@tech.com", contacto.getCorreoElectronico());
    }

    @Test
    public void testSetters() {
        contacto.setNombreCompleto("Laura Diaz");
        contacto.setCargo("Gerente General");
        contacto.setNumeroTelefono("3209998877");
        contacto.setCorreoElectronico("laura@tech.com");
        assertEquals("Laura Diaz", contacto.getNombreCompleto());
        assertEquals("Gerente General", contacto.getCargo());
        assertEquals("3209998877", contacto.getNumeroTelefono());
        assertEquals("laura@tech.com", contacto.getCorreoElectronico());
    }

    @Test
    public void testToString() {
        String texto = contacto.toString();
        assertTrue(texto.contains("Carlos Ruiz"));
        assertTrue(texto.contains("Director TI"));
    }
}
