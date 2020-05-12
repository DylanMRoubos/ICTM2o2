package site.nerdygadgets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ServerJP class
 * JPanel with the servers in a card layout which can be switched
 * Individual server commands can be run from this JPanel
 *
 * @author Mike Thomas & Dylan Roubos
 * @version 1.0
 * @since 12-05-2020
 */
public class ServerJP extends JPanel implements ActionListener {
    private JLabel title;
    private JPanel north, south;
    private ServerPanel s1, s2, s3, s4;
    private JToggleButton t1, t2, t3, t4;
    private TestScriptFrame frame;

    public ServerJP(TestScriptFrame frame) {
        this.frame = frame;

        setLayout(new BorderLayout());

        north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));

        title = new JLabel("Commands per server:");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        north.add(title);
        north.setPreferredSize(new Dimension(0, 75));

        t1 = new JToggleButton("Web1");
        t1.addActionListener(this);
        t2 = new JToggleButton("Web2");
        t2.addActionListener(this);
        t3 = new JToggleButton("DB1");
        t3.addActionListener(this);
        t4 = new JToggleButton("DB2");
        t4.addActionListener(this);

        JPanel buttonRow = new JPanel(new GridLayout(0, 4));
        buttonRow.add(t1);
        buttonRow.add(t2);
        buttonRow.add(t3);
        buttonRow.add(t4);

        buttonRow.setAlignmentX(Component.CENTER_ALIGNMENT);
        north.add(buttonRow);

        south = new JPanel(new CardLayout(20, 20));

        s1 = new ServerPanel(frame, "web", "172.16.0.190", "student", "KHxd4gu7");
        s2 = new ServerPanel(frame, "web", "172.16.0.191", "student", "KHxd4gu7");
        s3 = new ServerPanel(frame, "db", "172.16.0.158", "student", "wd9AdEuN");
        s4 = new ServerPanel(frame, "db", "172.16.0.159", "student", "wd9AdEuN");

        south.add(s1, "S1");
        south.add(s2, "S2");
        south.add(s3, "S3");
        south.add(s4, "S4");

        add(north, BorderLayout.NORTH);
        add(south, BorderLayout.CENTER);

        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));

        t1.setSelected(true);
        setVisible(true);
    }

    //Switch panels
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!App.scriptRunning) {
            if (e.getSource() == t1) {
                CardLayout cl = (CardLayout) south.getLayout();
                cl.show(south, "S1");
                t1.setSelected(true);
                t2.setSelected(false);
                t3.setSelected(false);
                t4.setSelected(false);
            } else if (e.getSource() == t2) {
                CardLayout cl = (CardLayout) south.getLayout();
                cl.show(south, "S2");
                t1.setSelected(false);
                t2.setSelected(true);
                t3.setSelected(false);
                t4.setSelected(false);
            } else if (e.getSource() == t3) {
                CardLayout cl = (CardLayout) south.getLayout();
                cl.show(south, "S3");
                t1.setSelected(false);
                t2.setSelected(false);
                t3.setSelected(true);
                t4.setSelected(false);
            } else if (e.getSource() == t4) {
                CardLayout cl = (CardLayout) south.getLayout();
                cl.show(south, "S4");
                t1.setSelected(false);
                t2.setSelected(false);
                t3.setSelected(false);
                t4.setSelected(true);
            }
        }
    }
}
