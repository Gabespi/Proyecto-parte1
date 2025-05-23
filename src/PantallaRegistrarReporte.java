import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PantallaRegistrarReporte extends JFrame {

    private JComboBox<String> comboCategoria;
    private JTextArea campoDescripcion;
    private JTextArea campoUbicacion;
    private JTextArea campoRutaArchivos;
    private JButton btnRegistrar;

    public PantallaRegistrarReporte(Usuario usuario) {
        // Configuración de la ventana
        setTitle("Registrar Reporte");
        setSize(512, 512);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.decode("#F2EFE7"));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Márgenes para centrar

        // Título
        JLabel titulo = new JLabel("Registrar Reporte");
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 20));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ComboBox para seleccionar la categoría
        JLabel labelCategoria = new JLabel("Categoría:");
        labelCategoria.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        labelCategoria.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] categorias = {"Baches", "Alumbrado", "Basura", "Fugas", "Circulación", "Otro"};
        comboCategoria = new JComboBox<>(categorias);
        comboCategoria.setMaximumSize(new Dimension(300, 30));
        comboCategoria.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        // Campos de texto
        campoDescripcion = crearCampoTextoArea("Descripción");
        campoUbicacion = crearCampoTextoArea("Ubicación");
        campoRutaArchivos = crearCampoTextoArea("Ruta de Archivos");

        // Botón de registrar
        btnRegistrar = new JButton("Registrar Reporte");
        btnRegistrar.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btnRegistrar.setBackground(Color.decode("#27548A")); // Azul
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

        // Agregar componentes al panel
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(labelCategoria);
        panel.add(comboCategoria);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(campoDescripcion);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(campoUbicacion);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(campoRutaArchivos);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnRegistrar);

        // Agregar panel a la ventana
        add(panel);
    }

    // Método para crear un JTextArea con mayor altura
    private JTextArea crearCampoTextoArea(String titulo) {
        JTextArea campo = new JTextArea(3, 20); // 3 filas de altura
        campo.setLineWrap(true);
        campo.setWrapStyleWord(true);
        campo.setBorder(BorderFactory.createTitledBorder(titulo));
        campo.setMaximumSize(new Dimension(300, 80)); // Ajustar el tamaño máximo
        campo.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        return campo;
    }

    public JComboBox<String> getComboCategoria() {
        return comboCategoria;
    }

    public JTextArea getCampoDescripcion() {
        return campoDescripcion;
    }

    public JTextArea getCampoUbicacion() {
        return campoUbicacion;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PantallaRegistrarReporte reporte = new PantallaRegistrarReporte(new Usuario());
            reporte.setVisible(true);
        });
    }
}
