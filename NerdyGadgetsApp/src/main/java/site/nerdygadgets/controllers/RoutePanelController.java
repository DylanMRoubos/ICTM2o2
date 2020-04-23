package site.nerdygadgets.controllers;

import site.nerdygadgets.views.MainFrameView;
import site.nerdygadgets.views.Views;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoutePanelController {
    private MainFrameView mainFrameView;

    public RoutePanelController(MainFrameView mainFrameView) {
        this.mainFrameView = mainFrameView;
        initController();
    }

    private void initController() {
        // temporary button
        mainFrameView.getHeaderPanel().getJbHome().addActionListener(e -> switchPanel(Views.HOME));
        mainFrameView.getHeaderPanel().getJbCurrentInfra().addActionListener(e -> switchPanel(Views.CURRENTINFRA));
        mainFrameView.getHeaderPanel().getJbDesign().addActionListener(e -> switchPanel(Views.DESIGN));
        mainFrameView.getHeaderPanel().getJbComponents().addActionListener(e -> switchPanel(Views.COMPONENTS));

        // header panel home button
        mainFrameView.getHeaderPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                switchPanel(Views.HOME);
            }
        });

        // home panel buttons
        // huidige infrastructuur panel
        mainFrameView.getHomePanel().getJpCurrentInfra().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                switchPanel(Views.CURRENTINFRA);
            }
        });
        // open
        mainFrameView.getHomePanel().getJpOpen().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                // TODO: implement dialog
                System.out.println("open a dialog or something");
            }
        });
        // nieuw ontwerp panel (gelijk naar design panel)
        mainFrameView.getHomePanel().getJpCreate().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                switchPanel(Views.DESIGN);
            }
        });
        // componenten beheer panel
        mainFrameView.getHomePanel().getJpComponent().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                switchPanel(Views.COMPONENTS);
            }
        });
    }

    private void switchPanel(Views views) {
        CardLayout cl = (CardLayout) mainFrameView.getContent().getLayout();
        cl.show(mainFrameView.getContent(), views.name());
    }
}
