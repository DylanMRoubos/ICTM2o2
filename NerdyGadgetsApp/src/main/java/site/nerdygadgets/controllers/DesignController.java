package site.nerdygadgets.controllers;

import site.nerdygadgets.functions.ComponentType;
import site.nerdygadgets.functions.Serialization;
import site.nerdygadgets.models.ComponentModel;
import site.nerdygadgets.models.DesignModel;
import site.nerdygadgets.models.InfrastructuurComponentModel;
import site.nerdygadgets.views.DesignPanel;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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

        panel.getOpslaanButton().addActionListener(this);
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
            cb.setSelectedIndex(-1);
            ComponentModel model;

            if (e.getSource() == panel.getJcDatabase()) {
                model = ComponentModel.getModel(item, ComponentType.Database);
                if(model == null)
                {
                    System.out.println("Unable to convert component");
                    return;
                }
                //tableModel.addRow(new Object[]{"firewall", "vuurmuur", "99%", "1000", "5", " + ", " - ", "Delete"});
                panel.getTableModel().addRow(new Object[]{model.getType().name(), model.getName(), String.valueOf(model.getAvailability()), String.valueOf(model.getPrice()), "1", " + ", " - ", "Delete"});
            }

            if (e.getSource() == panel.getJcWeb()) {
                model = ComponentModel.getModel(item, ComponentType.Webserver);
                if(model == null)
                {
                    System.out.println("Unable to convert component");
                    return;
                }
                panel.getTableModel().addRow(new Object[]{model.getType().name(), model.getName(), String.valueOf(model.getAvailability()), String.valueOf(model.getPrice()), "1", " + ", " - ", "Delete"});
                //panel.getTableModel().addRow(new Object[]{model.getType().name(), model.getName(), String.valueOf(model.getAvailability()), String.valueOf(model.getPrice())});
            }

            if (e.getSource() == panel.getJcPfsense()) {
                model = ComponentModel.getModel(item, ComponentType.Firewall);
                System.out.println(model);
                if(model == null)
                {
                    System.out.println("Unable to convert component");
                    return;
                }
                panel.getTableModel().addRow(new Object[]{model.getType().name(), model.getName(), String.valueOf(model.getAvailability()), String.valueOf(model.getPrice()), "1", " + ", " - ", "Delete"});
                //panel.getTableModel().addRow(new Object[]{model.getType().name(), model.getName(), String.valueOf(model.getAvailability()), String.valueOf(model.getPrice())});
            }

            //Update prijs & beschikbaarheid
            updatePrijs();
            updateBeschikbaarheid();
        }

        if (e.getSource() == panel.getOpslaanButton()) {
            String filePath;

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Opslaan Als");
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.isDirectory())
                        return true;
                    return (f.getName().toLowerCase().endsWith(".json"));
                }

                @Override
                public String getDescription() {
                    return "(*.json) JSON Format";
                }
            });

            int userSelection = fileChooser.showSaveDialog(panel);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                filePath = fileToSave.getAbsolutePath();
                if (!filePath.endsWith(".json"))
                    filePath = filePath + ".json";
                System.out.println("Save as file: " + filePath);
            } else {
                return;
            }


            ArrayList<InfrastructuurComponentModel> l = new ArrayList<InfrastructuurComponentModel>();
            int count = panel.getTableModel().getRowCount();
            for (int i = 0; i < count; i++) {
                ComponentType type = null;
                switch (panel.getTableModel().getValueAt(i, 0).toString().toLowerCase()) {
                    case "database":
                        type = ComponentType.Database;
                    case "firewall":
                        type = ComponentType.Firewall;
                    case "webserver":
                        type = ComponentType.Webserver;
                }

                l.add(new InfrastructuurComponentModel(panel.getTableModel().getValueAt(i, 1).toString(),
                        Double.parseDouble(panel.getTableModel().getValueAt(i, 2).toString()),
                        Double.parseDouble(panel.getTableModel().getValueAt(i, 3).toString()),
                        type,
                        Integer.parseInt(panel.getTableModel().getValueAt(i, 4).toString())));
            }

            try {
                Serialization.serializeInfrastructuur(l, filePath);
                System.out.println(l);
                System.out.println("Gelukt jawelll");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

