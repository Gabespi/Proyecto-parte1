import java.awt.*;
import javax.swing.*;

public class FormularioReporte extends JFrame {
    // Form fields
    private JTextField idField;
    private JTextField numFolioField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField fileField;
    private JComboBox<String> categoryCombo;
    private JTextArea contentArea;
    private JLabel dateLabel;
    
    private ReporteController controller;
    
    public FormularioReporte() {
        super("Formulario de Reporte");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        
        controller = new ReporteController();
        
        initComponents();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(8, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel idPanel = ComponentesFormulario.createFormFieldPanel("ID Reporte:");
        idField = new JTextField(20);
        idPanel.add(idField);
        
        JPanel numFolioPanel = ComponentesFormulario.createFormFieldPanel("Numero de Folio:");
        numFolioField = new JTextField(20);
        numFolioPanel.add(numFolioField);
        
        JPanel titlePanel = ComponentesFormulario.createFormFieldPanel("Asunto del Reporte:");
        titleField = new JTextField(20);
        titlePanel.add(titleField);
        
        JPanel authorPanel = ComponentesFormulario.createFormFieldPanel("Nombre del Autor:");
        authorField = new JTextField(20);
        authorPanel.add(authorField);
        
        JPanel categoryPanel = ComponentesFormulario.createFormFieldPanel("Categoría:");
        categoryCombo = new JComboBox<>(ReporteController.CATEGORIES);
        categoryPanel.add(categoryCombo);

        JPanel filePanel = ComponentesFormulario.createFormFieldPanel("Archivo Adjunto:");
        fileField = new JTextField(20);
        filePanel.add(fileField);


        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(new JLabel("Descripción de la Sitaución:"), BorderLayout.NORTH);
        contentArea = new JTextArea(5, 20);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(contentArea);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        JButton submitButton = new JButton("Subir Reporte");
        JButton clearButton = new JButton("Vaciar Formulario");
        JPanel buttonPanel = ComponentesFormulario.createButtonPanel(submitButton, clearButton);
        
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        dateLabel = new JLabel("Fecha: " + controller.getCurrentDate());
        datePanel.add(dateLabel);
        
        mainPanel.add(datePanel);
        mainPanel.add(idPanel);
        mainPanel.add(numFolioPanel);
        mainPanel.add(titlePanel);
        mainPanel.add(authorPanel);
        mainPanel.add(categoryPanel);
        mainPanel.add(contentPanel);
        mainPanel.add(filePanel);  
        mainPanel.add(buttonPanel);
       
        
        submitButton.addActionListener(e -> submitReport());
        clearButton.addActionListener(e -> clearForm());
        
        add(mainPanel);
    }
    
    private void submitReport() {
        Reporte report = new Reporte();
        report.setIdReporte(idField.getText());
        report.setNumFolio(numFolioField.getText());
        report.setTitle(titleField.getText());
        report.setAutor(authorField.getText());
        report.setCategoria((String) categoryCombo.getSelectedItem());
        report.setContenido(contentArea.getText());
        report.setArchivoAdjunto(fileField.getText());
        report.setDate(controller.getCurrentDate());
        
        if (!controller.validateReport(report)) {
            JOptionPane.showMessageDialog(this, 
                "Por favor llenar todos los campos", 
                "Formulario incompleto", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (controller.saveReport(report)) {
            JOptionPane.showMessageDialog(this, 
                report.toString(),
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Hubo un error registrando el formulario. Por favor, inténtelo de nuevo", 
                "Error de Guardado", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearForm() {
        idField.setText("");
        numFolioField.setText("");
        titleField.setText("");
        authorField.setText("");
        categoryCombo.setSelectedIndex(0);
        contentArea.setText("");
        idField.requestFocus();
    }
}