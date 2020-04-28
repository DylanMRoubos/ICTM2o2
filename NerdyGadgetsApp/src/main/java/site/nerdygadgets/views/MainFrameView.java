package site.nerdygadgets.views;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;

public class MainFrameView extends JFrame {
    private final HeaderPanel headerPanel;
    private final HomePanel homePanel;
    private final CurrentInfrastructurePanel currentInfrastructurePanel;
    private final DesignPanel designPanel;
    private final ComponentManagementPanel componentManagementPanel;
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

        // design panel
        designPanel = new DesignPanel();
        content.add(designPanel, Views.DESIGN.name());

        // component management panel
        componentManagementPanel = new ComponentManagementPanel();
        content.add(componentManagementPanel, Views.COMPONENTS.name());

        CardLayout cl = (CardLayout) content.getLayout();
        cl.show(content, Views.HOME.name());

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

    public DesignPanel getDesignPanel() {
        return designPanel;
    }

    public ComponentManagementPanel getComponentManagementPanel() {
        return componentManagementPanel;
    }

    public JPanel getContent() {
        return content;
    }
}