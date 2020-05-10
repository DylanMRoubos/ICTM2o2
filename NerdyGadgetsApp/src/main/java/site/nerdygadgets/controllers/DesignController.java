package site.nerdygadgets.controllers;

import site.nerdygadgets.functions.ComponentType;
import site.nerdygadgets.models.ComponentModel;
import site.nerdygadgets.models.DesignModel;
import site.nerdygadgets.views.DesignPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Component;

public class DesignController implements ActionListener {
    private DesignPanel panel;
    private DesignModel model;
    private ArrayList<ComponentModel> lijst;

    public DesignController(DesignPanel panel, DesignModel model){
        this.panel = panel;
        this.model = model;
        lijst = new ArrayList<ComponentModel>();

        initController();

        panel.getJcDatabase().addActionListener(this);
        panel.getJcWeb().addActionListener(this);
        panel.getJcPfsense().addActionListener(this);
    }

    private void fillArraylist() {
        this.lijst.addAll(model.getDatabaseModels());
        this.lijst.addAll(model.getWebModels());
        this.lijst.addAll(model.getPfsenseModels());
    }

    public void initController() {
        model.reloadList();

        ArrayList<ComponentModel> l = model.getDatabaseModels();
        //panel.getJcDatabase().removeAllItems();
        for (ComponentModel m : l)
            panel.getJcDatabase().addItem(m.getName());

        l = model.getWebModels();
        //panel.getJcWeb().removeAllItems();
        for (ComponentModel m : l)
            panel.getJcWeb().addItem(m.getName());

        l = model.getPfsenseModels();
        //panel.getJcPfsense().removeAllItems();
        for (ComponentModel m : l)
            panel.getJcPfsense().addItem(m.getName());

        //Update prijs & beschikbaarheid
        updatePrijs();
        updateBeschikbaarheid();
    }

    public void updatePrijs() {
        for (int i = 0; i < panel.getjTable().getModel().getRowCount(); i++) {

        }
    }

    public void updateBeschikbaarheid() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JComboBox) {
            JComboBox cb = (JComboBox)e.getSource();
            String item = String.valueOf(cb.getSelectedItem());
            ComponentModel model;

            if (e.getSource() == panel.getJcDatabase()) {
                model = ComponentModel.getModel(item, ComponentType.Database);
                if(model == null)
                {
                    System.out.println("Unable to convert component");
                    return;
                }
                panel.getTableModel().addRow(new Object[]{model.getType().name(), model.getName(), String.valueOf(model.getAvailability()), String.valueOf(model.getPrice())});
            }

            if (e.getSource() == panel.getJcWeb()) {
                model = ComponentModel.getModel(item, ComponentType.Database);
                if(model == null)
                {
                    System.out.println("Unable to convert component");
                    return;
                }
                panel.getTableModel().addRow(new Object[]{model.getType().name(), model.getName(), String.valueOf(model.getAvailability()), String.valueOf(model.getPrice())});
            }

            if (e.getSource() == panel.getJcPfsense()) {
                model = ComponentModel.getModel(item, ComponentType.Database);
                if(model == null)
                {
                    System.out.println("Unable to convert component");
                    return;
                }
                panel.getTableModel().addRow(new Object[]{model.getType().name(), model.getName(), String.valueOf(model.getAvailability()), String.valueOf(model.getPrice())});
            }

            //Update prijs & beschikbaarheid
            cb.setSelectedIndex(-1);
            updatePrijs();
            updateBeschikbaarheid();
        }
    }
}
