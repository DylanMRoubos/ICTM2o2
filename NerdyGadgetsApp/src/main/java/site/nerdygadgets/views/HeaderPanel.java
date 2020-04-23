package site.nerdygadgets.views;

import javax.swing.*;

public class HeaderPanel extends JPanel {
    private JLabel headerText;
    private JButton jbHome;         // temp
    private JButton jbCurrentInfra; // temp
    private JButton jbDesign;       // temp
    private JButton jbComponents;   // temp

    public HeaderPanel() {
        headerText = new JLabel("NerdyGadgets");
        add(headerText);

        // temp
        jbHome = new JButton("home");
        jbCurrentInfra = new JButton("current infra");
        jbDesign = new JButton("design panel");
        jbComponents = new JButton("componenten");
        add(jbHome);
        add(jbCurrentInfra);
        add(jbDesign);
        add(jbComponents);
    }

    public JLabel getHeaderText() {
        return headerText;
    }

    // temp
    public JButton getJbHome() {
        return jbHome;
    }
    public JButton getJbCurrentInfra() {
        return jbCurrentInfra;
    }
    public JButton getJbDesign() {
        return jbDesign;
    }
    public JButton getJbComponents() {
        return jbComponents;
    }
}
