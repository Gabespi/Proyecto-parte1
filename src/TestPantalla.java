import org.junit.Test;
import static org.junit.Assert.*;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestPantalla {

    @Test
    public void testLoginUsuarioExitoso() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Preparar datos de prueba
            String correo = "testuser@example.com";
            String contraseña = "password123";
            String contraseñaHash = HashUtil.hashPassword(contraseña);

            String sqlInsert = "INSERT INTO Usuarios (Usuario_Nombre, Usuario_Correo, Usuario_Contraseña) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);
            stmt.setString(1, "Test User");
            stmt.setString(2, correo);
            stmt.setString(3, contraseñaHash);
            stmt.executeUpdate();

            // Probar inicio de sesión
            PantallaLogin pantallaLogin = new PantallaLogin();
            pantallaLogin.campoUsuario.setText(correo);
            pantallaLogin.campoPassword.setText(contraseña);
            pantallaLogin.btnLogin.doClick();

            // Verificar que la pantalla se cerró (inicio de sesión exitoso)
            assertFalse(pantallaLogin.isVisible());

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

            String sqlInsert = "INSERT INTO Funcionario (Funcionario_Nombre, Funcionario_Correo, Funcionario_Clave) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);
            stmt.setString(1, "Funcionario Test");
            stmt.setString(2, correo);
            stmt.setString(3, clave);
            stmt.executeUpdate();

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

            String sqlInsert = "INSERT INTO Usuarios (Usuario_Nombre, Usuario_Correo, Usuario_Contraseña) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);
            stmt.setString(1, "Usuario Cambio");
            stmt.setString(2, correo);
            stmt.setString(3, contraseñaHash);
            stmt.executeUpdate();

            // Probar cambio de contraseña
            PantallaCambiarContraseña pantallaCambio = new PantallaCambiarContraseña();
            pantallaCambio.campoCorreo.setText(correo);
            pantallaCambio.campoContraseñaActual.setText(contraseñaActual);
            pantallaCambio.campoNuevaContraseña.setText(nuevaContraseña);
            pantallaCambio.campoConfirmarContraseña.setText(nuevaContraseña);
            pantallaCambio.btnCambiarContraseña.doClick();

            // Verificar que la contraseña fue actualizada
            String sql = "SELECT Usuario_Contraseña FROM Usuarios WHERE Usuario_Correo = ?";
            PreparedStatement stmtSelect = conn.prepareStatement(sql);
            stmtSelect.setString(1, correo);
            ResultSet rs = stmtSelect.executeQuery();

            assertTrue(rs.next());
            assertEquals(HashUtil.hashPassword(nuevaContraseña), rs.getString("Usuario_Contraseña"));

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
    public void testCambioEstadoReporte() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Preparar datos de prueba
            String sqlInsert = "INSERT INTO Reporte (Reporte_Categoria, Reporte_Descripcion, Reporte_Ubicacion, Reporte_Estado, Reporte_ID_Usuario) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);
            stmt.setString(1, "Baches");
            stmt.setString(2, "Bache en la calle principal");
            stmt.setString(3, "Calle Principal #123");
            stmt.setString(4, "En espera");
            stmt.setInt(5, 1); // ID de usuario ficticio
            stmt.executeUpdate();

            // Obtener el ID del reporte recién creado
            String sqlSelect = "SELECT Reporte_ID FROM Reporte WHERE Reporte_Descripcion = ?";
            PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect);
            stmtSelect.setString(1, "Bache en la calle principal");
            ResultSet rs = stmtSelect.executeQuery();
            assertTrue(rs.next());
            int reporteId = rs.getInt("Reporte_ID");

            // Cambiar el estado del reporte
            String sqlUpdate = "UPDATE Reporte SET Reporte_Estado = ? WHERE Reporte_ID = ?";
            PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate);
            stmtUpdate.setString(1, "Resuelto");
            stmtUpdate.setInt(2, reporteId);
            stmtUpdate.executeUpdate();

            // Verificar que el estado fue actualizado
            String sqlVerify = "SELECT Reporte_Estado FROM Reporte WHERE Reporte_ID = ?";
            PreparedStatement stmtVerify = conn.prepareStatement(sqlVerify);
            stmtVerify.setInt(1, reporteId);
            ResultSet rsVerify = stmtVerify.executeQuery();
            assertTrue(rsVerify.next());
            assertEquals("Resuelto", rsVerify.getString("Reporte_Estado"));

            // Limpiar datos de prueba
            String sqlDelete = "DELETE FROM Reporte WHERE Reporte_ID = ?";
            PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete);
            stmtDelete.setInt(1, reporteId);
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

            // Simular carga de notificaciones
            JPanel panelNotificaciones = new JPanel();
            panelUsuario.cargarNotificaciones(panelNotificaciones);

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
}