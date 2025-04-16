public class PantallaLogin {
    
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PantallaLogin extends JFrame {

    // Credenciales predefinidas para simulación
    private static final String USUARIO_VALIDO = "usuario123";
    private static final String EMAIL_VALIDO = "correo@ejemplo.com";
    private static final String CONTRASEÑA_VALIDA = "12345";

    public PantallaLogin() {
        // Configuración de la ventana
        setTitle("Sistema de Sugerencias y Quejas");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal con BoxLayout (organiza los elementos en columna)
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        // Etiqueta de título
        JLabel titulo = new JLabel("Sistema de Sugerencias y Quejas");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Etiqueta de descripción
        JLabel descripcion = new JLabel("<html><center>Reporta problemas de infraestructura pública de manera rápida y sencilla.</center></html>");
        descripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        descripcion.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Espaciador
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(descripcion);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Campo de usuario/correo
        JTextField campoUsuario = new JTextField();
        campoUsuario.setMaximumSize(new Dimension(300, 30));
        campoUsuario.setBorder(BorderFactory.createTitledBorder("Usuario o Correo"));

        // Campo de contraseña
        JPasswordField campoPassword = new JPasswordField();
        campoPassword.setMaximumSize(new Dimension(300, 30));
        campoPassword.setBorder(BorderFactory.createTitledBorder("Contraseña"));

        // Checkbox "Recordar Sesión"
        JCheckBox recordarSesion = new JCheckBox("Recordar sesión");
        recordarSesion.setBackground(Color.WHITE);
        recordarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Mensaje de error (por defecto oculto)
        JLabel mensajeError = new JLabel("Favor de introducir los datos correctamente.");
        mensajeError.setForeground(Color.RED);
        mensajeError.setVisible(false);
        mensajeError.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón "¿Olvidaste tu contraseña?"
        JButton btnRecuperar = new JButton("¿Olvidaste tu contraseña?");
        btnRecuperar.setBorderPainted(false);
        btnRecuperar.setContentAreaFilled(false);
        btnRecuperar.setForeground(Color.BLUE);
        btnRecuperar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());
        panelBotones.setBackground(Color.WHITE);

        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnRegistro = new JButton("Registrarse");

        // Estilos de botones
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(new Color(33, 150, 243)); // Azul
        btnLogin.setForeground(Color.WHITE);

        btnRegistro.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistro.setBackground(new Color(76, 175, 80)); // Verde
        btnRegistro.setForeground(Color.WHITE);

        panelBotones.add(btnLogin);
        panelBotones.add(btnRegistro);

        // Acción del botón de registrarse -> ABRE PantallaRegistro.java
        btnRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana actual
                PantallaRegistro registro = new PantallaRegistro();
                registro.setVisible(true);
            }
        });

        // Acción del botón de iniciar sesión
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = campoUsuario.getText().trim();
                String contraseña = new String(campoPassword.getPassword()).trim();

                if ((usuario.equals(USUARIO_VALIDO) || usuario.equals(EMAIL_VALIDO)) && contraseña.equals(CONTRASEÑA_VALIDA)) {
                    Usuario usuarioActual = new Usuario(1, "Juan Pérez", "correo@ejemplo.com", "12345", "Usuario");
                    dispose();
                    PanelUsuario panelUsuario = new PanelUsuario(usuarioActual);
                    panelUsuario.setVisible(true);
                } else {
                    mensajeError.setVisible(true);
                }
            }
        });

        // Acción del botón "¿Olvidaste tu contraseña?" -> ABRE PantallaCambiarContraseña.java
        btnRecuperar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana actual
                PantallaCambiarContraseña cambiarContraseña = new PantallaCambiarContraseña();
                cambiarContraseña.setVisible(true);
            }
        });

        // Agregar componentes al panel principal
        panel.add(campoUsuario);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(campoPassword);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(recordarSesion);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(mensajeError); // Mensaje de error en rojo
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnRecuperar);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(panelBotones);

        // Agregar panel a la ventana
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PantallaLogin login = new PantallaLogin();
            login.setVisible(true);
        });
    }
}

