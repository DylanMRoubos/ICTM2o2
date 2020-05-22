package site.nerdygadgets;

import javax.swing.*;
import java.awt.*;

/**
 * ConsoleJP class
 * JPanel with the console to display command output from SSH
 *
 * @author Mike Thomas & Dylan Roubos
 * @version 1.0
 * @since 12-05-2020
 */
public class ConsoleJP extends JPanel {
    private JTextArea console;
    private JLabel title;

    public ConsoleJP() {

        //Add backgroundcolor to the console while setEditable is false
        UIManager.put("TextField.inactiveBackground", Color.black);

        title = new JLabel("Infrastructuur Console:");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        console = new JTextArea();
        console.setEditable(false);
        console.setBackground(Color.BLACK);
        console.setForeground(Color.GREEN);
        console.setPreferredSize(new Dimension(350, 700));
        add(title);
        add(console);

        setVisible(true);
    }

    //Append text to console
    public void appendConsoleText(String Text) {
        console.setText(console.getText() + Text);
    }

    //Clear consoleText
    public void clearText() {
        console.setText("");
    }
}
