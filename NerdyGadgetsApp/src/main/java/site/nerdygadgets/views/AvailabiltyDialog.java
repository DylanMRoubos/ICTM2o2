package site.nerdygadgets.views;

import site.nerdygadgets.controllers.DesignController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AvailabiltyDialog extends JDialog implements ActionListener {

    private JButton okButton;
    private JTextField availabilityJTF;
    private JRadioButton allComponentsJR, chosenComponentsJR;
    private ButtonGroup componentsBG;


    public AvailabiltyDialog(JFrame f) {
        super(f, true);
        setTitle("Beschikbaarheid");
        setSize(500,125);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(3,3,3,3);

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
        allComponentsJR = new JRadioButton("Gebruik alle componenten");
        add(allComponentsJR, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        chosenComponentsJR = new JRadioButton("Gebruik gekozen componenten (linker tabel)");
        add(chosenComponentsJR, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 2;
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

    public boolean isOk() {
        return ok;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            availability = Double.parseDouble(availabilityJTF.getText());

            ok = true;

            dispose();
        }
    }
}
