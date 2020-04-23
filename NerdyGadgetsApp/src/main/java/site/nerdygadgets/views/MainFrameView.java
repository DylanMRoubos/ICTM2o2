package site.nerdygadgets.views;

import javax.swing.*;
import java.awt.*;

public class MainFrameView extends JFrame {
    private final HeaderPanel headerPanel;
    private final HomePanel homePanel;
    private final CurrentInfrastructurePanel currentInfrastructurePanel;
    private JPanel content;

    public MainFrameView() throws HeadlessException {
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        headerPanel = new HeaderPanel();
        add(headerPanel, BorderLayout.PAGE_START);

        content = new JPanel();
        content.setLayout(new CardLayout());
        add(content, BorderLayout.CENTER);

        // home panel
        homePanel = new HomePanel();
        content.add(homePanel, Views.HOME.name());

        // currentInfraPanel
        currentInfrastructurePanel = new CurrentInfrastructurePanel();
        content.add(currentInfrastructurePanel, Views.CURRENTINFRA.name());

        CardLayout cl = (CardLayout) content.getLayout();
        cl.show(content, "HOME");

        setVisible(true);
    }

    public HeaderPanel getHeaderPanel() {
        return headerPanel;
    }

    public HomePanel getHomePanel() {
        return homePanel;
    }

    public CurrentInfrastructurePanel getCurrentInfrastructurePanel() {
        return currentInfrastructurePanel;
    }

    public JPanel getContent() {
        return content;
    }

    public void switchPanel(Views view) {
        switch (view) {
            case HOME:
                // code
            case CURRENTINFRA:
                // code
            case DESIGN:
                // code
            case COMPONENTS:
                // code
        }
    }
}
