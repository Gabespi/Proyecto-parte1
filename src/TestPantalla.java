import org.junit.Test;
import org.sqlite.Function.Window;

import static org.junit.Assert.*;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.Window;


public class TestPantalla{
    @Test
    public void testPantallaloginExitoso() {
        // Crear una instancia de la clase PantallaLogin
        PantallaLogin pantallaLogin = new PantallaLogin();
        pantallaLogin.setVisible(true);
        // Simular la entrada de usuario y contraseña correctos
        pantallaLogin.campoUsuario.setText("ejemplo@udalp.mx");
        pantallaLogin.campoPassword.setText("example");

        // Simular el clic en el botón de inicio de sesión
        pantallaLogin.btnIniciarSesion.doClick();
        try {
            Thread.sleep(1000); // Espera para procesamiento
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(pantallaLogin.isVisible()); // Verifica que la pantalla de inicio de sesión se haya cerrado
      
    }

    @Test
    public void testLoginFuncionarioBasico() {
        PantallaLoginFuncionario pantalla = new PantallaLoginFuncionario();
        pantalla.setVisible(true);
        
        pantalla.campoCorreo.setText("prueba");
        pantalla.campoClave.setText("1");
    
        pantalla.btnLogin.doClick();
        
        try {
            Thread.sleep(2000);
            // Verificar que la pantalla sigue visible (porque el diálogo está abierto)
            assertTrue(pantalla.isVisible());
            
            // Simular que el usuario cierra el diálogo
            pantalla.dispose();
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Test interrumpido");
        }
    }
}