package site.nerdygadgets;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.*;

public class TestScriptFrame extends JFrame {
    private ConsoleJP consoleJP;
    private ProcedureJP procedureJP;
    private ServerJP serverJP;

    public TestScriptFrame() {
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 3));

        procedureJP = new ProcedureJP();
        serverJP = new ServerJP();
        consoleJP = new ConsoleJP();

        add(procedureJP);
        add(serverJP);
        add(consoleJP);


        setVisible(true);
    }

    public JPanel getProcedureJP() {
        return procedureJP;
    }

    public JPanel getServerJP() {
        return serverJP;
    }

    public ConsoleJP getConsoleJP() {
        return consoleJP;
    }
}
