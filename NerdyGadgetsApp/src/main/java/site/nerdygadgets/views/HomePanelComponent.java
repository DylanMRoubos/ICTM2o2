package site.nerdygadgets.views;

import javax.swing.*;

public class HomePanelComponent extends JPanel {
    private JLabel text;
    // private "image";


    public HomePanelComponent(String name) {
        text = new JLabel(name);
        add(text);
    }
}
