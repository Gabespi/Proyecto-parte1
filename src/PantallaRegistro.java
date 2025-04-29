import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PantallaRegistro extends JFrame {

    public PantallaRegistro() {
        // Configuración de la ventana
        setTitle("Registro de Usuario");
        setSize(512, 512);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal con BoxLayout
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(Color.decode("#F2EFE7"));

        // Panel de contenido dentro del JScrollPane
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(Color.decode("#F2EFE7"));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Margen interno

        // ScrollPane para permitir desplazamiento
        JScrollPane scrollPane = new JScrollPane(panelContenido);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Aumenta velocidad del scroll
        scrollPane.setBorder(null); // Sin borde extra
        add(scrollPane);

        // Etiqueta de título
        JLabel titulo = new JLabel("Crear Cuenta");
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelContenido.add(titulo);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 15)));

        // Campos de entrada
        JTextField campoNombre = crearCampoTexto("Nombre");
        JTextField campoApellidoPaterno = crearCampoTexto("Apellido Paterno");
        JTextField campoApellidoMaterno = crearCampoTexto("Apellido Materno");

        // Selección de Género
        JLabel labelGenero = new JLabel("Género:");
        labelGenero.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        labelGenero.setAlignmentX(Component.CENTER_ALIGNMENT);

        JRadioButton radioMasculino = new JRadioButton("Masculino");
        JRadioButton radioFemenino = new JRadioButton("Femenino");
        JRadioButton radioOtro = new JRadioButton("Otro");

        radioMasculino.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        radioFemenino.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        radioOtro.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        ButtonGroup grupoGenero = new ButtonGroup();
        grupoGenero.add(radioMasculino);
        grupoGenero.add(radioFemenino);
        grupoGenero.add(radioOtro);

        JPanel panelGenero = new JPanel();
        panelGenero.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0)); // Ajuste sin espacio innecesario
        panelGenero.setBackground(Color.decode("#F2EFE7"));
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
        mensajeError.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        // Botón "Crear Cuenta"
        JButton btnCrearCuenta = new JButton("Crear Cuenta");
        btnCrearCuenta.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btnCrearCuenta.setBackground(Color.decode("#27548A")); // Azul
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

                String primerNombre = campoNombre.getText().trim();
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
                if (primerNombre.isEmpty()) { campoNombre.setBackground(Color.PINK); hayErrores = true; }
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

                // Insertar en la base de datos
                try (Connection conn = DatabaseConnection.getConnection()) {
                    String sql = "INSERT INTO Usuarios (Usuario_Nombre, Usuario_Correo, Usuario_Contraseña, Usuario_Genero, Usuario_Telefono, Usuario_Primer_Nombre, Usuario_Apellido_Paterno, Usuario_Apellido_Materno) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, usuario);
                    stmt.setString(2, correo);
                    stmt.setString(3, contraseña);
                    stmt.setString(4, genero);
                    stmt.setString(5, telefono);
                    stmt.setString(6, primerNombre);
                    stmt.setString(7, apellidoPaterno);
                    stmt.setString(8, apellidoMaterno);
                    stmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "¡Cuenta creada con éxito!");
                    dispose();
                    new PantallaLogin().setVisible(true);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al registrar el usuario: " + ex.getMessage());
                }
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
        campo.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        return campo;
    }

    private JPasswordField crearCampoContraseña(String titulo) {
        JPasswordField campo = new JPasswordField();
        campo.setMaximumSize(new Dimension(300, 30));
        campo.setBorder(BorderFactory.createTitledBorder(titulo));
        campo.setFont(new Font("Times New Roman", Font.PLAIN, 14));
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
            PantallaRegistro registro = new PantallaRegistro();
            registro.setVisible(true);
        });
    }
}