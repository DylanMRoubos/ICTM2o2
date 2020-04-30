package site.nerdygadgets.views;

import site.nerdygadgets.functions.ComponentType;
import site.nerdygadgets.models.ComponentModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ComponentManagementPanel extends JPanel implements ActionListener {
    private JLabel jlHuidigeComponenten;
    private JPanel jpHuidigeComponenten;
    private JLabel jlComponentToevoegen;
    private JPanel jpComponentToevegoen;
    private JPanel jpComponentAanmaken;

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

        jlHuidigeComponenten = new JLabel("Huidige componenten:");
        jlHuidigeComponenten.setFont(new Font("Test", Font.BOLD, 15));
        jlHuidigeComponenten.setAlignmentX(Component.CENTER_ALIGNMENT);

        jlComponentToevoegen = new JLabel("Component toevoegen:");
        jlComponentToevoegen.setFont(new Font("Test", Font.BOLD, 15));
        jlComponentToevoegen.setAlignmentX(Component.CENTER_ALIGNMENT);
        jlComponentToevoegen.setBorder(new EmptyBorder(5,20,20,20));

        jpComponentAanmaken = new JPanel();
        jpComponentAanmaken.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jpComponentAanmaken.setMaximumSize(new Dimension(250,450));


        jpHuidigeComponenten = new JPanel();
        add(jpHuidigeComponenten);
//        jpHuidigeComponenten.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jpHuidigeComponenten.add(jlHuidigeComponenten);

        jpComponentToevegoen = new JPanel();
        add(jpComponentToevegoen);
//        jpComponentToevegoen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jpComponentToevegoen.setLayout(new BoxLayout(jpComponentToevegoen, BoxLayout.PAGE_AXIS));
        jpComponentToevegoen.add(jlComponentToevoegen);
        jpComponentToevegoen.add(jpComponentAanmaken);


        jlNaam = new JLabel("Naam: ");
        jlPrijs = new JLabel("Prijs: ");
        jlBeschikbaarheid = new JLabel("Beschikbaarheid:");
        jtNaam = new JTextField(5);
        jtPrijs = new JTextField(5);
        jtBeschikbaarheid = new JTextField(5);
        jcComponenten = new JComboBox(componenten);
        jbVoegToe = new JButton("Voeg Toe");
        jbVoegToe.addActionListener(this);

        jpComponentAanmaken.setLayout(new FlowLayout());
        jpComponentAanmaken.add(jlNaam);
        jpComponentAanmaken.add(jtNaam);
        jpComponentAanmaken.add(jlPrijs);
        jpComponentAanmaken.add(jtPrijs);
        jpComponentAanmaken.add(jlBeschikbaarheid);
        jpComponentAanmaken.add(jtBeschikbaarheid);
        jpComponentAanmaken.add(jcComponenten);
        jpComponentAanmaken.add(jbVoegToe);
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
                return availability/100;
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
                return  ComponentType.Firewall;
            default:
                System.out.println("Type Unavailable");
        }
        return null;
    }

    /*
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
    */

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
            System.out.println("Component added! <3");
            for (ComponentModel x : currentComponents)
                System.out.println(x.toString());
        }
    }
}
