package site.nerdygadgets.views;

import javax.swing.*;

public class HeaderPanel extends JPanel {
    private JLabel headerText;

    public HeaderPanel() {
        headerText = new JLabel("NerdyGadgets");
        add(headerText);
    }

    public JLabel getHeaderText() {
        return headerText;
    }
}
