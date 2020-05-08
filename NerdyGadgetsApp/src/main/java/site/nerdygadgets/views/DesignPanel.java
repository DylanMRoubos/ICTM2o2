package site.nerdygadgets.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DesignPanel extends JPanel {

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
    private JComboBox jcComponenten;
    private JButton jbVoegToe;

    public DesignPanel() {

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
        jpHuidigeComponenten.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //jpHuidigeComponenten.setLayout(new BorderLayout());
        jpHuidigeComponenten.setLayout(new BoxLayout(jpHuidigeComponenten, BoxLayout.PAGE_AXIS));
        //jpHuidigeComponenten.add(jlHuidigeComponenten);
        jpHuidigeComponenten.add(jpHuidigeComponentenContent);
        //jpHuidigeComponentenContent.setLayout(new BorderLayout());

        //init Content linker panel
        JComboBox combo1 = new JComboBox(new String[] {"Databaseservers"});
        JComboBox combo2 = new JComboBox(new String[] {"Webservers"});
        JComboBox combo3 = new JComboBox(new String[] {"Pfsense"});

        JList list = new JList();
        list.setPreferredSize(new Dimension(200,200));

        JButton opslaanButton = new JButton("Opslaan Als");

        //tableModel.addRow(new Object[]{"mep", "mep", "mep", "mep"});

        //Voeg content toe aan linker panel

        jpHuidigeComponentenContent.add(combo1);
        jpHuidigeComponentenContent.add(combo2);
        jpHuidigeComponentenContent.add(combo3);
        jpHuidigeComponentenContent.add(list);
        jpHuidigeComponentenContent.add(opslaanButton);

        //Maak het panel waar alle content in staat
        jpComponentToevoegenContent = new JPanel();
        //jpComponentToevoegenContent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jpComponentToevoegenContent.setMaximumSize(new Dimension(250,450));

        //Maak het hele rechter kant panel aan
        jpComponentToevoegen = new JPanel();
        add(jpComponentToevoegen);
//        jpComponentToevegoen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jpComponentToevoegen.setLayout(new BoxLayout(jpComponentToevoegen, BoxLayout.PAGE_AXIS));
        jpComponentToevoegen.add(jlComponentToevoegen);
        jpComponentToevoegen.add(jpComponentToevoegenContent);

        //init Content rechter panel
        JButton jbOpt = new JButton("Optimaliseer");

        //Voeg alle content toe
        jpComponentToevoegenContent.setLayout(new FlowLayout());
        jpComponentToevoegenContent.add(jbOpt);
    }
}
