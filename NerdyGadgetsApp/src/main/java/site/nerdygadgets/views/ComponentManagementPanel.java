package site.nerdygadgets.views;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ComponentManagementPanel extends JPanel {
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



    public ComponentManagementPanel() {
//        add(new JLabel("Dit moet het componenten paneel voorstellen."))
        setLayout(new GridLayout(0,2));
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
}
