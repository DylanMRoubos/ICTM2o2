package site.nerdygadgets.controllers;

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
    private DesignController dc;
    private ComponentenController cc;

    public RoutePanelController(MainFrameView mainFrameView, DesignController dc, ComponentenController cc) {
        this.mainFrameView = mainFrameView;
        this.dc = dc;
        this.cc = cc;
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
                // TODO: implement dialog
                JFileChooser fc = new JFileChooser();
                fc.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        if (f.getAbsolutePath().endsWith(".json") || f.isDirectory())
                            return true;
                        return false;
                    }

                    @Override
                    public String getDescription() {
                        return "(*.json) JSON Format";
                    }
                });

                System.out.println("open a dialog or something");
                int returnVal = fc.showOpenDialog(mainFrameView.getParent());

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //This is where a real application would open the file.
                    dc.openFile(file);
                    switchPanel(Views.DESIGN);
                    System.out.println("Opening: " + file.getName());

                } else {
                    System.out.println("Open command cancelled by user.");
                }

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
        //if (views.name() == "DESIGN")
        //    mainFrameView.getDesignPanel();
    }
}
