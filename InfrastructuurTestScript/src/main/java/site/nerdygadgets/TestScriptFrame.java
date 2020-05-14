package site.nerdygadgets;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.*;
/**
 * TestScriptFrame class
 * JFrame with the 3 three main components in a gridlayout
 *
 * @author Mike Thomas & Dylan Roubos
 * @version 1.0
 * @since 12-05-2020
 */
public class TestScriptFrame extends JFrame {
    private ConsoleJP consoleJP;
    private ProcedureJP procedureJP;
    private ServerJP serverJP;

    public TestScriptFrame() {
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 3));

        procedureJP = new ProcedureJP(this);
        serverJP = new ServerJP(this);
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
