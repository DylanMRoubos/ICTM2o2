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
import java.util.EventObject;

public class DesignPanel extends JPanel {


    private JPanel jpOntwerp;
    private JPanel jpOntwerpMaken;

    private JPanel jpWeergave;
    private JPanel jpWeergavePanel;
    //private JList jList;
    //private DefaultListModel jListModel;
    private JComboBox jcDatabase;
    private JComboBox jcWeb;
    private JComboBox jcPfsense;
    private JButton jbOpt;
    private JButton opslaanButton;

    private JLabel jlPrijs;
    private JLabel jlBeschikbaarheid;

    private JTable jTable;
    private DefaultTableModel tableModel;

    public DesignPanel() {
        setLayout(new GridLayout(0,2));

        //Maak het panel waar alle content in staat
        jpOntwerpMaken = new JPanel();
        jpOntwerpMaken.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jpOntwerpMaken.setMaximumSize(new Dimension(400, 650));

        //maak het hele linker kant panel aan
        jpOntwerp = new JPanel();
        add(jpOntwerp);
        jpOntwerp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jpOntwerp.setLayout(new BoxLayout(jpOntwerp, BoxLayout.PAGE_AXIS));
        jpOntwerp.add(jpOntwerpMaken);

        //init Content linker panel
        jcDatabase = new JComboBox(new String[]{""});
        jcWeb = new JComboBox(new String[]{""});
        jcPfsense = new JComboBox(new String[]{""});

        jcDatabase.setRenderer(new MyComboBoxRenderer("Databaseservers"));
        jcDatabase.setSelectedIndex(-1);
        jcWeb.setRenderer(new MyComboBoxRenderer("Webservers"));
        jcWeb.setSelectedIndex(-1);
        jcPfsense.setRenderer(new MyComboBoxRenderer("Firewall"));
        jcPfsense.setSelectedIndex(-1);

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
        };
        tableModel.addColumn("Type");
        tableModel.addColumn("Naam");
        tableModel.addColumn("Beschikbaarheid");
        tableModel.addColumn("Prijs");
        tableModel.addColumn("Aantal");
        tableModel.addColumn("Increment");
        tableModel.addColumn("Decrement");
        tableModel.addColumn("TEST");

        jTable = new JTable(tableModel);
        //SET CUSTOM RENDERER TO TEAMS COLUMN
        jTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        jTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        jTable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());

        //SET CUSTOM EDITOR TO TEAMS COLUMN
        jTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JTextField()));
        jTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JTextField()));
        jTable.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JTextField()));

        tableModel.addRow(new Object[]{"firewall", "vuurmuur", "99", "1000", "5", " + ", " - ", "Delete"});
        tableModel.addRow(new Object[]{"firewall", "vuurmuur", "99", "1000", "5", " + ", " - ", "Delete"});

        JScrollPane sp = new JScrollPane(jTable);

        sp.setPreferredSize(new Dimension(350,500));
        sp.setBorder(BorderFactory.createLineBorder(Color.black));
        opslaanButton = new JButton("Opslaan Als");

        //Voeg content toe aan linker panel
        jpOntwerpMaken.add(jcDatabase);
        jpOntwerpMaken.add(jcWeb);
        jpOntwerpMaken.add(jcPfsense);
        jpOntwerpMaken.add(sp);
        jpOntwerpMaken.add(opslaanButton);


        //Content rechter kant
        jlPrijs = new JLabel("$0.0");
        jlBeschikbaarheid = new JLabel("0.0%");

        //Maak het panel waar alle content in staat rechts
        jpWeergavePanel = new JPanel();
        jpWeergavePanel.setMaximumSize(new Dimension(250,450));

        //Maak het hele rechter kant panel aan
        jpWeergave = new JPanel();
        add(jpWeergave);
//        jpComponentToevegoen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jpWeergave.setLayout(new BoxLayout(jpWeergave, BoxLayout.PAGE_AXIS));
        jpWeergave.add( jpWeergavePanel);

        //init Content rechter panel
        jbOpt = new JButton("Optimaliseer");

        //Voeg alle content toe
        jpWeergavePanel.setLayout(new FlowLayout());
        jpWeergavePanel.add(jbOpt);
        jpWeergavePanel.add(new JLabel("Prijs"));
        jpWeergavePanel.add(jlPrijs);
        jpWeergavePanel.add(new JLabel("Beschikbaarheid"));
        jpWeergavePanel.add(jlBeschikbaarheid);
    }

    public JComboBox getJcDatabase() {
        return jcDatabase;
    }

    public JComboBox getJcPfsense() {
        return jcPfsense;
    }

    public JComboBox getJcWeb() {
        return jcWeb;
    }

    public JButton getJbOpt() {
        return jbOpt;
    }

    public JButton getOpslaanButton() {
        return opslaanButton;
    }

    public JLabel getJlPrijs() {
        return jlPrijs;
    }

    public JLabel getJlBeschikbaarheid() {
        return jlBeschikbaarheid;
    }

    public JTable getjTable() {
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

    //BUTTON RENDERER CLASS
    class ButtonRenderer extends JButton implements TableCellRenderer
    {

        //CONSTRUCTOR
        public ButtonRenderer() {
            //SET BUTTON PROPERTIES
            setOpaque(true);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object obj,
                                                       boolean selected, boolean focused, int row, int col) {

            //SET PASSED OBJECT AS BUTTON TEXT
            setText((obj==null) ? "":obj.toString());

            return this;
        }

    }

    //BUTTON EDITOR CLASS
    class ButtonEditor extends DefaultCellEditor
    {
        protected JButton btn;
        private String lbl;
        private Boolean clicked;

        public ButtonEditor(JTextField txt) {
            super(txt);

            btn=new JButton();
            btn.setOpaque(true);

            //WHEN BUTTON IS CLICKED
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    if (lbl.equals(" + ")) {
                        tableModel.setValueAt(String.valueOf(Integer.parseInt(tableModel.getValueAt(jTable.getSelectedRow(), 4).toString())+1), jTable.getSelectedRow(), 4);
                    }

                    if (lbl.equals(" - ")) {
                        if (Integer.parseInt(tableModel.getValueAt(jTable.getSelectedRow(), 4).toString()) > 1)
                            tableModel.setValueAt(String.valueOf(Integer.parseInt(tableModel.getValueAt(jTable.getSelectedRow(), 4).toString())-1), jTable.getSelectedRow(), 4);
                        else {
                            //Misschien verwijderen als hij minder dan 1 word?
                            //tableModel.removeRow(jTable.getSelectedRow());
                        }

                    }

                    if (lbl.equals("Delete")) {
                        tableModel.removeRow(jTable.getSelectedRow());
                    }
                }
            });
        }

        //OVERRIDE A COUPLE OF METHODS
        @Override
        public Component getTableCellEditorComponent(JTable table, Object obj,
                                                     boolean selected, int row, int col) {

            //SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
            lbl=(obj==null) ? "":obj.toString();
            btn.setText(lbl);
            clicked=true;
            return btn;
        }

        //IF BUTTON CELL VALUE CHNAGES,IF CLICKED THAT IS
        @Override
        public Object getCellEditorValue() {
            //SET IT TO FALSE NOW THAT ITS CLICKED
            clicked=false;
            return new String(lbl);
        }

        @Override
        public boolean stopCellEditing() {
            //SET CLICKED TO FALSE FIRST
            clicked=false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            // TODO Auto-generated method stub
            super.fireEditingStopped();
        }
    }

}
