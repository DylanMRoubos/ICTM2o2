package site.nerdygadgets.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ComponentManagementPanel extends JPanel {
    private JLabel jlCurrentComponents;
    private JPanel jpCurrentComponents;
    private JPanel jpCurrentComponentsContent;

    private JLabel jlAddComponents;
    private JPanel jpAddComponents;
    private JPanel jpAddComponentsLayout;
    private JPanel jpAddComponentsContent;

    //Huidigecomponents content
    private JTable jTable;
    private DefaultTableModel tableModel;
    private JScrollPane tableScrollPane;

    //ComponentToevoegen content
    private JLabel jlName;
    private JTextField jtName;
    private JLabel jlPrice;
    private JTextField jtPrice;
    private JLabel jlAvailability;
    private JTextField jtAvailability;
    String[] components = {"Database", "Webserver", "Firewall"};
    private JComboBox jcComponents;
    private JButton jbAdd;

    public ComponentManagementPanel() {
        setLayout(new GridLayout(0,2));

        //maak de label
        jlCurrentComponents = new JLabel("Huidige componenten:");
        jlCurrentComponents.setFont(new Font("Test", Font.BOLD, 15));
        jlCurrentComponents.setAlignmentX(Component.CENTER_ALIGNMENT);
        jlCurrentComponents.setBorder(new EmptyBorder(10, 20, 20,20));

        //Maak de label
        jlAddComponents = new JLabel("Component toevoegen:");
        jlAddComponents.setFont(new Font("Test", Font.BOLD, 15));
        jlAddComponents.setAlignmentX(Component.CENTER_ALIGNMENT);
        jlAddComponents.setBorder(new EmptyBorder(0,20,20,20));

        //Maak het panel waar alle content in staat
        jpCurrentComponentsContent = new JPanel();
        jpCurrentComponentsContent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jpCurrentComponentsContent.setMaximumSize(new Dimension(450, 550));

        //maak het hele linker kant panel aan
        jpCurrentComponents = new JPanel();
        add(jpCurrentComponents);
        jpCurrentComponents.setLayout(new BoxLayout(jpCurrentComponents, BoxLayout.PAGE_AXIS));
        jpCurrentComponents.add(jlCurrentComponents);
        jpCurrentComponents.add(jpCurrentComponentsContent);
        jpCurrentComponentsContent.setLayout(new BorderLayout());

        //init Content linker panel
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("Type");
        tableModel.addColumn("Naam");
        tableModel.addColumn("Beschikbaarheid");
        tableModel.addColumn("Prijs");

        jTable = new JTable(tableModel);
        tableScrollPane = new JScrollPane(jTable);
        tableScrollPane.setSize(new Dimension(250, 450));

        //Voeg content toe aan linker panel
        jpCurrentComponentsContent.add(tableScrollPane);

        //Maak het panel waar alle content in staat
        jpAddComponentsContent = new JPanel();
        jpAddComponentsLayout = new JPanel();
        jpAddComponentsContent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jpAddComponentsContent.setPreferredSize(new Dimension(450,550));
        jpAddComponentsContent.setLayout(new GridLayout(3,0));
        jpAddComponentsLayout.setLayout(new GridBagLayout());

        //Maak het hele rechter kant panel aan
        jpAddComponents = new JPanel();
        add(jpAddComponents);
        jpAddComponents.add(jlAddComponents);
        jpAddComponents.add(jpAddComponentsContent);
        jpAddComponentsContent.add(jpAddComponentsLayout);

        //init Content rechter panel
        jlName = new JLabel("Naam: ");
        jlPrice = new JLabel("Prijs: ");
        jlAvailability = new JLabel("Beschikbaarheid:");
        jtName = new JTextField(15);
        jtPrice = new JTextField(15);
        jtAvailability = new JTextField(10);
        jcComponents = new JComboBox(components);
        jbAdd = new JButton("Voeg Toe");

        //Voeg alle content toe
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(10,10,0,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        jpAddComponentsLayout.add(jlName, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        jpAddComponentsLayout.add(jtName, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        jpAddComponentsLayout.add(jlPrice, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        jpAddComponentsLayout.add(jtPrice, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        jpAddComponentsLayout.add(jlAvailability, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 2;
        jpAddComponentsLayout.add(jtAvailability, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        jpAddComponentsLayout.add(jcComponents, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 3;
        jpAddComponentsLayout.add(jbAdd, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
    }

    public JTextField getJtName(){
        return jtName;
    }

    public JTextField getJtPrice() {
        return jtPrice;
    }

    public JTextField getJtAvailability() {
        return jtAvailability;
    }

    public JComboBox getJcComponents() {
        return jcComponents;
    }

    public JButton getJbAdd() {
        return jbAdd;
    }

    public JTable getJTable() {
        return jTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }


}
