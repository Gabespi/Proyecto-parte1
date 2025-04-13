import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelUsuario extends JFrame {
    private Usuario usuario; // Atributo para almacenar el usuario actual

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
        btnRegistrarReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir la ventana para registrar un nuevo reporte
                PantallaRegistrarReporte pantallaRegistrarReporte = new PantallaRegistrarReporte();
                pantallaRegistrarReporte.setVisible(true);
            }
        });

        // Tabla para mostrar los reportes del usuario
        String[] columnas = {"ID", "Descripción", "Fecha de Creación", "Estado"};
        Object[][] datos = {
                {1, "Bache en la calle principal", "01/10/2023 10:30:00", "En espera"},
                {2, "Fuga de agua en el parque", "02/10/2023 14:45:00", "En proceso"},
                {3, "Lámpara rota en la plaza", "03/10/2023 09:15:00", "Finalizado"}
        };

        JTable tablaReportes = new JTable(datos, columnas);
        tablaReportes.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaReportes.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(tablaReportes);
        scrollPane.setPreferredSize(new Dimension(700, 300));

        // Panel de notificaciones
        JPanel panelNotificaciones = new JPanel();
        panelNotificaciones.setLayout(new BoxLayout(panelNotificaciones, BoxLayout.Y_AXIS));
        panelNotificaciones.setBackground(Color.WHITE);
        panelNotificaciones.setBorder(BorderFactory.createTitledBorder("Notificaciones"));

        JLabel notificacion1 = new JLabel("El reporte #2 ha cambiado a 'En proceso'.");
        JLabel notificacion2 = new JLabel("El reporte #3 ha sido finalizado.");
        notificacion1.setFont(new Font("Arial", Font.PLAIN, 14));
        notificacion2.setFont(new Font("Arial", Font.PLAIN, 14));

        panelNotificaciones.add(notificacion1);
        panelNotificaciones.add(notificacion2);

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

    public static void main(String[] args) {
        // Crear un usuario de ejemplo
        Usuario usuarioEjemplo = new Usuario(1, "Juan Pérez", "juan.perez@example.com", "12345", "Usuario");

        SwingUtilities.invokeLater(() -> {
            PanelUsuario panel = new PanelUsuario(usuarioEjemplo);
            panel.setVisible(true);
        });
    }
}