import org.junit.Test;

import static org.junit.Assert.*;


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
       public void testLoginFuncionarioExitoso() {
           PantallaLoginFuncionario pantalla = new PantallaLoginFuncionario();
           pantalla.setVisible(true);
           
           // 1. Configurar credenciales de prueba (deben existir en tu BD real)
           pantalla.campoCorreo.setText("prueba");  // Usar un correo que exista en tu BD
           pantalla.campoClave.setText("1");          // Usar una contraseña correcta
           
           // 2. Simular clic en el botón de login
           pantalla.btnLogin.doClick();
           x=campoClave.setText("1");
           // 3. Esperar un tiempo para el procesamiento
           try {
               Thread.sleep(2000); // Espera 2 segundos para el procesamiento
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           
           
}
}