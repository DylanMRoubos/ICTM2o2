package site.nerdygadgets.views;

import site.nerdygadgets.functions.ComponentType;
import site.nerdygadgets.functions.Serialization;
import site.nerdygadgets.models.ComponentModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

public class ComponentManagementPanel extends JPanel {
    private JLabel jlCurrentComponents;
    private JPanel jpCurrentComponents;
    private JPanel jpCurrentComponentsContent;

    private JLabel jlAddComponents;
    private JPanel jpAddComponents;
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

//    private ArrayList<ComponentModel> currentComponents;

    public ComponentManagementPanel() {
//        add(new JLabel("Dit moet het components paneel voorstellen."))
        setLayout(new GridLayout(0,2));

//        currentComponents = new ArrayList<ComponentModel>();

        //maak de label
        jlCurrentComponents = new JLabel("Huidige componenten:");
        jlCurrentComponents.setFont(new Font("Test", Font.BOLD, 15));
        jlCurrentComponents.setAlignmentX(Component.CENTER_ALIGNMENT);
        jlCurrentComponents.setBorder(new EmptyBorder(5, 20, 20,20));

        //Maak de label
        jlAddComponents = new JLabel("Component toevoegen:");
        jlAddComponents.setFont(new Font("Test", Font.BOLD, 15));
        jlAddComponents.setAlignmentX(Component.CENTER_ALIGNMENT);
        jlAddComponents.setBorder(new EmptyBorder(5,20,20,20));

        //Maak het panel waar alle content in staat
        jpCurrentComponentsContent = new JPanel();
        jpCurrentComponentsContent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jpCurrentComponentsContent.setMaximumSize(new Dimension(450, 550));

        //maak het hele linker kant panel aan
        jpCurrentComponents = new JPanel();
        add(jpCurrentComponents);
//        jpCurrentComponents.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //jpCurrentComponents.setLayout(new BorderLayout());
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

        //tableModel.addRow(new Object[]{"mep", "mep", "mep", "mep"});

        jTable = new JTable(tableModel);
        tableScrollPane = new JScrollPane(jTable);
        tableScrollPane.setSize(new Dimension(250, 450));

        //Voeg content toe aan linker panel

        jpCurrentComponentsContent.add(tableScrollPane);

        //Maak het panel waar alle content in staat
        jpAddComponentsContent = new JPanel();
        jpAddComponentsContent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jpAddComponentsContent.setMaximumSize(new Dimension(450,550));

        //Maak het hele rechter kant panel aan
        jpAddComponents = new JPanel();
        add(jpAddComponents);
//        jpComponentToevegoen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jpAddComponents.setLayout(new BoxLayout(jpAddComponents, BoxLayout.PAGE_AXIS));
        jpAddComponents.add(jlAddComponents);
        jpAddComponents.add(jpAddComponentsContent);

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
        jpAddComponentsContent.setLayout(new FlowLayout());
        jpAddComponentsContent.add(jlName);
        jpAddComponentsContent.add(jtName);
        jpAddComponentsContent.add(jlPrice);
        jpAddComponentsContent.add(jtPrice);
        jpAddComponentsContent.add(jlAvailability);
        jpAddComponentsContent.add(jtAvailability);
        jpAddComponentsContent.add(jcComponents);
        jpAddComponentsContent.add(jbAdd);

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
