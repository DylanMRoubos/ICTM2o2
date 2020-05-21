package site.nerdygadgets.views;

import javax.swing.*;
import java.awt.*;
/**
 * HomePanel class
 * Displays the homepanel with four JPanels in it
 *
 * @author Mike Thomas & Dylan Roubos
 * @version 1.0
 * @since 01-05-2020
 */

public class HomePanel extends JPanel {
    // panels
    private JPanel jpCurrentInfra;
    private JPanel jpOpen;
    private JPanel jpCreate;
    private JPanel jpComponent;

    public HomePanel() {
        setLayout(new GridLayout(2, 2, 40, 40));
        setBorder(BorderFactory.createEmptyBorder(40,200,40, 200));
        jpCurrentInfra = new HomePanelComponent("Huidige infrastructuur", "servers.png");
        jpOpen = new HomePanelComponent("Open", "open.png");
        jpCreate = new HomePanelComponent("Nieuw", "plus.png");
        jpComponent = new HomePanelComponent("Componenten", "cogs.png");

        add(jpCurrentInfra);
        add(jpOpen);
        add(jpCreate);
        add(jpComponent);

    }

    public JPanel getJpCurrentInfra() {
        return jpCurrentInfra;
    }

    public JPanel getJpOpen() {
        return jpOpen;
    }

    public JPanel getJpCreate() {
        return jpCreate;
    }

    public JPanel getJpComponent() {
        return jpComponent;
    }

}
