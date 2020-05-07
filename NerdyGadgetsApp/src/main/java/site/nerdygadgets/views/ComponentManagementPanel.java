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

public class ComponentManagementPanel extends JPanel implements ActionListener {
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

    private JList jListHuidigeComponenten;
    private DefaultListModel listModel;

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

    private ArrayList<ComponentModel> currentComponents;

    public ComponentManagementPanel() {
//        add(new JLabel("Dit moet het componenten paneel voorstellen."))
        setLayout(new GridLayout(0,2));

        currentComponents = new ArrayList<ComponentModel>();

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
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Type");
        tableModel.addColumn("Name");
        tableModel.addColumn("Availability");
        tableModel.addColumn("Price");

        //tableModel.addRow(new Object[]{"mep", "mep", "mep", "mep"});

        jTable = new JTable(tableModel);
        tableScrollPane = new JScrollPane(jTable);
        tableScrollPane.setSize(new Dimension(250, 450));

        //tableModel.addRow(new Object[]{"mep", "mep", "mep", "mep"});

        //Voeg content toe aan linker panel
        jTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }
            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) {
                int row = jTable.rowAtPoint(e.getPoint());
                if (row >= 0 && row < jTable.getRowCount()) {
                    jTable.setRowSelectionInterval(row, row);
                } else {
                    jTable.clearSelection();
                }

                //Row index is found
                int rowIndex = jTable.getSelectedRow();
                if (rowIndex < 0)
                    return;
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                    JPopupMenu popup = createYourPopUp(rowIndex);
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
        });
        jpHuidigeComponentenContent.add(tableScrollPane);
        loadComponents();

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
        jtNaam = new JTextField(5);
        jtPrijs = new JTextField(5);
        jtBeschikbaarheid = new JTextField(5);
        jcComponenten = new JComboBox(componenten);
        jbVoegToe = new JButton("Voeg Toe");
        jbVoegToe.addActionListener(this);

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

    private JPopupMenu createYourPopUp(int rowindex)
    {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem delete = new JMenuItem("Delete Component");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == delete) {
                    tableModel.removeRow(rowindex);
                }
            }
        });
        popup.add(delete);
        return popup;
    }

    private void loadComponents() {
        try {
            ArrayList<ComponentModel> componenten = Serialization.deserializeComponents();
            if (componenten.size() > 0) {
                for (ComponentModel model : componenten) {
                    tableModel.addRow(new Object[]{model.getType().name(), model.getName(), String.valueOf(model.getAvailability()), String.valueOf(model.getPrice())});
                }
            }
        }
        catch (IOException e) {
            System.out.println("Unable to load components");
        }
    }

    public String getName() {
        return jtNaam.getText();
    }

    public double getPrice() {
        try {
            double price = Double.parseDouble(jtPrijs.getText());
            if (price < 0) {
                return -1; //invalid price
            } else {
                return price;
            }
        }
        catch (NumberFormatException e) { System.out.println("Invalid input"); }
        return -2;
    }

    public double getAvailability() {
        try {
            double availability = Double.parseDouble(jtBeschikbaarheid.getText());
            if (availability < 0) {
                return -1; //invalid availability
            } else {
                return availability;
            }
        }
        catch (NumberFormatException e) { System.out.println("Invalid input"); }
        return -2;
    }

    public ComponentType getType() {
        switch (String.valueOf(jcComponenten.getSelectedItem())) {
            case "Webserver":
                return ComponentType.Webserver;
            case "Database":
                return ComponentType.Database;
            case "PFSense":
                return ComponentType.Firewall;
            default:
                System.out.println("Type Unavailable");
        }
        System.out.println("String.valueOf(jcComponenten.getSelectedItem())");
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbVoegToe) {
            //Voeg toe gedrukt
            double availability = this.getAvailability();
            if (availability == -1) {
                //invalid availability (0>)
            } else if (availability == -2) {
                //invalid input (text etc..)
            }

            double price = this.getPrice();
            if (price == -1) {
                //invalid price (0>)
                //Error afhandelen?
            } else if (price == -2) {
                //invalid input (text etc..)
                //Error afhandelen?
            }

            currentComponents.add(new ComponentModel(this.getName(), this.getAvailability(), this.getPrice(), this.getType()));
            tableModel.addRow(new Object[]{this.getType().name(), this.getName(), String.valueOf(this.getAvailability()), String.valueOf(this.getPrice())});
            System.out.println("Component added! <3");
            for (ComponentModel x : currentComponents)
                System.out.println(x.toString());
        }
    }
}
