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
    private JList jList;
    private JComboBox jcDatabase;
    private JComboBox jcWeb;
    private JComboBox jcPfsense;
    private JButton jbOpt;
    private JButton opslaanButton;

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
        jcDatabase = new JComboBox(new String[] {"Databaseservers"});
        jcWeb = new JComboBox(new String[] {"Webservers"});
        jcPfsense = new JComboBox(new String[] {"Pfsense"});

         jList = new JList();
        JScrollPane sp = new JScrollPane(jList);
        sp.setPreferredSize(new Dimension(350,500));
        sp.setBorder(BorderFactory.createLineBorder(Color.black));
        opslaanButton = new JButton("Opslaan Als");

        //Voeg content toe aan linker panel

        jpOntwerpMaken.add(jcDatabase);
        jpOntwerpMaken.add(jcWeb);
        jpOntwerpMaken.add(jcPfsense);
        jpOntwerpMaken.add(sp);
        jpOntwerpMaken.add(opslaanButton);

        //Maak het panel waar alle content in staat
        jpWeergavePanel = new JPanel();
        //jpComponentToevoegenContent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
}
