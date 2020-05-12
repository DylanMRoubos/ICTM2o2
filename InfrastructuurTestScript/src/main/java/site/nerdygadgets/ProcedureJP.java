package site.nerdygadgets;

import javax.swing.*;
import java.awt.*;
/**
 * ProcedureJP class
 * JPanel with the server procedures to be tested
 *
 * @author Mike Thomas & Dylan Roubos
 * @version 1.0
 * @since 12-05-2020
 */
public class ProcedureJP extends JPanel {
    private JLabel title;
    public ProcedureJP() {
        title = new JLabel("Infrastructuur Procedures:");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        add(title);

        setVisible(true);
    }
}
