import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
    private static String[] columnas = {"ID Reporte", "Categoría", "Descripción", "Ubicación", 
                                "Fecha", "Archivo Adjunto", "Estado"};
    
    private DefaultTableModel reporTableModel;
    private ArrayList<Reporte> reportes;
    private JTable tabla;

    public VisorReportes() {
        super("Visor de Reportes");
        
        reportes = new ArrayList<>();
        
        reporTableModel = new DefaultTableModel();
        for (String col : columnas) {
            reporTableModel.addColumn(col);
        }
        
        tabla = new JTable(reporTableModel);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        setLayout(new BorderLayout());
        
        JLabel titulo = new JLabel("Visor de Reportes", SwingConstants.CENTER);
        titulo.setFont(new Font("Consolas", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel botonesPanel = new JPanel();
        
        JButton recargarBtn = new JButton("Recargar");
        recargarBtn.addActionListener(e -> {
            cargarDatos();
        });
        
        JButton editarBtn = new JButton("Editar");
        editarBtn.addActionListener(e -> {
            editarReporte();
        });
        
        JButton eliminarBtn = new JButton("Eliminar");
        eliminarBtn.addActionListener(e -> {
            eliminarReporte();
        });
        
        botonesPanel.add(recargarBtn);
        botonesPanel.add(editarBtn);
        botonesPanel.add(eliminarBtn);
        
        add(botonesPanel, BorderLayout.SOUTH);
        
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        cargarDatos();
        
        setVisible(true);
    }
    
    private void cargarDatos() {
        reportes.clear();
        
        reportes.add(new Reporte(0000, "Ejemplo", "Este es un ejemplo de reporte", 
                "Lorem", "DD-MM-YYYY", "X", "Foo.pdf"));
        
        actualizarTabla();
    }
    
    private void actualizarTabla() {
        reporTableModel.setRowCount(0);
        
        for (Reporte r : reportes) {
            reporTableModel.addRow(new Object[]{
                r.getIdReporte(),
                r.getCategoria(),
                r.getDescripcion(),
                r.getUbicacion(),
                r.getFecha(),
                r.getArchivoAdjunto(),
                r.getEstado()
            });
        }
    }
    
    private void editarReporte() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un reporte para editar", 
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Reporte reporte = reportes.get(fila);
        JOptionPane.showMessageDialog(this, "Editando reporte ID: " + reporte.getIdReporte() + 
                "\nEsta función se implementará en futuros ejercicios.");
    }
    
    private void eliminarReporte() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un reporte para eliminar", 
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Reporte reporte = reportes.get(fila);
        
        int confirmar = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro que desea eliminar el reporte ID: " + reporte.getIdReporte() + "?", 
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirmar == JOptionPane.YES_OPTION) {
            reportes.remove(fila);
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Reporte eliminado con éxito.");
        }
    }

   
}