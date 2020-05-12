package site.nerdygadgets;

import javax.swing.*;
import java.awt.*;

public class ProcedureJP extends JPanel {
    private JLabel title;
    public ProcedureJP() {
        title = new JLabel("Infrastructuur Procedures:");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        add(title);

        setVisible(true);
    }
}
