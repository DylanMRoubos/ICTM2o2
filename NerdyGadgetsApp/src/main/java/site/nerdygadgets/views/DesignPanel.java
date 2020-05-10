package site.nerdygadgets.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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

        tableModel = new DefaultTableModel();
        jTable = new JTable(tableModel);
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
