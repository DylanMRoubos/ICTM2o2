package site.nerdygadgets;

import javax.swing.*;
import java.awt.*;

public class ConsoleJP extends JPanel {
    private JTextArea console;
    private JLabel title;

    public ConsoleJP() {

        title = new JLabel("Infrastructuur Console:");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        console = new JTextArea("joe");
//        console.setEditable(false);
        console.setBackground(Color.BLACK);
        console.setForeground(Color.GREEN);
        console.setPreferredSize(new Dimension(350, 700));
        add(title);
        add(console);

        setVisible(true);
    }

    public JTextArea getConsole() {
        return console;
    }

    public void setConsoleText(String Text) {
        console.setText(console.getText() + Text);
    }
}
