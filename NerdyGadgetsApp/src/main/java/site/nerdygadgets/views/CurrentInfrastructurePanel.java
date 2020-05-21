package site.nerdygadgets.views;

import javax.swing.*;
import java.awt.*;

/**
 * CurrentInfrastructurePanel class
 * creates layout for the CurrentInfrastructreComponentPanels
 *
 * @author Mike Thomas & Dylan Roubos
 * @version 1.0
 * @since 01-05-2020
 */

public class CurrentInfrastructurePanel extends JPanel {

    public CurrentInfrastructurePanel() {
        setLayout(new GridLayout(0, 3, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
    }
}
