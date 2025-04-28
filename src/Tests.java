import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.miapp.modelo.Reporte;

public class Tests {

    @Test
    public void testGettersReporte() {
        Reporte reporte = new Reporte(1, "Baches", "Este es un ejemplo", "Calle X", "DD-MM-YY", "Resuelto", "ArchivoAdjunto.jpg");

        assertEquals(1, reporte.getIdReporte());
        assertEquals("Baches", reporte.getCategoria());
        assertEquals("Este es un ejemplo", reporte.getDescripcion());
        assertEquals("Calle X", reporte.getUbicacion());
        assertEquals("DD-MM-YY", reporte.getFecha());
        assertEquals("Resuelto", reporte.getEstado());
        assertEquals("ArchivoAdjunto.jpg", reporte.getArchivoAdjunto());
    }

    @Test
    public void testSettersReporte() {
        Reporte reporte = new Reporte(1, "Baches", "Este es un ejemplo", "Calle X", "DD-MM-YY", "Resuelto", "ArchivoAdjunto.jpg");

        reporte.setIdReporte(2);
        reporte.setCategoria("Control Animal");
        reporte.setDescripcion("Hay muchas ratas");
        reporte.setUbicacion("Calle Y");
        reporte.setFecha("DD-MM-YYYY");
        reporte.setEstado("Pendiente");
        reporte.setArchivoAdjunto("Archivo2.png");

        assertEquals(2, reporte.getIdReporte());
        assertEquals("Control Animal", reporte.getCategoria());
        assertEquals("Hay muchas ratas", reporte.getDescripcion());
        assertEquals("Calle Y", reporte.getUbicacion());
        assertEquals("DD-MM-YYYY", reporte.getFecha());
        assertEquals("Pendiente", reporte.getEstado());
        assertEquals("Archivo2.png", reporte.getArchivoAdjunto());
    }

  @Test
public void testInicializacionTabla() {
    DefaultTableModel modeloTabla = new DefaultTableModel(new String[]{
        "ID Reporte", "Categoria", "Descripcion", "Ubicacion", 
        "Fecha", "Archivo Adjunto", "Estado", "ID Usuario"
    }, 0);
    String[] columnasEsperadas = {"ID Reporte", "Categoria", "Descripcion", "Ubicacion", 
                                  "Fecha", "Archivo Adjunto", "Estado", "ID Usuario"};
    for (int i = 0; i < columnasEsperadas.length; i++) {
        assertEquals(columnasEsperadas[i], modeloTabla.getColumnName(i));
    }
}



}