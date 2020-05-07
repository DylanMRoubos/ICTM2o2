package site.nerdygadgets.controllers;

import site.nerdygadgets.models.CurrentInfrastructureComponentModel;
import site.nerdygadgets.views.CurrentInfrastructureComponentPanel;
import site.nerdygadgets.views.CurrentInfrastructurePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * CurrentInfrastructureController class
 * Gets data from model and puts in in the JPanel
 *
 * @author Mike Thomas & Dylan Roubos
 * @version 1.0
 * @since 01-05-2020
 */
public class CurrentInfrastructureController extends TimerTask {
    private CurrentInfrastructureComponentModel model;
    private CurrentInfrastructurePanel panel;
    private CurrentInfrastructureComponentPanel componentPanel;

    public CurrentInfrastructureController(CurrentInfrastructureComponentModel model, CurrentInfrastructurePanel panel) {
        this.model = model;
        this.panel = panel;

        componentPanel = new CurrentInfrastructureComponentPanel(model.getName());

        panel.add(componentPanel);

        componentPanel.getNameValue().setText(model.getName());

        // Image
        java.net.URL imageURL = null;
        if (model.getType().equals("PFSENSE")) {
            imageURL = this.getClass().getClassLoader().getResource("firewall.png");
        } else if (model.getType().equals("WEB")) {
            imageURL = this.getClass().getClassLoader().getResource("generic_server.png");
        } else if (model.getType().equals("DATABASE")) {
            imageURL = this.getClass().getClassLoader().getResource("database_server.png");
        }
        BufferedImage img = null;
        try {
            img = ImageIO.read(imageURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(100, -1, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);

        JLabel imageLabel = new JLabel(imageIcon);
        componentPanel.add(imageLabel);


        // instantiate timer and add self to timer.
        Timer timer = new Timer();
        timer.schedule(this, 0,5000); // run on start and then every 30 seconds.

    }
    //Update the data from the model
    public void update() {
        model.getData();

        componentPanel.getOnlineValue().setText(String.valueOf(model.getOnline()));
        componentPanel.getUptimeValue().setText(model.getUptime());
        componentPanel.getCpuValue().setText(model.getCpu());
        componentPanel.getMemoryValue().setText(model.getMemory());
        componentPanel.getDiskValue().setText(model.getDisk());

    }

    // wrapper for timertask
    public void run() {
        update();
    }
}
