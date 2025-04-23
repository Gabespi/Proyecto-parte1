import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Reporte {

    private int idReporte;
    private String categoria;
    private String descripcion;
    private String ubicacion;
    private String fecha;
    private String estado;
    private String archivoAdjunto;

   
    public Reporte(int idReporte, String categoria, String descripcion, String ubicacion, 
                  String fecha, String estado, String archivoAdjunto) {
        this.idReporte = idReporte;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.estado = estado;
        this.archivoAdjunto = archivoAdjunto;
    }

    public int getIdReporte() {
        return idReporte; 
 }
    
    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getArchivoAdjunto() {
        return archivoAdjunto;
    }
    public void setArchivoAdjunto(String archivoAdjunto) {
        this.archivoAdjunto = archivoAdjunto;
    }
    
    public String getUbicacion() {
        return ubicacion;
    }
    
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    public String getFecha() {
        return fecha;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
}

public class VisorReportes extends JFrame {
    private static final String[] columnas = {"ID Reporte", "Categoría", "Descripción", "Ubicación", 
                                              "Fecha", "Archivo Adjunto", "Estado", "ID Usuario"};
    private DefaultTableModel modeloTabla;
    private JTable tabla;

    public VisorReportes() {
        super("Visor de Reportes - Funcionario");

        // Configuración de la ventana
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configuración de la tabla
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(tabla);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnRecargar = new JButton("Recargar");
        JButton btnEditar = new JButton("Editar Reporte");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        btnRecargar.addActionListener(e -> cargarDatos());
        btnEditar.addActionListener(e -> editarReporte());
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new PantallaLogin().setVisible(true);
        });

        panelBotones.add(btnRecargar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnCerrarSesion);

        // Layout principal
        setLayout(new BorderLayout());
        add(new JLabel("Visor de Reportes - Funcionario", SwingConstants.CENTER), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Cargar datos al iniciar
        cargarDatos();

        // Configurar WAL
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.createStatement().execute("PRAGMA journal_mode=WAL;");
        } catch (SQLException ex) {
            System.err.println("Error al configurar WAL: " + ex.getMessage());
        }
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0); // Limpiar la tabla

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT Reporte_ID, Reporte_Categoria, Reporte_Descripcion, Reporte_Ubicacion, " +
                         "Reporte_Fecha_De_Creacion, Reporte_Ruta_Archivos, Reporte_Estado, Reporte_ID_Usuario " +
                         "FROM Reporte";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getInt("Reporte_ID"),
                    rs.getString("Reporte_Categoria"),
                    rs.getString("Reporte_Descripcion"),
                    rs.getString("Reporte_Ubicacion"),
                    rs.getString("Reporte_Fecha_De_Creacion"),
                    rs.getString("Reporte_Ruta_Archivos"),
                    rs.getString("Reporte_Estado"),
                    rs.getInt("Reporte_ID_Usuario")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los reportes: " + ex.getMessage());
        }
    }

    private void editarReporte() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un reporte para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener los datos del reporte seleccionado
        int idReporte = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String categoria = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        String descripcion = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
        String ubicacion = (String) modeloTabla.getValueAt(filaSeleccionada, 3);
        String archivoAdjunto = (String) modeloTabla.getValueAt(filaSeleccionada, 5);
        String estado = (String) modeloTabla.getValueAt(filaSeleccionada, 6);

        // Mostrar un cuadro de diálogo para editar los datos
        JTextField campoCategoria = new JTextField(categoria);
        JTextField campoDescripcion = new JTextField(descripcion);
        JTextField campoUbicacion = new JTextField(ubicacion);
        JTextField campoArchivoAdjunto = new JTextField(archivoAdjunto);
        JTextField campoEstado = new JTextField(estado);

        Object[] mensaje = {
            "Categoría:", campoCategoria,
            "Descripción:", campoDescripcion,
            "Ubicación:", campoUbicacion,
            "Archivo Adjunto:", campoArchivoAdjunto,
            "Estado:", campoEstado
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Editar Reporte", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                // Actualizar el reporte
                String sql = "UPDATE Reporte SET Reporte_Categoria = ?, Reporte_Descripcion = ?, " +
                             "Reporte_Ubicacion = ?, Reporte_Ruta_Archivos = ?, Reporte_Estado = ? " +
                             "WHERE Reporte_ID = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, campoCategoria.getText().trim());
                    stmt.setString(2, campoDescripcion.getText().trim());
                    stmt.setString(3, campoUbicacion.getText().trim());
                    stmt.setString(4, campoArchivoAdjunto.getText().trim());
                    stmt.setString(5, campoEstado.getText().trim());
                    stmt.setInt(6, idReporte);
                    stmt.executeUpdate();
                }

                // Registrar la notificación
                String mensajeNotificacion = "El reporte #" + idReporte + " ha sido actualizado.";
                String sqlNotificacion = "INSERT INTO Notificaciones (Reporte_ID, Usuario_ID, Mensaje) VALUES (?, ?, ?)";
                try (PreparedStatement stmtNotificacion = conn.prepareStatement(sqlNotificacion)) {
                    stmtNotificacion.setInt(1, idReporte);
                    stmtNotificacion.setInt(2, obtenerIdUsuarioPorReporte(conn, idReporte));
                    stmtNotificacion.setString(3, mensajeNotificacion);
                    stmtNotificacion.executeUpdate();
                }

                JOptionPane.showMessageDialog(this, "Reporte actualizado con éxito.");
                cargarDatos(); // Recargar los datos
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar el reporte: " + ex.getMessage());
            }
        }
    }

    private int obtenerIdUsuarioPorReporte(Connection conn, int idReporte) throws SQLException {
        String sql = "SELECT Reporte_ID_Usuario FROM Reporte WHERE Reporte_ID = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idReporte);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("Reporte_ID_Usuario");
        }
        return -1; // Retorna -1 si no se encuentra el usuario
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VisorReportes().setVisible(true));
    }
}