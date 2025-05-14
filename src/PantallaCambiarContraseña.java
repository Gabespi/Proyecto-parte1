import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PantallaCambiarContraseña extends JFrame {

    private JTextField campoCorreo;
    private JPasswordField campoNuevaContraseña;
    private JPasswordField campoConfirmarContraseña;
    private JButton btnCambiarContraseña;

    public PantallaCambiarContraseña() {
        setTitle("Cambiar Contraseña");
        setSize(640, 512);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.decode("#F2EFE7"));

        JLabel titulo = new JLabel("Cambiar Contraseña");
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 20));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel mensajeSupervisor = new JLabel("Si eres funcionario, ponte en contacto con tu supervisor para cambiar tu contraseña.");
        mensajeSupervisor.setFont(new Font("Times New Roman", Font.ITALIC, 14));
        mensajeSupervisor.setAlignmentX(Component.CENTER_ALIGNMENT);
        mensajeSupervisor.setForeground(Color.GRAY);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(mensajeSupervisor);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        campoCorreo = new JTextField();
        campoCorreo.setMaximumSize(new Dimension(300, 30));
        campoCorreo.setBorder(BorderFactory.createTitledBorder("Correo Electrónico"));
        campoCorreo.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        campoNuevaContraseña = new JPasswordField();
        campoNuevaContraseña.setMaximumSize(new Dimension(300, 30));
        campoNuevaContraseña.setBorder(BorderFactory.createTitledBorder("Nueva Contraseña"));
        campoNuevaContraseña.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        campoConfirmarContraseña = new JPasswordField();
        campoConfirmarContraseña.setMaximumSize(new Dimension(300, 30));
        campoConfirmarContraseña.setBorder(BorderFactory.createTitledBorder("Confirmar Nueva Contraseña"));
        campoConfirmarContraseña.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        JLabel mensajeError = new JLabel(" ");
        mensajeError.setForeground(Color.RED);
        mensajeError.setAlignmentX(Component.CENTER_ALIGNMENT);
        mensajeError.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        btnCambiarContraseña = new JButton("Cambiar Contraseña");
        btnCambiarContraseña.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btnCambiarContraseña.setBackground(Color.decode("#27548A"));
        btnCambiarContraseña.setForeground(Color.WHITE);
        btnCambiarContraseña.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnCambiarContraseña.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String correo = campoCorreo.getText().trim();
                String nuevaContraseña = new String(campoNuevaContraseña.getPassword());
                String confirmarContraseña = new String(campoConfirmarContraseña.getPassword());

                if (!nuevaContraseña.equals(confirmarContraseña)) {
                    mensajeError.setText("Las contraseñas no coinciden.");
                    return;
                }

                // Generar hash de la nueva contraseña
                String nuevaContraseñaHash = HashUtil.hashPassword(nuevaContraseña);

                try (Connection conn = DatabaseConnection.getConnection()) {
                    String sqlActualizar = "UPDATE Usuarios SET Usuario_Contraseña = ? WHERE Usuario_Correo = ?";
                    PreparedStatement stmtActualizar = conn.prepareStatement(sqlActualizar);
                    stmtActualizar.setString(1, nuevaContraseñaHash); // Guardar el nuevo hash
                    stmtActualizar.setString(2, correo);

                    int filasActualizadas = stmtActualizar.executeUpdate();
                    if (filasActualizadas > 0) {
                        JOptionPane.showMessageDialog(null, "¡Contraseña cambiada con éxito!");
                        dispose();
                        new PantallaLogin().setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró una cuenta con el correo ingresado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al cambiar la contraseña: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(campoCorreo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(campoNuevaContraseña);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(campoConfirmarContraseña);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(mensajeError);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnCambiarContraseña);

        add(panel);
    }

    public JTextField getCampoCorreo() {
        return campoCorreo;
    }

    public JPasswordField getCampoNuevaContraseña() {
        return campoNuevaContraseña;
    }

    public JPasswordField getCampoConfirmarContraseña() {
        return campoConfirmarContraseña;
    }

    public JButton getBtnCambiarContraseña() {
        return btnCambiarContraseña;
    }
}

