package site.nerdygadgets.views;

import site.nerdygadgets.functions.ComboRenderer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.EventObject;
/**
 * DesignPanel class
 * DesignPanel for infrastructures
 *
 * @author Tristan Scholten & Jordy Wielaard
 * @version 1.0
 * @since 14-05-2020
 */
public class DesignPanel extends JPanel {

    private JPanel jpDesign;
    private JPanel jpMakeDesign;

    private JPanel jpDisplay;
    private JPanel jpDisplayPanel;
    private JPanel jpDisplayControls;

    private JComboBox jcDatabase;
    private JComboBox jcWeb;
    private JComboBox jcFirewall;
    private JButton jbOpt;
    private JButton saveButton;

    private JLabel jlPrice;
    private JLabel jlAvailability;

    private JTable jTable;
    private DefaultTableModel tableModel;

    public DesignPanel() {
        setLayout(new GridLayout(0, 2));
        jpMakeDesign = new JPanel();
        jpMakeDesign.setPreferredSize(new Dimension(600, 650));
        jpMakeDesign.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));

        //Make panel left side
        jpDesign = new JPanel();
        add(jpDesign);
        jpDesign.setLayout(new FlowLayout());
        jpDesign.add(jpMakeDesign);

        //init Content left panel
        jcDatabase = new JComboBox(new String[]{""});
        jcWeb = new JComboBox(new String[]{""});
        jcFirewall = new JComboBox(new String[]{""});

        jcDatabase.setRenderer(new ComboRenderer("Databaseservers"));
        jcDatabase.setSelectedIndex(-1);
        jcWeb.setRenderer(new ComboRenderer("Webservers"));
        jcWeb.setSelectedIndex(-1);
        jcFirewall.setRenderer(new ComboRenderer("Firewall"));
        jcFirewall.setSelectedIndex(-1);

        tableModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //Add custom amount
                if (column == 4)
                    return true;
                //make buttons function
                if (column == 5 || column == 6 || column == 7)
                    return true;
                return false;
            }
            //Only able to enter integer in amount column
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4)
                    return Integer.class;
                return super.getColumnClass(columnIndex);
            }

            @Override
            //check if amount is not negative and/or below 1
            public void fireTableCellUpdated(int row, int column) {
                if (column == 4) {
                    if (Integer.parseInt(super.getValueAt(row, column).toString()) < 0)
                        super.setValueAt(Integer.parseInt(super.getValueAt(row, column).toString()) * -1, row, column);
                    else if (Integer.parseInt(super.getValueAt(row, column).toString()) < 1)
                        super.setValueAt(1, row, column);
                }

                super.fireTableCellUpdated(row, column);
            }
        };
        //
        tableModel.addColumn("Type");
        tableModel.addColumn("Naam");
        tableModel.addColumn("Beschikbaarheid");
        tableModel.addColumn("Prijs");
        tableModel.addColumn("Aantal");
        tableModel.addColumn("Verhogen");
        tableModel.addColumn("Verlagen");
        tableModel.addColumn("Verwijder");

        jTable = new JTable(tableModel);
        JScrollPane sp = new JScrollPane(jTable);

        sp.setPreferredSize(new Dimension(550, 590));
        sp.setBorder(BorderFactory.createLineBorder(Color.black));
        saveButton = new JButton("Opslaan Als");

        jpMakeDesign.add(jcDatabase);
        jpMakeDesign.add(jcWeb);
        jpMakeDesign.add(jcFirewall);
        jpMakeDesign.add(sp);
        jpMakeDesign.add(saveButton);

        jlPrice = new JLabel("€0.0");
        jlAvailability = new JLabel("0.0%");

        //Panels right side
        jpDisplayPanel = new JPanel();
        jpDisplayPanel.setPreferredSize(new Dimension(550, 590));

        jpDisplay = new JPanel();
        add(jpDisplay);
        jpDisplayControls = new JPanel();
        jpDisplayControls.setPreferredSize(new Dimension(600, 650));

        jpDisplay.setLayout(new FlowLayout());
        jpDisplayPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        jpDisplayControls.setBorder(BorderFactory.createEmptyBorder(0,50,0,50));

        //init Content right panel
        jbOpt = new JButton("Optimaliseer");

        //Voeg alle content toe
        jpDisplay.add(jpDisplayControls);
        jpDisplayControls.add(jbOpt);
        jpDisplayControls.add(jpDisplayPanel);
        jpDisplayControls.add(new JLabel("Prijs"));
        jpDisplayControls.add(jlPrice);
        jpDisplayControls.add(new JLabel("Beschikbaarheid"));
        jpDisplayControls.add(jlAvailability);
    }

    public JComboBox getJcDatabase() {
        return jcDatabase;
    }

    public JComboBox getJcFirewall() {
        return jcFirewall;
    }

    public JComboBox getJcWeb() {
        return jcWeb;
    }

    public JButton getJbOpt() {
        return jbOpt;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JLabel getJlPrice() {
        return jlPrice;
    }

    public JLabel getJlAvailability() {
        return jlAvailability;
    }

    public JTable getJTable() {
        return jTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

}