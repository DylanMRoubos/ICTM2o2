package site.nerdygadgets.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * AvailabilityDialog class
 * Class create a dialog which let's you configure settings for the algorithm to use
 *
 * @author Ade Wattimena & Ruben Oosting & Dylan Roubos
 * @version 1.0
 * @since 20-05-2020
 */
public class AvailabiltyDialog extends JDialog implements ActionListener {

    private int serverCount;
    private boolean allComponents, availabilityDialogOk;
    private double availability;
    private JButton okButton;
    private ButtonGroup componentsBG;
    private JTextField availabilityJTF, amountJTF;
    private JLabel amountJL;
    private JRadioButton allComponentsJR, chosenComponentsJR;

    public AvailabiltyDialog(JFrame f) {
        super(f, true);
        setTitle("Beschikbaarheid");
        setSize(500, 150);
        setLocationRelativeTo(null);

        //Create gridbaglayout to set different size buttons
        setLayout(new GridBagLayout());

        //Create gridbag settings
        GridBagConstraints c = new GridBagConstraints();

        //Add padding around gribdag
        c.insets = new Insets(3, 3, 3, 3);

        //initialise and set all components to the gridbaglayout
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        add(new JLabel("Beschikbaarheid: "), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        availabilityJTF = new JTextField(10);
        add(availabilityJTF, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        amountJL = new JLabel("Hoeveelheid servers: ");
        add(amountJL, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        amountJTF = new JTextField("10", 5);
        add(amountJTF, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        allComponentsJR = new JRadioButton("Gebruik alle componenten");
        allComponentsJR.setSelected(true);
        add(allComponentsJR, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 2;
        chosenComponentsJR = new JRadioButton("Gebruik gekozen componenten (linker tabel)");
        add(chosenComponentsJR, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 3;
        okButton = new JButton("OK");
        okButton.addActionListener(this);
        add(okButton, c);

        //Create a button group for the radio buttons
        componentsBG = new ButtonGroup();
        componentsBG.add(allComponentsJR);
        componentsBG.add(chosenComponentsJR);

        setVisible(true);
    }

    public double getAvailability() {
        return availability;
    }

    public boolean isAllComponents() {
        return allComponents;
    }

    public boolean isAvailabilityDialogOk() {
        return availabilityDialogOk;
    }

    //Simple method to display error message
    private void showError(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage);
    }

    public int getServerCount() {
        return serverCount;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //check if button pressed is the ok button
        if (e.getSource() == okButton) {
            //Try to get data from the jtextfields. If failed throw numberformat if data could not be formatted. If something else fails throw default exception
            try {
                //Get the data from JTextfield and put it in variables
                availability = Double.parseDouble(availabilityJTF.getText());
                serverCount = Integer.parseInt(amountJTF.getText());
                //Check if wished availability percentage is within the possible range
                if (availability > 0 && availability <= 100) {
                    //Check if all components should be used or only the selected
                    if (chosenComponentsJR.isSelected()) {
                        allComponents = false;
                    } else {
                        allComponents = true;
                    }
                    //set dialog to ok and close
                    availabilityDialogOk = true;
                    dispose();
                } else {
                    showError("Percentage moet tussen 0 en 100");
                }

            } catch (NumberFormatException ex) {
                showError("Geef een geldig getal op");
            } catch (Exception exc) {
                showError("Foutmelding");
            }
        }
    }
}
