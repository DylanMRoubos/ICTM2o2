package site.nerdygadgets.controllers;

import site.nerdygadgets.views.DesignPanel;
import site.nerdygadgets.views.MainFrameView;
import site.nerdygadgets.views.Views;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

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
                switchPanel(Views.DESIGN);
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
