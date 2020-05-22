package site.nerdygadgets.controllers;

import site.nerdygadgets.views.MainFrameView;
import site.nerdygadgets.views.Views;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * RouteplanelController class
 * Adds functionality to switch between panels in the mainfram
 *
 * @author Mike thomas
 * @version 1.0
 * @since 21-05-2020
 */
public class RoutePanelController {
    private MainFrameView mainFrameView;

    public RoutePanelController(MainFrameView mainFrameView) {
        this.mainFrameView = mainFrameView;
        initController();
    }

    private void initController() {
        // header panel home button
        mainFrameView.getHeaderPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                switchPanel(Views.HOME);
            }
        });

        // home panel buttons
        // current infrastructure panel
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
                switchPanel(Views.DESIGN);
            }
        });
        // new design panel
        mainFrameView.getHomePanel().getJpCreate().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                switchPanel(Views.DESIGN);
            }
        });
        // componenten management panel
        mainFrameView.getHomePanel().getJpComponent().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                switchPanel(Views.COMPONENTS);
            }
        });
    }

    //Switch between the panels
    private void switchPanel(Views views) {
        CardLayout cl = (CardLayout) mainFrameView.getContent().getLayout();
        cl.show(mainFrameView.getContent(), views.name());
    }
}
