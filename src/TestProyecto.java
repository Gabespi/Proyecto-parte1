import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestProyecto {

    @Test
    public void testLoginUsuarioExitoso() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Preparar datos de prueba
            String correo = "testuser@example.com";
            String contraseña = "password123";
            String contraseñaHash = HashUtil.hashPassword(contraseña);

            String sqlInsert = "INSERT INTO Usuarios (Usuario_Nombre, Usuario_Correo, Usuario_Contraseña, Usuario_Genero, Usuario_Telefono) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);
            stmt.setString(1, "Test User");
            stmt.setString(2, correo);
            stmt.setString(3, contraseñaHash);
            stmt.setString(4, "Masculino");
            stmt.setString(5, "1234567890"); // Proporcionar un valor para Usuario_Telefono
            stmt.executeUpdate();

            // Probar inicio de sesión
            PantallaLogin pantallaLogin = new PantallaLogin();
            pantallaLogin.campoUsuario.setText(correo);
            pantallaLogin.campoPassword.setText(contraseña);
            pantallaLogin.btnLogin.doClick();

            // Verificar que el inicio de sesión fue exitoso
            assertFalse("La pantalla de inicio de sesión no se cerró.", pantallaLogin.isVisible());

            // Limpiar datos de prueba
            String sqlDelete = "DELETE FROM Usuarios WHERE Usuario_Correo = ?";
            PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete);
            stmtDelete.setString(1, correo);
            stmtDelete.executeUpdate();
        } catch (SQLException e) {
            fail("Error en la prueba: " + e.getMessage());
        }
    }

    @Test
    public void testLoginFuncionarioExitoso() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Preparar datos de prueba
            String correo = "funcionario@example.com";
            String clave = "clave123";

            String sqlInsertFuncionario = "INSERT INTO Funcionario (Funcionario_Nombre, Funcionario_Correo, Funcionario_Clave, Funcionario_Area_Responsable) VALUES (?, ?, ?, ?)";
            PreparedStatement stmtFuncionario = conn.prepareStatement(sqlInsertFuncionario);
            stmtFuncionario.setString(1, "Funcionario Test");
            stmtFuncionario.setString(2, correo);
            stmtFuncionario.setString(3, clave);
            stmtFuncionario.setString(4, "Infraestructura"); // Proporcionar un valor para Funcionario_Area_Responsable
            stmtFuncionario.executeUpdate();

            // Probar inicio de sesión
            PantallaLoginFuncionario pantallaFuncionario = new PantallaLoginFuncionario();
            pantallaFuncionario.campoCorreo.setText(correo);
            pantallaFuncionario.campoClave.setText(clave);
            pantallaFuncionario.btnLogin.doClick();

            // Verificar que la pantalla se cerró (inicio de sesión exitoso)
            assertFalse(pantallaFuncionario.isVisible());

            // Limpiar datos de prueba
            String sqlDelete = "DELETE FROM Funcionario WHERE Funcionario_Correo = ?";
            PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete);
            stmtDelete.setString(1, correo);
            stmtDelete.executeUpdate();
        } catch (SQLException e) {
            fail("Error en la prueba: " + e.getMessage());
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
            String sql = "SELECT * FROM Usuarios WHERE Usuario_Correo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "nuevo@example.com");
            ResultSet rs = stmt.executeQuery();

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

    @Test
    public void testCambioContraseña() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Preparar datos de prueba
            String correo = "cambiar@example.com";
            String contraseñaActual = "oldpassword";
            String nuevaContraseña = "newpassword";
            String contraseñaHash = HashUtil.hashPassword(contraseñaActual);

            String sqlInsert = "INSERT INTO Usuarios (Usuario_Nombre, Usuario_Correo, Usuario_Contraseña, Usuario_Genero, Usuario_Telefono) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);
            stmt.setString(1, "Usuario Cambio");
            stmt.setString(2, correo);
            stmt.setString(3, contraseñaHash);
            stmt.setString(4, "Masculino");
            stmt.setString(5, "1234567890");
            stmt.executeUpdate();

            // Probar cambio de contraseña
            PantallaCambiarContraseña pantallaCambio = new PantallaCambiarContraseña();
            pantallaCambio.setVisible(true);

            JTextField campoCorreo = (JTextField) pantallaCambio.getContentPane().getComponent(4);
            JPasswordField campoContraseñaActual = (JPasswordField) pantallaCambio.getContentPane().getComponent(6);
            JPasswordField campoNuevaContraseña = (JPasswordField) pantallaCambio.getContentPane().getComponent(8);
            JPasswordField campoConfirmarContraseña = (JPasswordField) pantallaCambio.getContentPane().getComponent(10);
            JButton btnCambiarContraseña = (JButton) pantallaCambio.getContentPane().getComponent(12);

            campoCorreo.setText(correo);
            campoContraseñaActual.setText(contraseñaActual);
            campoNuevaContraseña.setText(nuevaContraseña);
            campoConfirmarContraseña.setText(nuevaContraseña);
            btnCambiarContraseña.doClick();

            // Verificar que la contraseña fue actualizada
            String sql = "SELECT Usuario_Contraseña FROM Usuarios WHERE Usuario_Correo = ?";
            PreparedStatement stmtSelect = conn.prepareStatement(sql);
            stmtSelect.setString(1, correo);
            ResultSet rs = stmtSelect.executeQuery();

            assertTrue("No se encontró el usuario en la base de datos.", rs.next());
            assertEquals("La contraseña no fue actualizada correctamente.", HashUtil.hashPassword(nuevaContraseña), rs.getString("Usuario_Contraseña"));

            // Limpiar datos de prueba
            String sqlDelete = "DELETE FROM Usuarios WHERE Usuario_Correo = ?";
            PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete);
            stmtDelete.setString(1, correo);
            stmtDelete.executeUpdate();
        } catch (SQLException e) {
            fail("Error en la prueba: " + e.getMessage());
        }
    }

    @Test
    public void testRegistroReporte() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Preparar datos de prueba
            Usuario usuario = new Usuario(1, "Usuario Test", "test@example.com", "12345", "Usuario");

            PantallaRegistrarReporte pantallaReporte = new PantallaRegistrarReporte(usuario);
            pantallaReporte.setVisible(true);

            JComboBox<String> comboCategoria = pantallaReporte.getComboCategoria();
            JTextArea campoDescripcion = pantallaReporte.getCampoDescripcion();
            JTextArea campoUbicacion = pantallaReporte.getCampoUbicacion();
            JButton btnRegistrar = pantallaReporte.getBtnRegistrar();

            comboCategoria.setSelectedItem("Baches");
            campoDescripcion.setText("Bache en la calle principal");
            campoUbicacion.setText("Calle Principal #123");
            btnRegistrar.doClick();

            // Verificar que el reporte fue registrado
            String sql = "SELECT * FROM Reporte WHERE Reporte_Descripcion = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "Bache en la calle principal");
            ResultSet rs = stmt.executeQuery();

            assertTrue(rs.next());

            // Limpiar datos de prueba
            String sqlDelete = "DELETE FROM Reporte WHERE Reporte_Descripcion = ?";
            PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete);
            stmtDelete.setString(1, "Bache en la calle principal");
            stmtDelete.executeUpdate();
        } catch (SQLException e) {
            fail("Error en la prueba: " + e.getMessage());
        }
    }

    @Test
    public void testNotificacionesReporte() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Preparar datos de prueba
            String sqlInsert = "INSERT INTO Notificaciones (Usuario_ID, Mensaje, Fecha) VALUES (?, ?, datetime('now'))";
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);
            stmt.setInt(1, 1); // ID de usuario ficticio
            stmt.setString(2, "El reporte #123 ha sido actualizado.");
            stmt.executeUpdate();

            // Verificar que la notificación aparece
            PanelUsuario panelUsuario = new PanelUsuario(new Usuario(1, "Usuario Test", "test@example.com", "12345", "Usuario"));
            panelUsuario.setVisible(true);

            JPanel panelNotificaciones = panelUsuario.getPanelNotificaciones();
            assertTrue(panelNotificaciones.getComponentCount() > 0);

            // Limpiar datos de prueba
            String sqlDelete = "DELETE FROM Notificaciones WHERE Usuario_ID = ?";
            PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete);
            stmtDelete.setInt(1, 1);
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
}
