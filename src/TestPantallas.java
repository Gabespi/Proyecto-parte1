import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.swing.*;
import java.awt.*;
import java.util.function.Predicate;

public class TestPantallas {

    private PantallaLogin pantalla;

    @Before
    public void setUp() {
        pantalla = new PantallaLogin();
        pantalla.setVisible(true);
    }

    @Test
    public void InicioDeSesion() {
        JTextField campoUsuario = (JTextField) findComponent(pantalla, JTextField.class);
        JPasswordField campoPassword = (JPasswordField) findComponent(pantalla, JPasswordField.class);
        campoUsuario.setText("usuario123");
        campoPassword.setText("12345");

        JButton btnLogin = (JButton) findComponent(pantalla, "Iniciar Sesión");
        btnLogin.doClick();

        JLabel mensajeError = (JLabel) findComponent(pantalla, JLabel.class,
                comp -> comp.getForeground().equals(Color.RED));
        assertFalse(mensajeError.isVisible());
    }

    
}
