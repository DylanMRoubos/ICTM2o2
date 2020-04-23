package site.nerdygadgets.controllers;

import site.nerdygadgets.views.MainFrameView;
import site.nerdygadgets.views.Views;

import java.awt.*;

public class RoutePanelController {
    private MainFrameView mainFrameView;

    public RoutePanelController(MainFrameView mainFrameView) {
        this.mainFrameView = mainFrameView;
        initController();
    }

    private void initController() {
        mainFrameView.getHeaderPanel().getJbHome().addActionListener(e -> switchPanel(Views.HOME));
        mainFrameView.getHeaderPanel().getJbCurrentInfra().addActionListener(e -> switchPanel(Views.CURRENTINFRA));

    }

    private void switchPanel(Views views) {
        CardLayout cl = (CardLayout) mainFrameView.getContent().getLayout();
        cl.show(mainFrameView.getContent(), views.name());
    }
}
