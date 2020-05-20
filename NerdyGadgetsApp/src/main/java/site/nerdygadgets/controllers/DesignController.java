package site.nerdygadgets.controllers;

import site.nerdygadgets.models.Algorithm;
import site.nerdygadgets.functions.*;
import site.nerdygadgets.models.ComponentModel;
import site.nerdygadgets.models.DesignModel;
import site.nerdygadgets.models.InfrastructureComponentModel;
import site.nerdygadgets.views.AvailabiltyDialog;
import site.nerdygadgets.views.DesignPanel;
import site.nerdygadgets.views.MainFrameView;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * DesignController class
 * Adds functionality to Design panel
 *
 * @author Tristan Scholten & Jordy Wielaard
 * @version 1.0
 * @since 14-05-2020
 */
public class DesignController implements ActionListener, TableModelListener {
    private DesignPanel panel;
    private DesignModel model;
    private ArrayList<ComponentModel> list;
    private JPanel graphicsPanel;

    private boolean isUpdatingComboboxes;
    private Algorithm algorithm;

    private MainFrameView mfv;

    public DesignController(DesignPanel panel, DesignModel model, MainFrameView mfv) {
        this.mfv = mfv;
        this.panel = panel;
        this.model = model;
        this.isUpdatingComboboxes = false;

        graphicsPanel = new JPanel(){
          @Override
          public void paintComponent(Graphics g){
              super.paintComponent(g);
              g.setFont(new Font("Segoe UI", Font.PLAIN, 12));
              ArrayList<InfrastructureComponentModel> components = getCurrentModels();
              int xOffset = 20; // start x offset
              int yOffset = 20; // start y offset
              int rectX = 80; // rectangle size
              int rectY = 30;
              int counter = 0; // couter for vertical line

              // draw firewalls
              for (InfrastructureComponentModel component : components) {
                  if (component.getType() == ComponentType.Firewall) {
                      for (int i = 0; i < component.getAmount(); i++) {
                          g.setColor(Color.black);
                          // draw vertical line if counter above 0
                          if (counter > 0) {
                              int x = xOffset+rectX/2;
                              g.drawLine(x, yOffset, x, yOffset-20);
                          }
                          g.drawRect(xOffset, yOffset, rectX, rectY);
                          // formatting of component name
                          String name = component.getName();
                          if (name.length() > 11) {
                              name = name.substring(0, 11) + "..";
                          }
                          g.drawString(name, xOffset + 8, yOffset + 20); // set name of server in rectangle
                          yOffset += rectY+20; // up the y offset for the next component
                          counter++; // up the counter for vertical line
                      }
                  }
              }

              // set offsets for next column of servers
              xOffset += 180;
              yOffset = 20;
              counter = 0;

              // draw webservers
              for (InfrastructureComponentModel component : components) {
                  if (component.getType() == ComponentType.Webserver) {
                      for (int i = 0; i < component.getAmount(); i++) {
                          g.setColor(Color.black);
                          // draw vertical line if counter above 0
                          if (counter > 0) {
                              int x = xOffset+rectX/2;
                              g.drawLine(x, yOffset, x, yOffset-20);
                          }
                          g.drawRect(xOffset, yOffset, rectX, rectY);
                          // formatting of component name
                          String name = component.getName();
                          if (name.length() > 11) {
                              name = name.substring(0, 11) + "..";
                          }
                          g.drawString(name, xOffset + 8, yOffset + 20); // set name of server in rectangle
                          yOffset += rectY+20; // up the y offset for the next component
                          counter++; // up the counter for vertical line
                      }
                  }
              }

              // set offsets for next column of servers
              xOffset += 180;
              yOffset = 20;
              counter = 0;

              // draw databaseservers
              for (InfrastructureComponentModel component : components) {
                  if (component.getType() == ComponentType.Database) {
                      for (int i = 0; i < component.getAmount(); i++) {
                          g.setColor(Color.black);
                          // draw vertical line if counter above 0
                          if (counter > 0) {
                              int x = xOffset+rectX/2;
                              g.drawLine(x, yOffset, x, yOffset-20);
                          }
                          g.drawRect(xOffset, yOffset, rectX, rectY);
                          // formatting of component name
                          String name = component.getName();
                          if (name.length() > 11) {
                              name = name.substring(0, 11) + "..";
                          }
                          g.drawString(name, xOffset + 8, yOffset + 20); // set name of server in rectangle
                          yOffset += rectY+20; // up the y offset for the next component
                          counter++; // up the counter for vertical line
                      }
                  }
              }

              // reset offsets for line drawing
              xOffset = 20;
              yOffset = 20;
              int startX = xOffset+rectX;
              int startY = yOffset+rectY/2;

              // draw lines from firewalls to webservers
              for (InfrastructureComponentModel component : components) {
                  if (component.getType() == ComponentType.Firewall) {
                      for (int i = 0; i < component.getAmount(); i++) {
                          int endY = yOffset;
                          for (InfrastructureComponentModel componentInner : components) {
                              if (componentInner.getType() == ComponentType.Webserver) {
                                  for (int j = 0; j < componentInner.getAmount(); j++) {
                                      int endX = xOffset+180;
                                      g.drawLine(startX, startY, endX, endY+rectY/2);
                                      endY += rectY+20;
                                  }
                              }
                          }
                          startY += rectY+20;
                      }
                  }
              }

              // set offsets for next set of lines
              xOffset += 180;
              startX = xOffset+rectX;
              startY = yOffset+rectY/2;

              // draw lines from webservers to database servers
              for (InfrastructureComponentModel component : components) {
                  if (component.getType() == ComponentType.Webserver) {
                      for (int i = 0; i < component.getAmount(); i++) {
                          int endY = yOffset;
                          for (InfrastructureComponentModel componentInner : components) {
                              if (componentInner.getType() == ComponentType.Database) {
                                  for (int j = 0; j < componentInner.getAmount(); j++) {
                                      int endX = xOffset+180;
                                      g.drawLine(startX, startY, endX, endY+rectY/2);
                                      endY += rectY+20;
                                  }
                              }
                          }
                          startY += rectY+20;
                      }
                  }
              }
          }
        };
        graphicsPanel.setPreferredSize(new Dimension(540,580));
        panel.getJpDisplayPanel().add(graphicsPanel);


        list = new ArrayList<ComponentModel>();

        //Adds buttons
        panel.getJTable().getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        panel.getJTable().getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        panel.getJTable().getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());

