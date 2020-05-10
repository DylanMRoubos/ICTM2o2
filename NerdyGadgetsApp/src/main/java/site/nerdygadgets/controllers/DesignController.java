package site.nerdygadgets.controllers;

import site.nerdygadgets.functions.CalculateComponent;
import site.nerdygadgets.functions.ComponentType;
import site.nerdygadgets.functions.Serialization;
import site.nerdygadgets.models.ComponentModel;
import site.nerdygadgets.models.DesignModel;
import site.nerdygadgets.models.InfrastructuurComponentModel;
import site.nerdygadgets.views.DesignPanel;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableCellRenderer;
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

//BUTTON RENDERER CLASS
class ButtonRenderer extends JButton implements TableCellRenderer
{

    //CONSTRUCTOR
    public ButtonRenderer() {
        //SET BUTTON PROPERTIES
        setOpaque(true);
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object obj,
                                                   boolean selected, boolean focused, int row, int col) {

        //SET PASSED OBJECT AS BUTTON TEXT
        setText((obj==null) ? "":obj.toString());

        return this;
    }

}

//BUTTON EDITOR CLASS
class ButtonEditor extends DefaultCellEditor
{
    protected JButton btn;
    private String lbl;
    private Boolean clicked;

    public ButtonEditor(JTextField txt, DesignPanel panel, DesignController controller) {
        super(txt);

        btn=new JButton();
        btn.setOpaque(true);

        //WHEN BUTTON IS CLICKED
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                if (lbl.equals(" + ")) {
                    panel.getTableModel().setValueAt(
                            String.valueOf(Integer.parseInt(panel.getTableModel().getValueAt(panel.getjTable().getSelectedRow(), 4).toString())+1),
                            panel.getjTable().getSelectedRow(), 4);
                }

                if (lbl.equals(" - ")) {
                    if (Integer.parseInt(panel.getTableModel().getValueAt(panel.getjTable().getSelectedRow(), 4).toString()) > 1)
                        panel.getTableModel().setValueAt(String.valueOf(Integer.parseInt(panel.getTableModel().getValueAt(panel.getjTable().getSelectedRow(), 4).toString())-1), panel.getjTable().getSelectedRow(), 4);
                    else {
                        //Misschien verwijderen als hij minder dan 1 word?
                        //tableModel.removeRow(jTable.getSelectedRow());
                    }

                }

                if (lbl.equals("Delete")) {
                    panel.getTableModel().removeRow(panel.getjTable().getSelectedRow());
                }
                //Update prijs & beschikbaarheid
                controller.update();
            }
        });
    }

    //OVERRIDE A COUPLE OF METHODS
    @Override
    public Component getTableCellEditorComponent(JTable table, Object obj,
                                                 boolean selected, int row, int col) {

        //SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
        lbl=(obj==null) ? "":obj.toString();
        btn.setText(lbl);
        clicked=true;
        return btn;
    }

    //IF BUTTON CELL VALUE CHNAGES,IF CLICKED THAT IS
    @Override
    public Object getCellEditorValue() {
        //SET IT TO FALSE NOW THAT ITS CLICKED
        clicked=false;
        return new String(lbl);
    }

    @Override
    public boolean stopCellEditing() {
        //SET CLICKED TO FALSE FIRST
        clicked=false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        // TODO Auto-generated method stub
        super.fireEditingStopped();
    }
}

