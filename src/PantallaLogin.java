import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PantallaLogin extends JFrame {
    public JTextField campoUsuario; // Cambiado a atributo de clase
    public JPasswordField campoPassword; // Cambiado a atributo de clase
    public JButton btnLogin; // Cambiado a atributo de clase

    public PantallaLogin() {
        // Configuración de la ventana
        setTitle("Sistema de Sugerencias y Quejas");
        setSize(512, 512);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal con BoxLayout (organiza los elementos en columna)
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.decode("#F2EFE7"));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Márgenes para centrar

        // Etiqueta de título
        JLabel titulo = new JLabel("Sistema de Sugerencias y Quejas");
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 20));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Etiqueta de descripción
        JLabel descripcion = new JLabel("<html><center>Reporta problemas de infraestructura pública de manera rápida y sencilla.</center></html>");
        descripcion.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        descripcion.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Espaciador
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(descripcion);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Campo de usuario/correo
        campoUsuario = new JTextField();
        campoUsuario.setMaximumSize(new Dimension(300, 30));
        campoUsuario.setBorder(BorderFactory.createTitledBorder("Usuario o Correo"));
        campoUsuario.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panel.add(campoUsuario);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Campo de contraseña
        campoPassword = new JPasswordField();
        campoPassword.setMaximumSize(new Dimension(300, 30));
        campoPassword.setBorder(BorderFactory.createTitledBorder("Contraseña"));
        campoPassword.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panel.add(campoPassword);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Checkbox "Ver Contraseña"
        JCheckBox verContraseña = new JCheckBox("Ver Contraseña");
        verContraseña.setBackground(Color.decode("#F2EFE7"));
        verContraseña.setAlignmentX(Component.CENTER_ALIGNMENT);
        verContraseña.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        // Acción del checkbox "Ver Contraseña"
        verContraseña.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (verContraseña.isSelected()) {
                    campoPassword.setEchoChar((char) 0); // Muestra el texto
                } else {
                    campoPassword.setEchoChar('•'); // Oculta el texto
                }
            }
        });

        // Agregar el checkbox al panel
        panel.add(verContraseña);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Checkbox "Recordar Sesión"
        JCheckBox recordarSesion = new JCheckBox("Recordar sesión");
        recordarSesion.setBackground(Color.decode("#F2EFE7"));
        recordarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);
        recordarSesion.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panel.add(recordarSesion);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Mensaje de error (por defecto oculto)
        JLabel mensajeError = new JLabel("Favor de introducir los datos correctamente.");
        mensajeError.setForeground(Color.RED);
        mensajeError.setVisible(false);
        mensajeError.setAlignmentX(Component.CENTER_ALIGNMENT);
        mensajeError.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panel.add(mensajeError);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botón "¿Olvidaste tu contraseña?"
        JButton btnRecuperar = new JButton("¿Olvidaste tu contraseña?");
        btnRecuperar.setBorderPainted(false);
        btnRecuperar.setContentAreaFilled(false);
        btnRecuperar.setForeground(Color.BLUE);
        btnRecuperar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRecuperar.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panel.add(btnRecuperar);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.setBackground(Color.decode("#F2EFE7"));


        btnLogin = new JButton("Iniciar Sesión");
        JButton btnRegistro = new JButton("Registrarse");

        // Estilos de botones
        btnLogin.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btnLogin.setBackground(Color.decode("#27548A")); // Azul oscuro
        btnLogin.setForeground(Color.WHITE);

        btnRegistro.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btnRegistro.setBackground(Color.decode("#004f30")); // Verde
        btnRegistro.setForeground(Color.WHITE);

        panelBotones.add(btnLogin);
        panelBotones.add(btnRegistro);
        panelBotones.add(btnLogin);
        panelBotones.add(btnRegistro);
        panel.add(panelBotones);

        // Botón "Login Funcionarios"
        JButton btnLoginFuncionario = new JButton("Login Funcionarios");
        btnLoginFuncionario.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btnLoginFuncionario.setBackground(Color.decode("#f37c22")); // Naranja
        btnLoginFuncionario.setForeground(Color.WHITE);
        btnLoginFuncionario.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnLoginFuncionario);

        // Agregar panel a la ventana
        add(panel);

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

                // Generar el hash de la contraseña ingresada
                String contraseñaHash = HashUtil.hashPassword(contraseña);

                try (Connection conn = DatabaseConnection.getConnection()) {
                    String sql = "SELECT * FROM Usuarios WHERE (Usuario_Nombre = ? OR Usuario_Correo = ?) AND Usuario_Contraseña = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, usuario);
                    stmt.setString(2, usuario);
                    stmt.setString(3, contraseñaHash); // Comparar con el hash almacenado
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        Usuario usuarioActual = new Usuario(
                            rs.getInt("Usuario_ID"),
                            rs.getString("Usuario_Nombre"),
                            rs.getString("Usuario_Correo"),
                            rs.getString("Usuario_Contraseña"),
                            "Usuario"
                        );
                        dispose();
                        new PanelUsuario(usuarioActual).setVisible(true);
                    } else {
                        mensajeError.setText("Credenciales incorrectas.");
                        mensajeError.setVisible(true);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + ex.getMessage());
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

        btnLoginFuncionario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana actual
                PantallaLoginFuncionario loginFuncionario = new PantallaLoginFuncionario();
                loginFuncionario.setVisible(true);
            }
        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PantallaLogin login = new PantallaLogin();
            login.setVisible(true);
        });
    }
}

