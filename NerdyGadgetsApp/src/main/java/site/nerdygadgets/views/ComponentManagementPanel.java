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
    private JLabel jlHuidigeComponenten;
    private JPanel jpHuidigeComponenten;
    private JPanel jpHuidigeComponentenContent;

    private JLabel jlComponentToevoegen;
    private JPanel jpComponentToevoegen;
    private JPanel jpComponentToevoegenContent;

    //HuidigeComponenten content
    private JTable jTable;
    private DefaultTableModel tableModel;
    private JScrollPane tableScrollPane;

//    private JList jListHuidigeComponenten;
//    private DefaultListModel listModel;

    //ComponentToevoegen content
    private JLabel jlNaam;
    private JTextField jtNaam;
    private JLabel jlPrijs;
    private JTextField jtPrijs;
    private JLabel jlBeschikbaarheid;
    private JTextField jtBeschikbaarheid;
    String[] componenten = {"Database", "Webserver", "PFSense"};
    private JComboBox jcComponenten;
    private JButton jbVoegToe;

//    private ArrayList<ComponentModel> currentComponents;

    public ComponentManagementPanel() {
//        add(new JLabel("Dit moet het componenten paneel voorstellen."))
        setLayout(new GridLayout(0,2));

//        currentComponents = new ArrayList<ComponentModel>();

        //maak de label
        jlHuidigeComponenten = new JLabel("Huidige componenten:");
        jlHuidigeComponenten.setFont(new Font("Test", Font.BOLD, 15));
        jlHuidigeComponenten.setAlignmentX(Component.CENTER_ALIGNMENT);
        jlHuidigeComponenten.setBorder(new EmptyBorder(5, 20, 20,20));

        //Maak de label
        jlComponentToevoegen = new JLabel("Component toevoegen:");
        jlComponentToevoegen.setFont(new Font("Test", Font.BOLD, 15));
        jlComponentToevoegen.setAlignmentX(Component.CENTER_ALIGNMENT);
        jlComponentToevoegen.setBorder(new EmptyBorder(5,20,20,20));

        //Maak het panel waar alle content in staat
        jpHuidigeComponentenContent = new JPanel();
        jpHuidigeComponentenContent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jpHuidigeComponentenContent.setMaximumSize(new Dimension(250, 450));

        //maak het hele linker kant panel aan
        jpHuidigeComponenten = new JPanel();
        add(jpHuidigeComponenten);
//        jpHuidigeComponenten.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //jpHuidigeComponenten.setLayout(new BorderLayout());
        jpHuidigeComponenten.setLayout(new BoxLayout(jpHuidigeComponenten, BoxLayout.PAGE_AXIS));
        jpHuidigeComponenten.add(jlHuidigeComponenten);
        jpHuidigeComponenten.add(jpHuidigeComponentenContent);
        jpHuidigeComponentenContent.setLayout(new BorderLayout());
        //init Content linker panel
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("Type");
        tableModel.addColumn("Name");
        tableModel.addColumn("Availability");
        tableModel.addColumn("Price");

        //tableModel.addRow(new Object[]{"mep", "mep", "mep", "mep"});

        jTable = new JTable(tableModel);
        tableScrollPane = new JScrollPane(jTable);
        tableScrollPane.setSize(new Dimension(250, 450));

        //Voeg content toe aan linker panel

        jpHuidigeComponentenContent.add(tableScrollPane);

        //Maak het panel waar alle content in staat
        jpComponentToevoegenContent = new JPanel();
        jpComponentToevoegenContent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jpComponentToevoegenContent.setMaximumSize(new Dimension(250,450));

        //Maak het hele rechter kant panel aan
        jpComponentToevoegen = new JPanel();
        add(jpComponentToevoegen);
//        jpComponentToevegoen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jpComponentToevoegen.setLayout(new BoxLayout(jpComponentToevoegen, BoxLayout.PAGE_AXIS));
        jpComponentToevoegen.add(jlComponentToevoegen);
        jpComponentToevoegen.add(jpComponentToevoegenContent);

        //init Content rechter panel
        jlNaam = new JLabel("Naam: ");
        jlPrijs = new JLabel("Prijs: ");
        jlBeschikbaarheid = new JLabel("Beschikbaarheid:");
        jtNaam = new JTextField(15);
        jtPrijs = new JTextField(15);
        jtBeschikbaarheid = new JTextField(10);
        jcComponenten = new JComboBox(componenten);
        jbVoegToe = new JButton("Voeg Toe");

        //Voeg alle content toe
        jpComponentToevoegenContent.setLayout(new FlowLayout());
        jpComponentToevoegenContent.add(jlNaam);
        jpComponentToevoegenContent.add(jtNaam);
        jpComponentToevoegenContent.add(jlPrijs);
        jpComponentToevoegenContent.add(jtPrijs);
        jpComponentToevoegenContent.add(jlBeschikbaarheid);
        jpComponentToevoegenContent.add(jtBeschikbaarheid);
        jpComponentToevoegenContent.add(jcComponenten);
        jpComponentToevoegenContent.add(jbVoegToe);

    }

    public JTextField getJtNaam(){
        return jtNaam;
    }

    public JTextField getJtPrijs() {
        return jtPrijs;
    }

    public JTextField getJtBeschikbaarheid() {
        return jtBeschikbaarheid;
    }

    public JComboBox getJcComponenten() {
        return jcComponenten;
    }

    public JButton getJbVoegToe() {
        return jbVoegToe;
    }

    public JTable getjTable() {
        return jTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }


}
