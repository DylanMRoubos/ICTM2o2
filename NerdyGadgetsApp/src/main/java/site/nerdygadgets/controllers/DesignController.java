package site.nerdygadgets.controllers;

import site.nerdygadgets.functions.*;
import site.nerdygadgets.models.ComponentModel;
import site.nerdygadgets.models.DesignModel;
import site.nerdygadgets.models.InfrastructuurComponentModel;
import site.nerdygadgets.views.DesignPanel;
import site.nerdygadgets.views.MainFrameView;
import site.nerdygadgets.views.Views;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Component;

public class DesignController implements ActionListener {
    private DesignPanel panel;
    private DesignModel model;
    private ArrayList<ComponentModel> lijst;

    public DesignController(DesignPanel panel, DesignModel model, MainFrameView mfv){
        this.panel = panel;
        this.model = model;
        lijst = new ArrayList<ComponentModel>();
        initController();

        panel.getjTable().getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        panel.getjTable().getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        panel.getjTable().getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());

        //SET CUSTOM EDITOR TO TEAMS COLUMN
        panel.getjTable().getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JTextField(), panel, this));
        panel.getjTable().getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JTextField(), panel, this));
        panel.getjTable().getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JTextField(), panel, this));

        panel.getJcDatabase().addActionListener(this);
        panel.getJcWeb().addActionListener(this);
        panel.getJcPfsense().addActionListener(this);

        panel.getOpslaanButton().addActionListener(this);

        mfv.getHomePanel().getJpOpen().addMouseListener(new MouseAdapter() {
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
                int returnVal = fc.showOpenDialog(mfv.getParent());

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //This is where a real application would open the file.
                    openFile(file);
                    System.out.println("Opening: " + file.getName());

                } else {
                    System.out.println("Open command cancelled by user.");
                }
            }
        });
    }

    public void openFile(File f) {
        try {
            this.panel.getTableModel().setRowCount(0);

            ArrayList<InfrastructuurComponentModel> l = Serialization.deserializeInfrastructuur(f.getAbsolutePath());
            for (InfrastructuurComponentModel m : l)
                this.addModelToTable(m);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    }

    public void update() {
        updatePrijs();
        updateBeschikbaarheid();
    }
    public void updatePrijs() {
        ArrayList<InfrastructuurComponentModel> l = getCurrentModels();
        double price = CalculateComponent.calculatePrice(l);
        panel.getJlPrijs().setText("€" + String.valueOf(price));
    }
    public void updateBeschikbaarheid() {
        ArrayList<InfrastructuurComponentModel> l = getCurrentModels();
        double beschikbaarheid = CalculateComponent.calculateAvailability(l);
        panel.getJlBeschikbaarheid().setText(String.valueOf(beschikbaarheid) + "%");
    }

    private void addModelToTable(InfrastructuurComponentModel model) {
        panel.getTableModel().addRow(new Object[]{model.getType().name(), model.getName(), String.valueOf(model.getAvailability()), String.valueOf(model.getPrice()), String.valueOf(model.getAantal()), " + ", " - ", "Delete"});
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


            ArrayList<InfrastructuurComponentModel> l = getCurrentModels();
            try {
                Serialization.serializeInfrastructuur(l, filePath);
                System.out.println(l);
                System.out.println("Gelukt jawelll");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public ArrayList<InfrastructuurComponentModel> getCurrentModels() {
        ArrayList<InfrastructuurComponentModel> l = new ArrayList<InfrastructuurComponentModel>();
        int count = panel.getTableModel().getRowCount();
        for (int i = 0; i < count; i++) {
            ComponentType type = null;

            switch (panel.getTableModel().getValueAt(i, 0).toString().toLowerCase()) {
                case "database":
                    type = ComponentType.Database;
                    break;
                case "firewall":
                    type = ComponentType.Firewall;
                    break;
                case "webserver":
                    type = ComponentType.Webserver;
                    break;
            }

            l.add(new InfrastructuurComponentModel(panel.getTableModel().getValueAt(i, 1).toString(),
                    Double.parseDouble(panel.getTableModel().getValueAt(i, 2).toString()),
                    Double.parseDouble(panel.getTableModel().getValueAt(i, 3).toString()),
                    type,
                    Integer.parseInt(panel.getTableModel().getValueAt(i, 4).toString())));
        }
        return l;
    }
}