        //SET CUSTOM EDITOR
        panel.getJTable().getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JTextField(), panel, this));
        panel.getJTable().getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JTextField(), panel, this));
        panel.getJTable().getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JTextField(), panel, this));

        panel.getJcDatabase().addActionListener(this);
        panel.getJcWeb().addActionListener(this);
        panel.getJcFirewall().addActionListener(this);
        panel.getSaveButton().addActionListener(this);
        panel.getTableModel().addTableModelListener(this);

        panel.getJbOpt().addActionListener(this);

        //Update combobox on click get new compents
        mfv.getHomePanel().getJpCreate().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                updateComboboxes();
            }
        });

        // Open saved file
        mfv.getHomePanel().getJpOpen().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
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

                // Open file dialog
                int returnVal = fc.showOpenDialog(mfv.getParent());

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //This is where a real application would open the file.
                    openFile(file);
//                    System.out.println("Opening: " + file.getName());

                } else {
                    System.out.println("Open command cancelled by user.");
                }
            }
        });
    }

    //Reade file
    public void openFile(File f) {
        try {
            this.panel.getTableModel().setRowCount(0);

            ArrayList<InfrastructureComponentModel> l = Serialization.deserializeInfrastructure(f.getAbsolutePath());
            for (InfrastructureComponentModel m : l)
                this.addModelToTable(m);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillArraylist() {
        model.reloadList();
        this.list.addAll(model.getDatabaseModels());
        this.list.addAll(model.getWebModels());
        this.list.addAll(model.getFirewallModels());
    }

    //Function for updating/reloading combobox components
    public void updateComboboxes() {
        this.isUpdatingComboboxes = true;
        panel.getJcDatabase().removeAllItems();
        panel.getJcWeb().removeAllItems();
        panel.getJcFirewall().removeAllItems();
//        System.out.println("Items removed");
        model.reloadList();

        ArrayList<ComponentModel> l = model.getDatabaseModels();
        for (ComponentModel m : l)
            panel.getJcDatabase().addItem(m.getName());

        l = model.getWebModels();
        for (ComponentModel m : l)
            panel.getJcWeb().addItem(m.getName());

        l = model.getFirewallModels();
        for (ComponentModel m : l)
            panel.getJcFirewall().addItem(m.getName());

        panel.getJcFirewall().setSelectedIndex(-1);
        panel.getJcWeb().setSelectedIndex(-1);
        panel.getJcDatabase().setSelectedIndex(-1);

        this.isUpdatingComboboxes = false;
    }

    //Update price in design panel when component is added
    public void updatePrice() {
        ArrayList<InfrastructureComponentModel> l = getCurrentModels();
        double price = CalculateComponent.calculatePrice(l);
        panel.getJlPrice().setText("€" + String.valueOf(price));
    }

    //Update availibility in design panel when component is added
    public void updateAvailability() {
        ArrayList<InfrastructureComponentModel> l = getCurrentModels();
        double beschikbaarheid = CalculateComponent.calculateAvailability(l);
        panel.getJlAvailability().setText(String.valueOf(beschikbaarheid) + "%");
    }

    //Add component to table
    private void addModelToTable(InfrastructureComponentModel model) {
        panel.getTableModel().addRow(new Object[]{model.getType().name(), model.getName(), String.valueOf(model.getAvailability()), String.valueOf(model.getPrice()), String.valueOf(model.getAmount()), " + ", " - ", "Verwijder"});
    }

    private void addInfModelsToTable(ArrayList<InfrastructureComponentModel> l) {
        for (InfrastructureComponentModel model : l)
            panel.getTableModel().addRow(new Object[]{model.getType().name(), model.getName(), String.valueOf(model.getAvailability()), String.valueOf(model.getPrice()), String.valueOf(model.getAmount()), " + ", " - ", "Verwijder"});
    }

    private void clearTable() {
        panel.getTableModel().setRowCount(0);
    }

    @Override
    public void tableChanged(TableModelEvent tableModelEvent) {
        updateAvailability();
        updatePrice();
        graphicsPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update table with combobox
        if (e.getSource() instanceof JComboBox && !isUpdatingComboboxes) {
            JComboBox cb = (JComboBox) e.getSource();
            String item = String.valueOf(cb.getSelectedItem());
            cb.setSelectedIndex(-1);
            ComponentModel model;
            if (e.getSource() == panel.getJcDatabase()) {
                // naam en componenttype is unique
                model = ComponentModel.getModel(item, ComponentType.Database);
                if (model == null) {
                    System.out.println("Unable to convert component TO DATABASE");
                    return;
                }

                boolean hasItem = false;
                int r = 0;
                for (int i = 0; i < panel.getTableModel().getRowCount(); i++) {
                    if (panel.getJTable().getValueAt(i, 0).toString().equals(model.getType().name()) && panel.getJTable().getValueAt(i, 1).toString().equals(model.getName())) {
                        hasItem = true;
                        r = i;
                    }
                }
                if (!hasItem) {
                    panel.getTableModel().addRow(new Object[]{model.getType().name(), model.getName(), String.valueOf(model.getAvailability()), String.valueOf(model.getPrice()), "1", " + ", " - ", "Verwijder"});
                } else {
                    panel.getTableModel().setValueAt(Integer.parseInt(panel.getJTable().getValueAt(r, 4).toString()) + 1, r, 4);
                }
            }

            if (e.getSource() == panel.getJcWeb()) {
                model = ComponentModel.getModel(item, ComponentType.Webserver);
                if (model == null) {
                    System.out.println("Unable to convert component TO WEBSERVER");
                    return;
                }

                boolean hasItem = false;
                int r = 0;
                for (int i = 0; i < panel.getTableModel().getRowCount(); i++) {
                    if (panel.getJTable().getValueAt(i, 0).toString().equals(model.getType().name()) && panel.getJTable().getValueAt(i, 1).toString().equals(model.getName())) {
                        hasItem = true;
                        r = i;
                    }
                }
                if (!hasItem) {
                    panel.getTableModel().addRow(new Object[]{model.getType().name(), model.getName(), String.valueOf(model.getAvailability()), String.valueOf(model.getPrice()), "1", " + ", " - ", "Verwijder"});
                } else {
                    panel.getTableModel().setValueAt(Integer.parseInt(panel.getJTable().getValueAt(r, 4).toString()) + 1, r, 4);
                }
            }

            if (e.getSource() == panel.getJcFirewall()) {
                model = ComponentModel.getModel(item, ComponentType.Firewall);
                if (model == null) {
                    System.out.println("Unable to convert component TO FIREWALL");
                    return;
                }

                boolean hasItem = false;
                int r = 0;
                for (int i = 0; i < panel.getTableModel().getRowCount(); i++) {
                    if (panel.getJTable().getValueAt(i, 0).toString().equals(model.getType().name()) && panel.getJTable().getValueAt(i, 1).toString().equals(model.getName())) {
                        hasItem = true;
                        r = i;
                    }
                }
                if (!hasItem) {
                    panel.getTableModel().addRow(new Object[]{model.getType().name(), model.getName(), String.valueOf(model.getAvailability()), String.valueOf(model.getPrice()), "1", " + ", " - ", "Verwijder"});
                } else {
                    panel.getTableModel().setValueAt(Integer.parseInt(panel.getJTable().getValueAt(r, 4).toString()) + 1, r, 4);
                }
            }

            //Update prijs & beschikbaarheid
            updatePrice();
            updateAvailability();
        }
        //Save table content to file
        if (e.getSource() == panel.getSaveButton()) {
            String filePath;

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Opslaan Als");
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    }
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
                if (!filePath.endsWith(".json")) {
                    filePath = filePath + ".json";
                }
                System.out.println("Save as file: " + filePath);
            } else {
                return;
            }


            ArrayList<InfrastructureComponentModel> l = getCurrentModels();
            try {
                Serialization.serializeInfrastructure(l, filePath);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == panel.getJbOpt()) {
            AvailabiltyDialog dia = new AvailabiltyDialog(mfv);

            boolean isDialogClosed = dia.isAvailabilityDialogOk();
            double availability = dia.getAvailability();

            list.clear();

            if (dia.isAllcomponents()) {
                fillArraylist();
            } else {
                list = convertInfraComponentToComponent(getCurrentModels());
            }

//            System.out.println(Arrays.toString(list.toArray()));

            clearTable();

            if (isDialogClosed) {
                algorithm = new Algorithm(availability, list, dia.getServerCount());
                addInfModelsToTable(algorithm.getList());
                panel.getJlPrice().setText("€" + algorithm.getBestSolutionPrice());
                panel.getJlAvailability().setText(algorithm.getBestSolutionAvailabilty() + "%");
            }
        }
    }
    //Convert a arraylist of Infrastructurecomponents into an arraylist of components
    public ArrayList<ComponentModel> convertInfraComponentToComponent(ArrayList<InfrastructureComponentModel> infraComponents) {
        ArrayList<ComponentModel> componentModelList = new ArrayList<>();

        for (InfrastructureComponentModel infraComponent : infraComponents) {
            componentModelList.add(new ComponentModel(infraComponent.getName(), infraComponent.getAvailability(), infraComponent.getPrice(), infraComponent.getType()));
        }
        return componentModelList;
    }


    //Get components from table
    public ArrayList<InfrastructureComponentModel> getCurrentModels() {
        ArrayList<InfrastructureComponentModel> l = new ArrayList<InfrastructureComponentModel>();
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

            l.add(new InfrastructureComponentModel(panel.getTableModel().getValueAt(i, 1).toString(),
                    Double.parseDouble(panel.getTableModel().getValueAt(i, 2).toString()),
                    Double.parseDouble(panel.getTableModel().getValueAt(i, 3).toString()),
                    type,
                    Integer.parseInt(panel.getTableModel().getValueAt(i, 4).toString())));
        }
        return l;
    }
}

