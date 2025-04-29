
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PantallaLogin extends JFrame {

    // Hacemos estos campos públicos para poder hacer test
    public JTextField campoUsuario;
    public JPasswordField campoPassword;
    public JButton btnIniciarSesion;

    public PantallaLogin() {
        setTitle("Sistema de Sugerencias y Quejas");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Sistema de Sugerencias y Quejas");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descripcion = new JLabel("<html><center>Reporta problemas de infraestructura pública de manera rápida y sencilla.</center></html>");
        descripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        descripcion.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(descripcion);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        campoUsuario = new JTextField();
        campoUsuario.setMaximumSize(new Dimension(300, 30));
        campoUsuario.setBorder(BorderFactory.createTitledBorder("Usuario o Correo"));

        campoPassword = new JPasswordField();
        campoPassword.setMaximumSize(new Dimension(300, 30));
        campoPassword.setBorder(BorderFactory.createTitledBorder("Contraseña"));

        JCheckBox recordarSesion = new JCheckBox("Recordar sesión");
        recordarSesion.setBackground(Color.WHITE);
        recordarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel mensajeError = new JLabel("Favor de introducir los datos correctamente.");
        mensajeError.setForeground(Color.RED);
        mensajeError.setVisible(false);
        mensajeError.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnRecuperar = new JButton("¿Olvidaste tu contraseña?");
        btnRecuperar.setBorderPainted(false);
        btnRecuperar.setContentAreaFilled(false);
        btnRecuperar.setForeground(Color.BLUE);
        btnRecuperar.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());
        panelBotones.setBackground(Color.WHITE);

        btnIniciarSesion = new JButton("Iniciar Sesión");
        JButton btnRegistro = new JButton("Registrarse");

        btnIniciarSesion.setFont(new Font("Arial", Font.BOLD, 14));
        btnIniciarSesion.setBackground(new Color(33, 150, 243));
        btnIniciarSesion.setForeground(Color.WHITE);

        btnRegistro.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistro.setBackground(new Color(76, 175, 80));
        btnRegistro.setForeground(Color.WHITE);

        panelBotones.add(btnIniciarSesion);
        panelBotones.add(btnRegistro);

        btnRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                PantallaRegistro registro = new PantallaRegistro();
                registro.setVisible(true);
            }
        });

        btnIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = campoUsuario.getText().trim();
                String contraseña = new String(campoPassword.getPassword()).trim();

                try (Connection conn = DatabaseConnection.getConnection()) {
                    String sql = "SELECT * FROM Usuarios WHERE (Usuario_Nombre = ? OR Usuario_Correo = ?) AND Usuario_Contraseña = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, usuario);
                    stmt.setString(2, usuario);
                    stmt.setString(3, contraseña);
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

        btnRecuperar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                PantallaCambiarContraseña cambiarContraseña = new PantallaCambiarContraseña();
                cambiarContraseña.setVisible(true);
            }
        });

        panel.add(campoUsuario);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(campoPassword);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(recordarSesion);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(mensajeError);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnRecuperar);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(panelBotones);

        JButton btnLoginFuncionario = new JButton("Login Funcionarios");
        btnLoginFuncionario.setFont(new Font("Arial", Font.BOLD, 14));
        btnLoginFuncionario.setBackground(new Color(255, 152, 0));
        btnLoginFuncionario.setForeground(Color.WHITE);
        btnLoginFuncionario.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnLoginFuncionario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                PantallaLoginFuncionario loginFuncionario = new PantallaLoginFuncionario();
                loginFuncionario.setVisible(true);
            }
        });

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnLoginFuncionario);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PantallaLogin login = new PantallaLogin();
            login.setVisible(true);
        });
    }
}
