package site.nerdygadgets.views;

import javax.swing.*;

public class HeaderPanel extends JPanel {
    private JLabel headerText;
    private JButton jbHome;         // temp
    private JButton jbCurrentInfra; // temp

    public HeaderPanel() {
        headerText = new JLabel("NerdyGadgets");
        add(headerText);

        // temp
        jbHome = new JButton("home");
        jbCurrentInfra = new JButton("current infra");
        add(jbHome);
        add(jbCurrentInfra);
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
}
