package site.nerdygadgets.views;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class HeaderPanel extends JPanel {
    private JLabel headerText;

    public HeaderPanel() {
        headerText = new JLabel("NerdyGadgets");
        headerText.setFont(new Font("Segoe UI", Font.BOLD,  18));
        add(headerText);
        setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
    }

    public JLabel getHeaderText() {
        return headerText;
    }
}
