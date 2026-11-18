package Modelo;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClienteTest {

    private Contacto contacto;
    private Empresa empresa;
    private PersonaNatural personaNatural;

    @Before
    public void setUp() {
        contacto = new Contacto("Carlos Ruiz", "Director TI", "3104445566", "carlos@tech.com");
        empresa = new Empresa("9001", "TechCali SAS", "Cali", contacto);
        personaNatural = new PersonaNatural("1001", "Ana Perez", "Palmira", "3001112233", "ana@mail.com");
    }

    @Test
    public void testEmpresaYPersonaNaturalSonClientes() {
        Cliente cliente1 = empresa;
        Cliente cliente2 = personaNatural;
        assertTrue(cliente1 instanceof Empresa);
        assertTrue(cliente2 instanceof PersonaNatural);
        assertFalse(cliente1 instanceof PersonaNatural);
    }

    @Test
    public void testGettersHeredadosEmpresa() {
        assertEquals("9001", empresa.getNumeroIdentificacion());
        assertEquals("TechCali SAS", empresa.getNombre());
        assertEquals("Cali", empresa.getNombreCiudad());
    }

    @Test
    public void testGettersHeredadosPersonaNatural() {
        assertEquals("1001", personaNatural.getNumeroIdentificacion());
        assertEquals("Ana Perez", personaNatural.getNombre());
        assertEquals("Palmira", personaNatural.getNombreCiudad());
    }

    @Test
    public void testDatosPropiosEmpresa() {
        assertSame(contacto, empresa.getSuContacto());
        Contacto otroContacto = new Contacto("Laura Diaz", "Gerente", "3209998877", "laura@tech.com");
        empresa.setSuContacto(otroContacto);
        assertEquals("Laura Diaz", empresa.getSuContacto().getNombreCompleto());
    }

    @Test
    public void testDatosPropiosPersonaNatural() {
        assertEquals("3001112233", personaNatural.getNumeroTelefono());
        assertEquals("ana@mail.com", personaNatural.getCorreoElectronico());
        personaNatural.setNumeroTelefono("3005556677");
        personaNatural.setCorreoElectronico("ana.perez@mail.com");
        assertEquals("3005556677", personaNatural.getNumeroTelefono());
        assertEquals("ana.perez@mail.com", personaNatural.getCorreoElectronico());
    }

    @Test
    public void testSettersHeredados() {
        empresa.setNombre("TechCali Group SAS");
        empresa.setNombreCiudad("Bogota");
        assertEquals("TechCali Group SAS", empresa.getNombre());
        assertEquals("Bogota", empresa.getNombreCiudad());
    }

    @Test
    public void testToStringEmpresa() {
        String texto = empresa.toString();
        assertTrue(texto.contains("Empresa:"));
        assertTrue(texto.contains("TechCali SAS"));
        assertTrue(texto.contains("Carlos Ruiz"));
    }

    @Test
    public void testToStringPersonaNatural() {
        String texto = personaNatural.toString();
        assertTrue(texto.contains("Persona natural:"));
        assertTrue(texto.contains("Ana Perez"));
        assertTrue(texto.contains("ana@mail.com"));
    }
}
