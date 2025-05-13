import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PanelUsuario extends JFrame {

    private Usuario usuario; // Atributo para almacenar el usuario actual
    private JTable tablaReportes; // Tabla para mostrar los reportes
    private JPanel panelNotificaciones; // Panel de notificaciones

    public PanelUsuario(Usuario usuario) {
        this.usuario = usuario; // Recibir el usuario como parámetro

 
        // Configuración de la ventana
        setTitle("Panel de Usuario");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal con BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Color.WHITE);


        // Panel superior con el título y datos del usuario
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(33, 150, 243)); // Azul
        panelSuperior.setPreferredSize(new Dimension(800, 60));
        panelSuperior.setLayout(new BorderLayout());
 

        JLabel titulo = new JLabel("Panel de Usuario");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);

        panelSuperior.add(titulo);

        titulo.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel datosUsuario = new JLabel("Bienvenido, " + usuario.getNombre() + " (" + usuario.getRol() + ")");
        datosUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
        datosUsuario.setForeground(Color.WHITE);
        datosUsuario.setHorizontalAlignment(SwingConstants.RIGHT);

        panelSuperior.add(titulo, BorderLayout.WEST);
        panelSuperior.add(datosUsuario, BorderLayout.EAST);
 

        // Panel central con las funcionalidades
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(Color.WHITE);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botón para registrar reportes
        JButton btnRegistrarReporte = new JButton("Registrar Nuevo Reporte");
        btnRegistrarReporte.setFont(new Font("Arial", Font.BOLD, 16));
        btnRegistrarReporte.setBackground(new Color(76, 175, 80)); // Verde
        btnRegistrarReporte.setForeground(Color.WHITE);
        btnRegistrarReporte.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegistrarReporte.setMaximumSize(new Dimension(300, 50));

        // Acción del botón "Registrar Nuevo Reporte"
        btnRegistrarReporte.addActionListener(e -> {
            // Abrir la ventana para registrar un nuevo reporte
            PantallaRegistrarReporte pantallaRegistrarReporte = new PantallaRegistrarReporte(usuario);
            pantallaRegistrarReporte.setVisible(true);
        });

        // Tabla para mostrar los reportes del usuario
        String[] columnas = {"ID", "Categoría", "Descripción", "Ubicación", "Estado", "Fecha de Creación"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        tablaReportes = new JTable(modeloTabla);
        tablaReportes.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaReportes.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(tablaReportes);
        scrollPane.setPreferredSize(new Dimension(700, 300));

        // Cargar los reportes desde la base de datos
        cargarReportes(modeloTabla);

        // Panel de notificaciones
        panelNotificaciones = new JPanel();
        panelNotificaciones.setLayout(new BoxLayout(panelNotificaciones, BoxLayout.Y_AXIS));
        panelNotificaciones.setBackground(Color.WHITE);
        panelNotificaciones.setBorder(BorderFactory.createTitledBorder("Notificaciones"));

        cargarNotificaciones(panelNotificaciones);

        // Agregar componentes al panel central
        panelCentral.add(btnRegistrarReporte);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        panelCentral.add(scrollPane);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        panelCentral.add(panelNotificaciones);

        // Agregar paneles al panel principal
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        // Agregar panel principal a la ventana
        add(panelPrincipal);
    }

    private void cargarReportes(DefaultTableModel modeloTabla) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT Reporte_ID, Reporte_Categoria, Reporte_Descripcion, Reporte_Ubicacion, Reporte_Estado, Reporte_Fecha_De_Creacion " +
                         "FROM Reporte WHERE Reporte_ID_Usuario = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usuario.getIdUsuario());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                        rs.getInt("Reporte_ID"),
                        rs.getString("Reporte_Categoria"),
                        rs.getString("Reporte_Descripcion"),
                        rs.getString("Reporte_Ubicacion"),
                        rs.getString("Reporte_Estado"),
                        rs.getString("Reporte_Fecha_De_Creacion")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los reportes: " + ex.getMessage());
        }
    }

    public void cargarNotificaciones(JPanel panelNotificaciones) {
        panelNotificaciones.removeAll(); // Limpiar notificaciones previas

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT Mensaje, Fecha FROM Notificaciones WHERE Usuario_ID = ? ORDER BY Fecha DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usuario.getIdUsuario());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String mensaje = rs.getString("Mensaje");
                String fecha = rs.getString("Fecha");

                JLabel notificacion = new JLabel(fecha + ": " + mensaje);
                notificacion.setFont(new Font("Arial", Font.PLAIN, 14));
                panelNotificaciones.add(notificacion);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar las notificaciones: " + ex.getMessage());
        }

        panelNotificaciones.revalidate();
        panelNotificaciones.repaint();
    }

    public JPanel getPanelNotificaciones() {
        return panelNotificaciones;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear un usuario de ejemplo
            Usuario usuarioEjemplo = new Usuario(1, "Juan Pérez", "juan.perez@example.com", "12345", "Usuario");

            // Crear y mostrar el panel de usuario
            PanelUsuario panel = new PanelUsuario(usuarioEjemplo);
            panel.setVisible(true);
        });
    }
}