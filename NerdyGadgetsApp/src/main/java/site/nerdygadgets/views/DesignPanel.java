package site.nerdygadgets.views;

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

public class DesignPanel extends JPanel {

    private JPanel jpDesign;
    private JPanel jpMakeDesign;

    private JPanel jpDisplay;
    private JPanel jpDisplayPanel;
    private JPanel jpDisplayControls;
    //private JList jList;
    //private DefaultListModel jListModel;
    private JComboBox jcDatabase;
    private JComboBox jcWeb;
    private JComboBox jcFirewall;
    private JButton jbOpt;
    private JButton saveButton;

    private JLabel jlPrice;
    private JLabel jlAvailability;
    private boolean hasToReload;

    private JTable jTable;
    private DefaultTableModel tableModel;

    public DesignPanel() {
        setLayout(new GridLayout(0, 2));
        hasToReload = false;
        //Maak het panel waar alle content in staat
        jpMakeDesign = new JPanel();
        jpMakeDesign.setPreferredSize(new Dimension(600, 650));
        jpMakeDesign.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));

        //maak het hele linker kant panel aan
        jpDesign = new JPanel();
        add(jpDesign);
        jpDesign.setLayout(new FlowLayout());
        jpDesign.add(jpMakeDesign);

        //init Content linker panel
        jcDatabase = new JComboBox(new String[]{""});
        jcWeb = new JComboBox(new String[]{""});
        jcFirewall = new JComboBox(new String[]{""});

        jcDatabase.setRenderer(new MyComboBoxRenderer("Databaseservers"));
        jcDatabase.setSelectedIndex(-1);
        jcWeb.setRenderer(new MyComboBoxRenderer("Webservers"));
        jcWeb.setSelectedIndex(-1);
        jcFirewall.setRenderer(new MyComboBoxRenderer("Firewall"));
        jcFirewall.setSelectedIndex(-1);

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //voor custom amount
                if (column == 4)
                    return true;
                //zodat buttons werken
                if (column == 5 || column == 6 || column == 7)
                    return true;
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4)
                    return Integer.class;
                return super.getColumnClass(columnIndex);
            }

            @Override
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
        tableModel.addColumn("Type");
        tableModel.addColumn("Naam");
        tableModel.addColumn("Beschikbaarheid");
        tableModel.addColumn("Prijs");
        tableModel.addColumn("Aantal");
        tableModel.addColumn("Verhogen");
        tableModel.addColumn("Verlagen");
        tableModel.addColumn("Verwijder");

        jTable = new JTable(tableModel);
//        tableModel.addRow(new Object[]{"firewall", "vuurmuur", "99", "1000", "5", " + ", " - ", "Delete"});
//        tableModel.addRow(new Object[]{"firewall", "vuurmuur", "99", "1000", "5", " + ", " - ", "Delete"});

        JScrollPane sp = new JScrollPane(jTable);

        sp.setPreferredSize(new Dimension(550, 590));
        sp.setBorder(BorderFactory.createLineBorder(Color.black));
        saveButton = new JButton("Opslaan Als");

        //Voeg content toe aan linker panel
        jpMakeDesign.add(jcDatabase);
        jpMakeDesign.add(jcWeb);
        jpMakeDesign.add(jcFirewall);
        jpMakeDesign.add(sp);
        jpMakeDesign.add(saveButton);


        //Content rechter kant
        jlPrice = new JLabel("€0.0");
        jlAvailability = new JLabel("0.0%");

        //Maak het panel waar alle content in staat rechts
        jpDisplayPanel = new JPanel();
        jpDisplayPanel.setPreferredSize(new Dimension(550, 590));

        //Maak het hele rechter kant panel aan
        jpDisplay = new JPanel();
        add(jpDisplay);
        jpDisplayControls = new JPanel();
        jpDisplayControls.setPreferredSize(new Dimension(600, 650));

        jpDisplay.setLayout(new FlowLayout());
        jpDisplayPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        jpDisplayControls.setBorder(BorderFactory.createEmptyBorder(0,50,0,50));

        //init Content rechter panel
        jbOpt = new JButton("Optimaliseer");

        //Voeg alle content toe
//        jpDisplayPanel.setLayout(new FlowLayout());
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


    //zodat de combobox een naam heeft
    class MyComboBoxRenderer extends JLabel implements ListCellRenderer {
        private String _title;

        public MyComboBoxRenderer(String title) {
            _title = title;
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
            if (index == -1 && value == null)
                setText(_title);
            else
                setText(value.toString());
            return this;
        }
    }
}