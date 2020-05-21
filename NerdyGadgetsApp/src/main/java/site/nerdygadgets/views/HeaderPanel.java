package site.nerdygadgets.views;

import org.pushingpixels.substance.api.ComponentState;
import org.pushingpixels.substance.api.SubstanceCortex;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
/**
 * HeaderPanel class
 * Displays the header
 *
 * @author Mike Thomas & Dylan Roubos & Ruben Oosting
 * @version 1.0
 * @since 01-05-2020
 */

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
