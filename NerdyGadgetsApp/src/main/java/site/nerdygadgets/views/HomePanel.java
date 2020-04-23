package site.nerdygadgets.views;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    // panels
    private JPanel jpCurrentInfra;
    private JPanel jpOpen;
    private JPanel jpCreate;
    private JPanel jpComponent;

    public HomePanel() {
        setLayout(new GridLayout(2, 2, 10, 10));
        jpCurrentInfra = new HomePanelComponent("Huidige infrastructuur");
        jpOpen = new HomePanelComponent("Open");
        jpCreate = new HomePanelComponent("Nieuw");
        jpComponent = new HomePanelComponent("Componenten");

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
