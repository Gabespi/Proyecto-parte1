
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PantallaCambiarContraseña extends JFrame {
    
    private static final String CONTRASEÑA_ACTUAL = "12345";

    public PantallaCambiarContraseña() {
        setTitle("Cambiar Contraseña");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Cambiar Contraseña");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPasswordField campoContraseñaActual = new JPasswordField();
        campoContraseñaActual.setMaximumSize(new Dimension(300, 30));
        campoContraseñaActual.setBorder(BorderFactory.createTitledBorder("Contraseña Actual"));

        JPasswordField campoNuevaContraseña = new JPasswordField();
        campoNuevaContraseña.setMaximumSize(new Dimension(300, 30));
        campoNuevaContraseña.setBorder(BorderFactory.createTitledBorder("Nueva Contraseña"));

        JPasswordField campoConfirmarContraseña = new JPasswordField();
        campoConfirmarContraseña.setMaximumSize(new Dimension(300, 30));
        campoConfirmarContraseña.setBorder(BorderFactory.createTitledBorder("Confirmar Nueva Contraseña"));

        JLabel mensajeError = new JLabel(" ");
        mensajeError.setForeground(Color.RED);
        mensajeError.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnCambiarContraseña = new JButton("Cambiar Contraseña");
        btnCambiarContraseña.setFont(new Font("Arial", Font.BOLD, 14));
        btnCambiarContraseña.setBackground(new Color(33, 150, 243));
        btnCambiarContraseña.setForeground(Color.WHITE);
        btnCambiarContraseña.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnCambiarContraseña.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contraseñaActual = new String(campoContraseñaActual.getPassword());
                String nuevaContraseña = new String(campoNuevaContraseña.getPassword());
                String confirmarContraseña = new String(campoConfirmarContraseña.getPassword());

                if (!nuevaContraseña.equals(confirmarContraseña)) {
                    mensajeError.setText("Las contraseñas no coinciden.");
                    return;
                }

                try (Connection conn = DatabaseConnection.getConnection()) {
                    String sql = "UPDATE Usuarios SET Usuario_Contraseña = ? WHERE Usuario_Contraseña = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, nuevaContraseña);
                    stmt.setString(2, contraseñaActual);

                    int filasActualizadas = stmt.executeUpdate();
                    if (filasActualizadas > 0) {
                        JOptionPane.showMessageDialog(null, "¡Contraseña cambiada con éxito!");
                        dispose();
                        new PantallaLogin().setVisible(true);
                    } else {
                        mensajeError.setText("La contraseña actual es incorrecta.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al cambiar la contraseña: " + ex.getMessage());
                }
            }
        });

        panel.add(campoContraseñaActual);
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
}
 
