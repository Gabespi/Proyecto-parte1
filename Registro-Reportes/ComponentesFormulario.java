import java.awt.*;
import javax.swing.*;

public class ComponentesFormulario {
    
    public static JPanel createFormFieldPanel(String labelText) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(labelText));
        return panel;
    }
    
   
    public static JPanel createTextFieldPanel(String labelText, int columns) {
        JPanel panel = createFormFieldPanel(labelText);
        JTextField textField = new JTextField(columns);
        panel.add(textField);
        return new ComponentPair<>(panel, textField);
    }
    
  
    public static JPanel createComboBoxPanel(String labelText, String[] options) {
        JPanel panel = createFormFieldPanel(labelText);
        JComboBox<String> comboBox = new JComboBox<>(options);
        panel.add(comboBox);
        return new ComponentPair<>(panel, comboBox);
    }
    
 
    public static JPanel createButtonPanel(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }
    
    public static class ComponentPair<T extends JComponent> extends JPanel {
        private final T component;
        
        public ComponentPair(JPanel panel, T component) {
            this.component = component;
            this.setLayout(new BorderLayout());
            this.add(panel, BorderLayout.CENTER);
        }
        
        public T getComponent() {
            return component;
        }
    }
}
