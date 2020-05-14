package site.nerdygadgets.views;

import site.nerdygadgets.controllers.DesignController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AvailabiltyDialog extends JDialog implements ActionListener {

    private JButton okButton;
    private JTextField availabilityJTF;

    public AvailabiltyDialog(JFrame f) {
        super(f, true);
        setTitle("Beschikbaarheiddialoog");
        setSize(300,150);
        setLayout(new FlowLayout());

        add(new JLabel("Beschikbaarheid: "));

        availabilityJTF = new JTextField(10);
        add(availabilityJTF);

        okButton = new JButton("OK");
        okButton.addActionListener(this);
        add(okButton);

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
