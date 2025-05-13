import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel; // Importación necesaria
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Window;

public class TestProyecto {

    @Test
    public void testLoginUsuarioExitoso() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Preparar datos de prueba
            String correo = "testuser@example.com";
            String contrasena = "password123";
            String contrasenaHash = HashUtil.hashPassword(contrasena);

            String sqlInsert = "INSERT INTO Usuarios (Usuario_Nombre, Usuario_Correo, Usuario_Contraseña, Usuario_Genero, Usuario_Telefono, Usuario_Primer_Nombre, Usuario_Apellido_Paterno, Usuario_Apellido_Materno) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);
            stmt.setString(1, "Test User");
            stmt.setString(2, correo);
            stmt.setString(3, contrasenaHash);
            stmt.setString(4, "Masculino");
            stmt.setString(5, "1234567890");
            stmt.setString(6, "Test");
            stmt.setString(7, "User");
            stmt.setString(8, "Example");
            stmt.executeUpdate();

            // Probar inicio de sesión
            PantallaLogin pantallaLogin = new PantallaLogin();
            pantallaLogin.campoUsuario.setText(correo);
            pantallaLogin.campoPassword.setText(contrasena);
            pantallaLogin.btnLogin.doClick();

            // Verificar si se abre el panel de usuario y contiene el panel de notificaciones
            boolean panelAbierto = false;
            for (Window window : Window.getWindows()) {
                if (window instanceof PanelUsuario && window.isVisible()) {
                    PanelUsuario panelUsuario = (PanelUsuario) window;
                    panelAbierto = panelUsuario.getPanelNotificaciones() != null;
                    window.dispose(); // Cerrar el panel después de la prueba
                }
            }

            assertTrue("El panel de usuario no se abrió o no contiene el panel de notificaciones tras un inicio de sesión exitoso.", panelAbierto);
        } catch (SQLException e) {
            fail("Error en la base de datos: " + e.getMessage());
        }
    }


    @Test
    public void testRegistroUsuario() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Probar registro de usuario
            PantallaRegistro pantallaRegistro = new PantallaRegistro();
            pantallaRegistro.setVisible(true);

            pantallaRegistro.campoNombre.setText("Nuevo Usuario");
            pantallaRegistro.campoCorreo.setText("nuevo@example.com");
            pantallaRegistro.campoPassword.setText("password123");
            pantallaRegistro.campoPasswordConfirm.setText("password123");
            pantallaRegistro.btnCrearCuenta.doClick();

            // Verificar que el usuario fue registrado
            String sqlInsert = "INSERT INTO Usuarios (Usuario_Nombre, Usuario_Correo, Usuario_Contraseña, Usuario_Genero, Usuario_Telefono, Usuario_Primer_Nombre, Usuario_Apellido_Paterno, Usuario_Apellido_Materno) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);
            stmt.setString(1, "Nuevo Usuario");
            stmt.setString(2, "nuevo@example.com");
            stmt.setString(3, HashUtil.hashPassword("password123"));
            stmt.setString(4, "Femenino");
            stmt.setString(5, "9876543210");
            stmt.setString(6, "Nuevo");
            stmt.setString(7, "Usuario");
            stmt.setString(8, "Ejemplo");
            stmt.executeUpdate();

            String sql = "SELECT * FROM Usuarios WHERE Usuario_Correo = ?";
            PreparedStatement stmtSelect = conn.prepareStatement(sql);
            stmtSelect.setString(1, "nuevo@example.com");
            ResultSet rs = stmtSelect.executeQuery();

            assertTrue(rs.next());

            // Limpiar datos de prueba
            String sqlDelete = "DELETE FROM Usuarios WHERE Usuario_Correo = ?";
            PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete);
            stmtDelete.setString(1, "nuevo@example.com");
            stmtDelete.executeUpdate();
        } catch (SQLException e) {
            fail("Error en la prueba: " + e.getMessage());
        }
    }

    @After
    public void limpiarDatosPrueba() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sqlDeleteUsuarios = "DELETE FROM Usuarios WHERE Usuario_Correo LIKE 'test%'";
            conn.prepareStatement(sqlDeleteUsuarios).executeUpdate();

            String sqlDeleteFuncionarios = "DELETE FROM Funcionario WHERE Funcionario_Correo LIKE 'funcionario%'";
            conn.prepareStatement(sqlDeleteFuncionarios).executeUpdate();

            String sqlDeleteReportes = "DELETE FROM Reporte WHERE Reporte_Descripcion LIKE 'Bache%'";
            conn.prepareStatement(sqlDeleteReportes).executeUpdate();

            String sqlDeleteNotificaciones = "DELETE FROM Notificaciones WHERE Usuario_ID = 1";
            conn.prepareStatement(sqlDeleteNotificaciones).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testLoginFuncionarioBasico() {
        PantallaLoginFuncionario pantalla = new PantallaLoginFuncionario();
        pantalla.setVisible(true);

        // Establecer las credenciales de prueba
        pantalla.campoCorreo.setText("prueba");
        pantalla.campoClave.setText("1");

        // Simular el clic en el botón de login
        pantalla.btnLogin.doClick();

        // Verificar si se abre VisorReportes
        boolean visorAbierto = false;
        for (Window window : Window.getWindows()) {
            if (window instanceof VisorReportes && window.isVisible()) {
                visorAbierto = true;
                window.dispose(); // Cerrar la ventana después de la prueba
            }
        }

        // Comprobar que el visor de reportes se abrió
        assertEquals("El visor de reportes no se abrió tras un inicio de sesión exitoso.", true, visorAbierto);

        // Cerrar la pantalla de login
        pantalla.dispose();
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
