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
        jpHuidigeComponenten.add(jlHuidigeComponenten, BorderLayout.CENTER);

        jpComponentToevegoen = new JPanel();
        add(jpComponentToevegoen);
//        jpComponentToevegoen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jpComponentToevegoen.setLayout(new BoxLayout(jpComponentToevegoen, BoxLayout.PAGE_AXIS));
        jpComponentToevegoen.add(jlComponentToevoegen);
        jpComponentToevegoen.add(jpComponentAanmaken);

    }
}
