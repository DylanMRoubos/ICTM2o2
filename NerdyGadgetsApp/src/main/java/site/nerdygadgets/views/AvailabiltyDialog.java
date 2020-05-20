package site.nerdygadgets.views;

import site.nerdygadgets.controllers.DesignController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AvailabiltyDialog extends JDialog implements ActionListener {

    private JButton okButton;
    private JTextField availabilityJTF, amountJTF;
    private JLabel amountJL;
    private int serverCount;
    private boolean allcomponents;
    private JRadioButton allComponentsJR, chosenComponentsJR;
    private ButtonGroup componentsBG;


    public AvailabiltyDialog(JFrame f) {
        super(f, true);
        setTitle("Beschikbaarheid");
        setSize(500, 150);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(3, 3, 3, 3);

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
        amountJTF = new JTextField(5);
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

        componentsBG = new ButtonGroup();
        componentsBG.add(allComponentsJR);
        componentsBG.add(chosenComponentsJR);

        setVisible(true);
    }

    private double availability;
    private boolean ok;

    public double getAvailability() {
        return availability;
    }

    public boolean isAllcomponents() {
        return allcomponents;
    }

    public boolean isOk() {
        return ok;
    }

    private void showError(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage);
    }

    public int getServerCount() {
        return serverCount;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            try {
                availability = Double.parseDouble(availabilityJTF.getText());
                serverCount = Integer.parseInt(amountJTF.getText());
                if (availability > 0 && availability < 100) {
                    if (chosenComponentsJR.isSelected()) {
                        allcomponents = false;
                    } else {
                        allcomponents = true;
                    }

                    ok = true;
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
