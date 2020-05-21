package site.nerdygadgets.controllers;

import site.nerdygadgets.functions.ComponentType;
import site.nerdygadgets.models.ComponentModel;
import site.nerdygadgets.models.ComponentsModel;
import site.nerdygadgets.views.ComponentManagementPanel;
import site.nerdygadgets.views.MainFrameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * ComponentsController class
 * Adds functionality to controller panel
 *
 * @author Tristan Scholten & Jordy Wielaard
 * @version 1.0
 * @since 14-05-2020
 */
public class ComponentsController implements ActionListener {
    private ComponentManagementPanel view;
    private ComponentsModel model;
    private MainFrameView mainFrameView;

    public ComponentsController(ComponentManagementPanel view, ComponentsModel model, MainFrameView mainFrameView) {
        this.mainFrameView = mainFrameView;
        this.model = model;
        this.view = view;
        initController();

    }

    // init buttons and listeners
    public void initController() {
        view.getJbAdd().addActionListener(this);
        view.getJTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int row = view.getJTable().rowAtPoint(e.getPoint());
                if (row >= 0 && row < view.getJTable().getRowCount()) {
                    view.getJTable().setRowSelectionInterval(row, row);
                } else {
                    view.getJTable().clearSelection();
                }

                //Row index is found
                int rowIndex = view.getJTable().getSelectedRow();
                if (rowIndex < 0) {
                    return;
                }
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    JPopupMenu popup = createYourPopUp(rowIndex);
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        loadComponents();
    }

    //Creates right click menu to delete components
    private JPopupMenu createYourPopUp(int rowindex) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem delete = new JMenuItem("Delete Component");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == delete) {
                    view.getTableModel().removeRow(rowindex);
                    model.removeAt(rowindex);
                    loadComponents();
                }
            }
        });
        popup.add(delete);
        return popup;
    }

    // Load components into table
    private void loadComponents() {
        model.reloadComponentModel();
        ArrayList<ComponentModel> componenten = model.getComponentModels();
        if (componenten == null)
            componenten = new ArrayList<ComponentModel>();
        this.model.setComponentModels(componenten);
        if (componenten.size() > 0) {
            view.getTableModel().setRowCount(0);
            for (ComponentModel model : componenten) {
                view.getTableModel().addRow(new Object[]{model.getType().name(), model.getName(), String.valueOf(model.getAvailability()), String.valueOf(model.getPrice())});
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getJbAdd()) {
            //Voeg toe pressed
            double availability = this.getAvailability();
            if (availability > 100.0) {
                JOptionPane.showMessageDialog(null, "Beschikbaarheid hoger dan 100%", "ErrorInformation", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (availability == -1) {
                //invalid availability (0>)
                JOptionPane.showMessageDialog(null, "Invalide beschikbaarheid", "ErrorInformation", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (availability == -2) {
                //invalid input (text etc..)
                JOptionPane.showMessageDialog(null, "Invalide beschikbaarheid", "ErrorInformation", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double price = this.getPrice();
            if (price == -1) {
                //invalid price (0>)
                //Error handling
                JOptionPane.showMessageDialog(null, "Invalide prijs", "ErrorInformation", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (price == -2) {
                //invalid input (text etc..)
                //Error handling?
                JOptionPane.showMessageDialog(null, "Invalide prijs", "ErrorInformation", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ComponentModel m = new ComponentModel(view.getJtName().getText(), this.getAvailability(), this.getPrice(), this.getType());
            if (this.model.addComponentModel(m)) {
                view.getTableModel().addRow(new Object[]{getType().name(), view.getJtName().getText(), String.valueOf(this.getAvailability()), String.valueOf(this.getPrice())});
            } else {
                JOptionPane.showMessageDialog(null, "Component bestaat al, zorg dat naam & type uniek zijn.", "ErrorInformation", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public double getPrice() {
        try {
            double price = Double.parseDouble(view.getJtPrice().getText());
            if (price < 0) {
                return -1; //invalid price
            } else {
                return price;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
        }
        return -2;
    }

    public double getAvailability() {
        try {
            double availability = Double.parseDouble(view.getJtAvailability().getText());
            if (availability < 0) {
                return -1; //invalid availability
            } else {
                return availability;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
        }
        return -2;
    }

    public ComponentType getType() {
        switch (String.valueOf(view.getJcComponents().getSelectedItem())) {
            case "Webserver":
                return ComponentType.Webserver;
            case "Database":
                return ComponentType.Database;
            case "Firewall":
                return ComponentType.Firewall;
            default:
                System.out.println("Type Unavailable");
        }
        return null;
    }

}
