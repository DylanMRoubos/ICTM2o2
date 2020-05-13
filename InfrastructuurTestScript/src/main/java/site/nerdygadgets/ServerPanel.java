package site.nerdygadgets;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerPanel extends JPanel implements ActionListener {
    private SSHManager sshManager;
    private String type;
    private JButton ndbsJB, ndbssJB, sassJB, stopwsJB, startwsJB, restartwsJB, ndbdsJB, stopdbsJB, startdbsJB, restartdbsJB, shutdownJB, rebootJB;
    private TestScriptFrame frame;

    public ServerPanel(TestScriptFrame frame, String type, String host, String user, String password) {
        this.frame = frame;

        sshManager = new SSHManager(user, password, host);
        sshManager.startSession();

        if (type.equals("web")) {
            // buttons voor web
            ndbsJB = new JButton("ndb_mgm status");
            ndbssJB = new JButton("service status ndb"); // ndb mgm and mysql
            sassJB = new JButton("service status apache2");
            stopwsJB = new JButton("stop services");
            startwsJB = new JButton("start services");
            restartwsJB = new JButton("restart services");

            ndbsJB.addActionListener(this);
            ndbssJB.addActionListener(this);
            sassJB.addActionListener(this);
            stopwsJB.addActionListener(this);
            startwsJB.addActionListener(this);
            restartwsJB.addActionListener(this);

            add(ndbsJB);
            add(ndbssJB);
            add(sassJB);
            add(stopwsJB);
            add(startwsJB);
            add(restartwsJB);

        } else if (type.equals("db")) {
            // buttons voor db
            stopdbsJB = new JButton("stop services");
            startdbsJB = new JButton("start services");
            ndbdsJB = new JButton("service status ndbd");
            restartdbsJB = new JButton("restart services");

            ndbdsJB.addActionListener(this);
            stopdbsJB.addActionListener(this);
            startdbsJB.addActionListener(this);
            restartdbsJB.addActionListener(this);

            add(ndbdsJB);
            add(stopdbsJB);
            add(startdbsJB);
            add(restartdbsJB);
        }

        // buttons algemeen
        shutdownJB = new JButton("shutdown server");
        rebootJB = new JButton("reboot server");

        shutdownJB.addActionListener(this);
        rebootJB.addActionListener(this);

        add(shutdownJB);
        add(rebootJB);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!App.scriptRunning) {
            if (e.getSource() == ndbsJB) {
                new Thread(() -> {
                    App.scriptRunning = true;
                    frame.getConsoleJP().clearText();
                    frame.getConsoleJP().appendConsoleText(sshManager.runCommandSudo("ndb_mgm -e 'SHOW'"));
                    App.scriptRunning = false;
                }).start();
            } else if (e.getSource() == ndbssJB) {
                new Thread(() -> {
                    App.scriptRunning = true;
                    frame.getConsoleJP().clearText();
                    frame.getConsoleJP().appendConsoleText("ndb_mgmd: " + sshManager.runCommandSudo("systemctl status ndb_mgmd | grep Active | awk '{print $2 \" \" $3}'"));
                    frame.getConsoleJP().appendConsoleText("mysql: " + sshManager.runCommandSudo("systemctl status mysql | grep Active | awk '{print $2 \" \" $3}'"));
                    App.scriptRunning = false;
                }).start();
            } else if (e.getSource() == sassJB) {
                new Thread(() -> {
                    App.scriptRunning = true;
                    frame.getConsoleJP().clearText();
                    frame.getConsoleJP().appendConsoleText("apache2: " + sshManager.runCommandSudo("systemctl status apache2 | grep Active | awk '{print $2 \" \" $3}'"));
                    App.scriptRunning = false;
                }).start();
            } else if (e.getSource() == stopwsJB) {
                new Thread(() -> {
                    App.scriptRunning = true;
                    frame.getConsoleJP().clearText();
                    frame.getConsoleJP().appendConsoleText("stopping services:\n");
                    frame.getConsoleJP().appendConsoleText("ndb_mgmd: " + sshManager.runCommandSudo("systemctl stop ndb_mgmd") + "stopped\n");
                    frame.getConsoleJP().appendConsoleText("mysql: " + sshManager.runCommandSudo("systemctl stop mysql") + "stopped\n");
                    frame.getConsoleJP().appendConsoleText("apache2: " + sshManager.runCommandSudo("systemctl stop apache2") + "stopped\n");
                    frame.getConsoleJP().appendConsoleText("Services stopped");
                    App.scriptRunning = false;
                }).start();
            } else if (e.getSource() == startwsJB) {
                new Thread(() -> {
                    App.scriptRunning = true;
                    frame.getConsoleJP().clearText();
                    frame.getConsoleJP().appendConsoleText("starting services:\n");
                    frame.getConsoleJP().appendConsoleText("ndb_mgmd: " + sshManager.runCommandSudo("systemctl start ndb_mgmd") + "started\n");
                    frame.getConsoleJP().appendConsoleText("mysql: " + sshManager.runCommandSudo("systemctl start mysql") + "started\n");
                    frame.getConsoleJP().appendConsoleText("apache2: " + sshManager.runCommandSudo("systemctl start apache2") + "started\n");
                    frame.getConsoleJP().appendConsoleText("Services started");
                    App.scriptRunning = false;
                }).start();
            } else if (e.getSource() == restartwsJB) {
                new Thread(() -> {
                    App.scriptRunning = true;
                    frame.getConsoleJP().clearText();
                    frame.getConsoleJP().appendConsoleText("restarting services:\n");
                    frame.getConsoleJP().appendConsoleText("ndb_mgmd: " + sshManager.runCommandSudo("systemctl restart ndb_mgmd") + "restarted\n");
                    frame.getConsoleJP().appendConsoleText("mysql: " + sshManager.runCommandSudo("systemctl restart mysql") + "restarted\n");
                    frame.getConsoleJP().appendConsoleText("apache2: " + sshManager.runCommandSudo("systemctl restart apache2") + "restarted\n");
                    frame.getConsoleJP().appendConsoleText("Services restarted");
                    App.scriptRunning = false;
                }).start();
            } else if (e.getSource() == ndbdsJB) {
                new Thread(() -> {
                    App.scriptRunning = true;
                    frame.getConsoleJP().clearText();
                    frame.getConsoleJP().appendConsoleText("ndbd: " + sshManager.runCommandSudo("systemctl status ndbd | grep Active | awk '{print $2 \" \" $3}'"));
                    App.scriptRunning = false;
                }).start();
            } else if (e.getSource() == stopdbsJB) {
                new Thread(() -> {
                    App.scriptRunning = true;
                    frame.getConsoleJP().clearText();
                    frame.getConsoleJP().appendConsoleText("Stopping services:\n");
                    frame.getConsoleJP().appendConsoleText("ndbd: " + sshManager.runCommandSudo("pkill -f ndbd") + sshManager.runCommandSudo("systemctl stop ndbd") + "stopped\n");
                    frame.getConsoleJP().appendConsoleText("Services stopped");
                    App.scriptRunning = false;
                }).start();
            } else if (e.getSource() == startdbsJB) {
                new Thread(() -> {
                    App.scriptRunning = true;
                    frame.getConsoleJP().clearText();
                    frame.getConsoleJP().appendConsoleText("Starting services:\n");
                    frame.getConsoleJP().appendConsoleText("ndbd: " + sshManager.runCommandSudo("systemctl start ndbd") + "started\n");
                    frame.getConsoleJP().appendConsoleText("Services started");
                    App.scriptRunning = false;
                }).start();
            } else if (e.getSource() == restartdbsJB) {
                new Thread(() -> {
                    App.scriptRunning = true;
                    frame.getConsoleJP().clearText();
                    frame.getConsoleJP().appendConsoleText("Restarting services:\n");
                    frame.getConsoleJP().appendConsoleText("ndbd: " + sshManager.runCommandSudo("systemctl restart ndbd") + "started\n");
                    frame.getConsoleJP().appendConsoleText("Services restarted");
                    App.scriptRunning = false;
                }).start();
            } else if (e.getSource() == shutdownJB) {
                new Thread(() -> {
                    App.scriptRunning = true;
                    frame.getConsoleJP().clearText();
                    frame.getConsoleJP().appendConsoleText("Not shutting down server,\nthis is just a placeholder");
                    App.scriptRunning = false;
                }).start();
            } else if (e.getSource() == rebootJB) {
                new Thread(() -> {
                    App.scriptRunning = true;
                    frame.getConsoleJP().clearText();
                    frame.getConsoleJP().appendConsoleText("Rebooting server.....\nWhen server comes up again,\napplication must be restarted for ssh");
//                    sshManager.runCommandSudo("reboot");
                    App.scriptRunning = false;
                }).start();
            }
        }
    }
}
