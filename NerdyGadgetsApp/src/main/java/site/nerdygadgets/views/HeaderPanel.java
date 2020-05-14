package site.nerdygadgets.views;

import org.pushingpixels.substance.api.ComponentState;
import org.pushingpixels.substance.api.SubstanceCortex;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class HeaderPanel extends JPanel {
    private JLabel headerText;

    public HeaderPanel() {
        headerText = new JLabel("NerdyGadgets");
        headerText.setFont(new Font("Segoe UI", Font.BOLD,  24));
        add(headerText);
        setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        setBackground(SubstanceCortex.GlobalScope.getCurrentSkin().getColorScheme(this, ComponentState.DEFAULT).getLightColor());
    }

    public JLabel getHeaderText() {
        return headerText;
    }
}
