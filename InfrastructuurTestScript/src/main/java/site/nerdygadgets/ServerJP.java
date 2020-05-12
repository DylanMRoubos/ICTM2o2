package site.nerdygadgets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerJP extends JPanel implements ActionListener {
    private JLabel title;
    private JPanel north, south, s1, s2, s3, s4;
    private JButton t1, t2, t3, t4;

    public ServerJP() {

        setLayout(new BorderLayout());

        north = new JPanel(new FlowLayout());

        title = new JLabel("Commands per server:            ");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        north.add(title);
        north.setPreferredSize(new Dimension(0, 75));

        t1 = new JButton("Web1");
        t1.addActionListener(this);
        t2 = new JButton("Web2");
        t2.addActionListener(this);
        t3 = new JButton("DB1");
        t3.addActionListener(this);
        t4 = new JButton("DB2");
        t4.addActionListener(this);

        north.add(t1);
        north.add(t2);
        north.add(t3);
        north.add(t4);

        south = new JPanel(new CardLayout(20, 20));

        s1 = new JPanel();
        s2 = new JPanel();
        s3 = new JPanel();
        s4 = new JPanel();

        s1.add(new JLabel("joe1"));
        s2.add(new JLabel("joe2"));
        s3.add(new JLabel("joe3"));
        s4.add(new JLabel("joe4"));


        south.add(s1, "S1");
        south.add(s2, "S2");
        south.add(s3, "S3");
        south.add(s4, "S4");

        add(north, BorderLayout.NORTH);
        add(south, BorderLayout.CENTER);

        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == t1) {
            CardLayout cl = (CardLayout) south.getLayout();
            cl.show(south, "S1");
        } else if (e.getSource() == t2) {
            CardLayout cl = (CardLayout) south.getLayout();
            cl.show(south, "S2");
        } else if (e.getSource() == t3) {
            CardLayout cl = (CardLayout) south.getLayout();
            cl.show(south, "S3");
        } else if (e.getSource() == t4) {
            CardLayout cl = (CardLayout) south.getLayout();
            cl.show(south, "S4");
        }
    }
}
