
public class PantallaRegistrarReporte {
    
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PantallaRegistrarReporte extends JFrame { // Cambiado el nombre de la clase
    
    public PantallaRegistrarReporte(Usuario usuario) { // Recibe el usuario actual
        setTitle("Registrar Reporte"); // Cambiar el título
        setSize(400, 500); // Aumentar el tamaño de la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Registrar Reporte");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ComboBox para seleccionar la categoría
        JLabel labelCategoria = new JLabel("Categoría:");
        labelCategoria.setFont(new Font("Arial", Font.BOLD, 14));
        labelCategoria.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] categorias = {"Baches", "Alumbrado", "Basura", "Fugas", "Circulación", "Otro"};
        JComboBox<String> comboCategoria = new JComboBox<>(categorias);
        comboCategoria.setMaximumSize(new Dimension(300, 30));

        // Campos de texto con mayor altura
        JTextArea campoDescripcion = crearCampoTextoArea("Descripción");
        JTextArea campoUbicacion = crearCampoTextoArea("Ubicación");
        JTextArea campoRutaArchivos = crearCampoTextoArea("Ruta de Archivos");

        JButton btnRegistrar = new JButton("Registrar Reporte");
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.setBackground(new Color(33, 150, 243));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoria = (String) comboCategoria.getSelectedItem();
                String descripcion = campoDescripcion.getText().trim();
                String ubicacion = campoUbicacion.getText().trim();
                String rutaArchivos = campoRutaArchivos.getText().trim();

                if (descripcion.isEmpty() || ubicacion.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos obligatorios.");
                    return;
                }

                try (Connection conn = DatabaseConnection.getConnection()) {
                    String sql = "INSERT INTO Reporte (Reporte_Categoria, Reporte_Descripcion, Reporte_Ubicacion, Reporte_Ruta_Archivos, Reporte_Notificacion, Reporte_Estado, Reporte_Fecha_De_Creacion, Reporte_ID_Usuario) VALUES (?, ?, ?, ?, ?, ?, datetime('now'), ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, categoria);
                    stmt.setString(2, descripcion);
                    stmt.setString(3, ubicacion);
                    stmt.setString(4, rutaArchivos);
                    stmt.setString(5, ""); // Notificación vacía por defecto
                    stmt.setString(6, "En espera"); // Estado inicial
                    stmt.setInt(7, usuario.getIdUsuario()); // ID del usuario actual
                    stmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "¡Reporte registrado con éxito!");
                    dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al registrar el reporte: " + ex.getMessage());
                }
            }
        });

        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(labelCategoria);
        panel.add(comboCategoria);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(campoDescripcion);
        panel.add(campoUbicacion);
        panel.add(campoRutaArchivos);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnRegistrar);

        add(panel);
    }

    // Método para crear un JTextArea con mayor altura
    private JTextArea crearCampoTextoArea(String titulo) {
        JTextArea campo = new JTextArea(3, 20); // 3 filas de altura
        campo.setLineWrap(true);
        campo.setWrapStyleWord(true);
        campo.setBorder(BorderFactory.createTitledBorder(titulo));
        campo.setMaximumSize(new Dimension(300, 80)); // Ajustar el tamaño máximo
        return campo;
    }

    // Métodos para crear campos de texto con bordes personalizados
    private JTextField crearCampoTexto(String titulo) {
        JTextField campo = new JTextField();
        campo.setMaximumSize(new Dimension(300, 30));
        campo.setBorder(BorderFactory.createTitledBorder(titulo));
        return campo;
    }

    private JPasswordField crearCampoContraseña(String titulo) {
        JPasswordField campo = new JPasswordField();
        campo.setMaximumSize(new Dimension(300, 30));
        campo.setBorder(BorderFactory.createTitledBorder(titulo));
        return campo;
    }

    // Método para resetear colores antes de validar
    private void resetearColores(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setBackground(Color.WHITE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PantallaRegistrarReporte Reporte = new PantallaRegistrarReporte(new Usuario());
            Reporte.setVisible(true);
        });
    }
}
