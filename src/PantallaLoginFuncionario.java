import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PantallaLoginFuncionario extends JFrame {
    public JTextField campoCorreo; // Cambiado a atributo de clase
    public JPasswordField campoClave; // Cambiado a atributo de clase
    public JButton btnLogin; // Cambiado a atributo de clase

    public PantallaLoginFuncionario() {
        setTitle("Login Funcionarios");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        // Título
        JLabel titulo = new JLabel("Login Funcionarios");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Campo de correo
        campoCorreo = new JTextField();
        campoCorreo.setMaximumSize(new Dimension(300, 50));
        campoCorreo.setBorder(BorderFactory.createTitledBorder("Correo"));

        // Campo de clave
        campoClave = new JPasswordField();
        campoClave.setMaximumSize(new Dimension(300, 50));
        campoClave.setBorder(BorderFactory.createTitledBorder("Clave"));

        // Mensaje de error
        JLabel mensajeError = new JLabel(" ");
        mensajeError.setForeground(Color.RED);
        mensajeError.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón de login
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(new Color(33, 150, 243)); // Azul
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Acción del botón de login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String correo = campoCorreo.getText().trim();
                String clave = new String(campoClave.getPassword()).trim();

                if (correo.isEmpty() || clave.isEmpty()) {
                    mensajeError.setText("Por favor, complete todos los campos.");
                    return;
                }

                try (Connection conn = DatabaseConnection.getConnection()) {
                    // Paso 1: Verificar si el correo existe
                    String sqlCorreo = "SELECT * FROM Funcionario WHERE Funcionario_Correo = ?";
                    PreparedStatement stmtCorreo = conn.prepareStatement(sqlCorreo);
                    stmtCorreo.setString(1, correo);
                    ResultSet rsCorreo = stmtCorreo.executeQuery();

                    if (rsCorreo.next()) {
                        // Paso 2: Verificar si la contraseña es correcta
                        String sqlClave = "SELECT * FROM Funcionario WHERE Funcionario_Correo = ? AND Funcionario_Clave = ?";
                        PreparedStatement stmtClave = conn.prepareStatement(sqlClave);
                        stmtClave.setString(1, correo);
                        stmtClave.setString(2, clave);
                        ResultSet rsClave = stmtClave.executeQuery();

                        if (rsClave.next()) {
                            String nombre = rsClave.getString("Funcionario_Nombre");
                            JOptionPane.showMessageDialog(null, "Bienvenido, " + nombre);
                            dispose();
                            new VisorReportes().setVisible(true); // Abrir VisorReportes
                        } else {
                            mensajeError.setText("La contraseña es incorrecta.");
                        }
                    } else {
                        mensajeError.setText("El correo no está registrado.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + ex.getMessage());
                }
            }
        });

        // Agregar componentes al panel
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(campoCorreo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(campoClave);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(mensajeError);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnLogin);

        // Agregar panel a la ventana
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PantallaLoginFuncionario loginFuncionario = new PantallaLoginFuncionario();
            loginFuncionario.setVisible(true);
        });
    }
}