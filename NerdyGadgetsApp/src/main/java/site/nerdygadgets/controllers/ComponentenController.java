package site.nerdygadgets.controllers;

import site.nerdygadgets.functions.ComponentType;
import site.nerdygadgets.functions.Serialization;
import site.nerdygadgets.models.ComponentModel;
import site.nerdygadgets.models.ComponentenModel;
import site.nerdygadgets.views.ComponentManagementPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

public class ComponentenController implements ActionListener{
    private ComponentManagementPanel view;
    private ComponentenModel model;

    public ComponentenController(ComponentManagementPanel view, ComponentenModel model){
        this.model = model;
        this.view = view;
        setData();
        initController();

    }

    public void setData(){
        view.getJtNaam().setText("hallo");
    }

    public void initController(){
        view.getJbVoegToe().addActionListener(this);
        view.getjTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }
            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) {
                int row = view.getjTable().rowAtPoint(e.getPoint());
                if (row >= 0 && row < view.getjTable().getRowCount()) {
                    view.getjTable().setRowSelectionInterval(row, row);
                } else {
                    view.getjTable().clearSelection();
                }

                //Row index is found
                int rowIndex = view.getjTable().getSelectedRow();
                if (rowIndex < 0)
                    return;
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    JPopupMenu popup = createYourPopUp(rowIndex);
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
        });
        loadComponents();
    }

    private JPopupMenu createYourPopUp(int rowindex)
    {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem delete = new JMenuItem("Delete Component");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == delete) {
                    view.getTableModel().removeRow(rowindex);
                }
            }
        });
        popup.add(delete);
        return popup;
    }

    private void loadComponents() {
        try {
            ArrayList<ComponentModel> componenten = Serialization.deserializeComponents();
            this.model.setComponentModels(componenten);
            if (componenten.size() > 0) {
                for (ComponentModel model : componenten) {
                    view.getTableModel().addRow(new Object[]{model.getType().name(), model.getName(), String.valueOf(model.getAvailability()), String.valueOf(model.getPrice())});
                }
            }
        }
        catch (IOException e) {
            System.out.println("Unable to load components");
        }
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getJbVoegToe()) {
            //Voeg toe gedrukt
            double availability = this.getAvailability();
            if (availability == -1) {
                //invalid availability (0>)
            } else if (availability == -2) {
                //invalid input (text etc..)
            }

            double price = this.getPrice();
            if (price == -1) {
                //invalid price (0>)
                //Error afhandelen?
            } else if (price == -2) {
                //invalid input (text etc..)
                //Error afhandelen?
            }

//            currentComponents.add(new ComponentModel(this.getName(), this.getAvailability(), this.getPrice(), this.getType()));
            view.getTableModel().addRow(new Object[]{getType().name(), view.getJtNaam().getText(), String.valueOf(this.getAvailability()), String.valueOf(this.getPrice())});
//            try {
                ComponentModel m = new ComponentModel(view.getJtNaam().getText(), this.getAvailability(), this.getPrice(),this.getType());
                this.model.addComponentModel(m);
                this.model.printComponenten();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
            System.out.println("Component added! <3");
//            for (ComponentModel x : currentComponents)
//                System.out.println(x.toString());
        }
    }


    public double getPrice() {
        try {
            double price = Double.parseDouble(view.getJtPrijs().getText());
            if (price < 0) {
                return -1; //invalid price
            } else {
                return price;
            }
        }
        catch (NumberFormatException e) { System.out.println("Invalid input"); }
        return -2;
    }

    public double getAvailability() {
        try {
            double availability = Double.parseDouble(view.getJtBeschikbaarheid().getText());
            if (availability < 0) {
                return -1; //invalid availability
            } else {
                return availability;
            }
        }
        catch (NumberFormatException e) { System.out.println("Invalid input"); }
        return -2;
    }

    public ComponentType getType() {
        switch (String.valueOf(view.getJcComponenten().getSelectedItem())) {
            case "Webserver":
                return ComponentType.Webserver;
            case "Database":
                return ComponentType.Database;
            case "PFSense":
                return ComponentType.Firewall;
            default:
                System.out.println("Type Unavailable");
        }
        System.out.println("String.valueOf(jcComponenten.getSelectedItem())");
        return null;
    }

}