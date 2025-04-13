import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PantallaRegistrarReporte extends JFrame { // Cambiado el nombre de la clase
    
    public PantallaRegistrarReporte() {
        // Configuración de la ventana
        setTitle("Registro de Usuario");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal con BoxLayout
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(Color.WHITE);

        // Panel de contenido dentro del JScrollPane
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Margen interno

        // ScrollPane para permitir desplazamiento
        JScrollPane scrollPane = new JScrollPane(panelContenido);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Aumenta velocidad del scroll
        scrollPane.setBorder(null); // Sin borde extra
        add(scrollPane);

        // Etiqueta de título
        JLabel titulo = new JLabel("Crear Cuenta");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelContenido.add(titulo);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 15)));

        // Campos de entrada
        JTextField campoNombre = crearCampoTexto("Nombre");
        JTextField campoApellidoPaterno = crearCampoTexto("Apellido Paterno");
        JTextField campoApellidoMaterno = crearCampoTexto("Apellido Materno");

        // Selección de Género
        JLabel labelGenero = new JLabel("Género:");
        labelGenero.setFont(new Font("Arial", Font.BOLD, 14));
        labelGenero.setAlignmentX(Component.CENTER_ALIGNMENT);

        JRadioButton radioMasculino = new JRadioButton("Masculino");
        JRadioButton radioFemenino = new JRadioButton("Femenino");
        JRadioButton radioOtro = new JRadioButton("Otro");

        ButtonGroup grupoGenero = new ButtonGroup();
        grupoGenero.add(radioMasculino);
        grupoGenero.add(radioFemenino);
        grupoGenero.add(radioOtro);

        JPanel panelGenero = new JPanel();
        panelGenero.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0)); // Ajuste sin espacio innecesario
        panelGenero.setBackground(Color.WHITE);
        panelGenero.add(radioMasculino);
        panelGenero.add(radioFemenino);
        panelGenero.add(radioOtro);

        JTextField campoTelefono = crearCampoTexto("Número de Teléfono");
        JTextField campoUsuario = crearCampoTexto("Nombre de Usuario");
        JTextField campoCorreo = crearCampoTexto("Correo Electrónico");
        JPasswordField campoPassword = crearCampoContraseña("Contraseña");
        JPasswordField campoPasswordConfirm = crearCampoContraseña("Confirmar Contraseña");

        // Mensaje de error
        JLabel mensajeError = new JLabel(" ");
        mensajeError.setForeground(Color.RED);
        mensajeError.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón "Crear Cuenta"
        JButton btnCrearCuenta = new JButton("Crear Cuenta");
        btnCrearCuenta.setFont(new Font("Arial", Font.BOLD, 14));
        btnCrearCuenta.setBackground(new Color(33, 150, 243)); // Azul
        btnCrearCuenta.setForeground(Color.WHITE);
        btnCrearCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Acción del botón "Crear Cuenta"
        btnCrearCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean hayErrores = false;

                // Resetea los colores antes de validar
                resetearColores(campoNombre, campoApellidoPaterno, campoApellidoMaterno,
                        campoTelefono, campoUsuario, campoCorreo, campoPassword, campoPasswordConfirm);

                String nombre = campoNombre.getText().trim();
                String apellidoPaterno = campoApellidoPaterno.getText().trim();
                String apellidoMaterno = campoApellidoMaterno.getText().trim();
                String telefono = campoTelefono.getText().trim();
                String usuario = campoUsuario.getText().trim();
                String correo = campoCorreo.getText().trim();
                String contraseña = new String(campoPassword.getPassword()).trim();
                String confirmarContraseña = new String(campoPasswordConfirm.getPassword()).trim();

                // Validar género seleccionado
                String genero = "";
                if (radioMasculino.isSelected()) genero = "Masculino";
                else if (radioFemenino.isSelected()) genero = "Femenino";
                else if (radioOtro.isSelected()) genero = "Otro";

                // Validaciones y cambio de color si hay error
                if (nombre.isEmpty()) { campoNombre.setBackground(Color.PINK); hayErrores = true; }
                if (apellidoPaterno.isEmpty()) { campoApellidoPaterno.setBackground(Color.PINK); hayErrores = true; }
                if (apellidoMaterno.isEmpty()) { campoApellidoMaterno.setBackground(Color.PINK); hayErrores = true; }
                if (genero.isEmpty()) { mensajeError.setText("Seleccione un género."); hayErrores = true; }
                if (telefono.isEmpty() || !telefono.matches("\\d{10}")) { campoTelefono.setBackground(Color.PINK); hayErrores = true; }
                if (usuario.isEmpty()) { campoUsuario.setBackground(Color.PINK); hayErrores = true; }
                if (correo.isEmpty() || !correo.contains("@") || !correo.contains(".")) { campoCorreo.setBackground(Color.PINK); hayErrores = true; }
                if (contraseña.isEmpty()) { campoPassword.setBackground(Color.PINK); hayErrores = true; }
                if (confirmarContraseña.isEmpty() || !contraseña.equals(confirmarContraseña)) { campoPasswordConfirm.setBackground(Color.PINK); hayErrores = true; }

                // Mostrar mensaje si hay errores
                if (hayErrores) {
                    mensajeError.setText("Corrige los campos en rojo.");
                    return;
                }

                // Registro exitoso
                mensajeError.setForeground(new Color(0, 128, 0)); // Verde éxito
                mensajeError.setText("Registro exitoso.");
                JOptionPane.showMessageDialog(null, "¡Cuenta creada con éxito!\nGénero: " + genero);

                dispose(); // Cierra la ventana actual
                PantallaLogin login = new PantallaLogin(); // Abre la pantalla de login
                login.setVisible(true);
            }
        });

        // Agregar componentes al panelContenido
        panelContenido.add(campoNombre);
        panelContenido.add(campoApellidoPaterno);
        panelContenido.add(campoApellidoMaterno);
        panelContenido.add(labelGenero);
        panelContenido.add(panelGenero);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 5))); // Se redujo espacio extra
        panelContenido.add(campoTelefono);
        panelContenido.add(campoUsuario);
        panelContenido.add(campoCorreo);
        panelContenido.add(campoPassword);
        panelContenido.add(campoPasswordConfirm);
        panelContenido.add(mensajeError);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 5))); // Espacio corregido
        panelContenido.add(btnCrearCuenta);
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
            PantallaRegistrarReporte registro = new PantallaRegistrarReporte();
            registro.setVisible(true);
        });
    }
}