package site.nerdygadgets.functions;

import site.nerdygadgets.controllers.DesignController;
import site.nerdygadgets.views.DesignPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * ButtonEditor class
 * Creates buttons in tables
 *
 * @author Tristan Scholten & Jordy Wielaard
 * @version 1.0
 * @since 14-05-2020
 */
//BUTTON EDITOR CLASS
public class ButtonEditor extends DefaultCellEditor
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
                            String.valueOf(Integer.parseInt(panel.getTableModel().getValueAt(panel.getJTable().getSelectedRow(), 4).toString())+1),
                            panel.getJTable().getSelectedRow(), 4);
                }

                if (lbl.equals(" - ")) {
                    if (Integer.parseInt(panel.getTableModel().getValueAt(panel.getJTable().getSelectedRow(), 4).toString()) > 1)
                        panel.getTableModel().setValueAt(String.valueOf(Integer.parseInt(panel.getTableModel().getValueAt(panel.getJTable().getSelectedRow(), 4).toString())-1), panel.getJTable().getSelectedRow(), 4);
                }

                if (lbl.equals("Verwijder")) {
                    panel.getTableModel().removeRow(panel.getJTable().getSelectedRow());
                }
                //Update price & Availability
                controller.updateAvailability();
                controller.updatePrice();
            }
        });
    }

    //OVERRIDE A COUPLE OF METHODS
    @Override
    public Component getTableCellEditorComponent(JTable table, Object obj, boolean selected, int row, int col) {

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
        super.fireEditingStopped();
    }
}