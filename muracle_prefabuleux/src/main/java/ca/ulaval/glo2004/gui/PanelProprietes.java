package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.classes.Imperial;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PanelProprietes extends JPanel
{

    private JButton collapseButton;
    private JPanel contentPanel;
    private ArrayList<Property> properties = new ArrayList<>();
    private HashMap<String, String> values = new HashMap<>();
    private ArrayList<String> errors = new ArrayList<>();

    private PropertyChangeListener listener;

    public PanelProprietes(String label, int minimumHeight)
    {
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(new Color(-8882056));
        this.setMinimumSize(new Dimension(221, minimumHeight));
        this.setPreferredSize(new Dimension(221, minimumHeight));

        collapseButton = new JButton();
        collapseButton.setBackground(new Color(-8224126));
        collapseButton.setBorderPainted(false);
        collapseButton.setFocusPainted(false);
        collapseButton.setHideActionText(true);
        collapseButton.setHorizontalAlignment(2);
        collapseButton.setHorizontalTextPosition(10);
        //collapseButton.setIcon(new ImageIcon(getClass().getResource("/buttons/collapse-up.png")));
        //collapseButton.setIconTextGap(0);
        collapseButton.setText(label);
        this.add(collapseButton, BorderLayout.NORTH);
        /*this.collapseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean isActivated = !contentPanel.isVisible();

                contentPanel.setVisible(isActivated);
                contentPanel.setMinimumSize(isActivated ? new Dimension(221, 240) : new Dimension(221, 20));
                System.out.println("CLICK");
            }
        });*/

        contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        contentPanel.setBackground(new Color(-4408132));
        this.add(contentPanel, BorderLayout.CENTER);
    }

    public void generateLayout()
    {
        contentPanel.removeAll();
        for(Property property : properties)
        {
            JPanel container = new JPanel();
            container.setLayout(new BorderLayout(0, 0));
            container.setBackground(new Color(-4408132));
            container.setPreferredSize(new Dimension(200, 24));
            contentPanel.add(container);

            final JLabel label = new JLabel();
            label.setBackground(new Color(-4408132));
            label.setForeground(new Color(-16777216));
            label.setText(property.label);
            container.add(label, BorderLayout.WEST);

            JTextField textField = new JTextField();
            textField.setBackground(new Color(-1));
            textField.setForeground(new Color(-16777216));
            textField.setPreferredSize(new Dimension(60, 30));
            textField.setText(property.getDefaultValue());
            textField.setEditable(!property.readOnly);
            textField.addActionListener(e -> {
                _onValueChange(property, textField.getText());
            });
            property.setTextField(textField);
            container.add(textField, BorderLayout.EAST);
        }
    }

    private void _onValueChange(Property property, String value)
    {
        values.put(property.name, value);
        listener.OnValueChange(getValues());
        updateValues();
    }

    public void setOnChangeListener(PropertyChangeListener listener)
    {
        this.listener = listener;
    }

    public void updateValues()
    {
        for (Property property : properties)
        {
            String newValue = values.get(property.name);
            property.textField.setText(newValue);

            Color textColor = hasError(property.name) ? Color.red : new Color(-16777216);
            property.textField.setForeground(textColor);
        }
    }

    public void addProperty(Property property)
    {
        this.properties.add(property);
        this.values.put(property.name, property.defaultValue);
    }

    public void addProperty(String name, String label, String defaultValue, boolean readOnly)
    {
        this.addProperty(new Property(name, label, defaultValue, readOnly));
    }

    public void addProperty(String name, String label)
    {
        this.addProperty(name, label, "", false);
    }

    public void removeProperty(String name)
    {
        for(int i = 0; i < properties.size(); i++)
        {
            String testName = properties.get(i).name;
            if(testName.equalsIgnoreCase(name))
            {
                properties.remove(i);
            }
        }
    }

    public String getValue(String propertyName)
    {
        if(!values.containsKey(propertyName))
            return null;

        return values.get(propertyName);
    }

    public int getInt(String propertyName)
    {
        if(!values.containsKey(propertyName))
            return -1;

        String value = values.get(propertyName);
        try
        {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException err)
        {
            return -1;
        }
    }

    public Imperial getImperial(String propertyName)
    {
        if(!values.containsKey(propertyName))
            return null;

        String value = values.get(propertyName);
        Imperial valueImp = Imperial.fromString(value);
        boolean isValid = valueImp != null;
        setError(propertyName, !isValid);

        return valueImp;
    }

    public void setValue(String propertyName, String value)
    {
        values.put(propertyName, value);
    }

    public boolean hasError(String propertyName)
    {
        return errors.contains(propertyName);
    }

    public boolean setError(String propertyName, boolean error)
    {
        if(error == hasError(propertyName))
            return error;

        if(error)
            errors.add(propertyName);
        else
            errors.remove(propertyName);

        return error;
    }

    public HashMap<String, String> getValues()
    {
        return (HashMap<String, String>) values.clone();
    }

    public class Property
    {
        private String name;
        private String label;
        private String defaultValue;

        private boolean readOnly = false;

        private JTextField textField;

        public Property(String name, String label, String defaultValue, boolean readOnly) {
            this.name = name;
            this.label = label;
            this.defaultValue = defaultValue;
            this.readOnly = readOnly;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String value) {
            this.defaultValue = value;
        }

        public JTextField getTextField() {
            return textField;
        }

        public void setTextField(JTextField textField) {
            this.textField = textField;
        }
    }

    public interface PropertyChangeListener
    {
        public abstract void OnValueChange(HashMap<String, String> values);
    }

}
