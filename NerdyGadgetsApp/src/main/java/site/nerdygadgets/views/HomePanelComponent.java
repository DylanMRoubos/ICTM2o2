package site.nerdygadgets.views;

import org.pushingpixels.substance.api.ComponentState;
import org.pushingpixels.substance.api.SubstanceCortex;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * HomePanelComponent class
 * Displays the image for the Jpanels used in HomePanel
 *
 * @author Mike Thomas & Dylan Roubos
 * @version 1.0
 * @since 10-05-2020
 */

public class HomePanelComponent extends JPanel {
    private JLabel text;

    public HomePanelComponent(String name, String filename) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
        setBackground(SubstanceCortex.GlobalScope.getCurrentSkin().getColorScheme(this, ComponentState.DEFAULT).getLightColor());

        java.net.URL imageURL = this.getClass().getClassLoader().getResource(String.format("home/%s", filename));
        BufferedImage img = null;
        try {
            img = ImageIO.read(imageURL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg = img.getScaledInstance(150, -1, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);

        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(imageLabel);

        text = new JLabel(name);
        text.setFont(new Font("Segoe UI", Font.BOLD, 14));
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(text);
    }
}
