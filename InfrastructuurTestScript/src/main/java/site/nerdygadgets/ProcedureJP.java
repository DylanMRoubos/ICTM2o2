package site.nerdygadgets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private SSHManager w1, w2, db1, db2;
    private DbConnection sql1, sql2;
    private TestScriptFrame frame;
    //TODO: knoppen toevoegen
    private JButton p1, p2, p3, p4;
    private JPanel north, south;


    public ProcedureJP(TestScriptFrame frame) {
        this.frame = frame;
        title = new JLabel("Infrastructuur Procedures:");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        setLayout(new BorderLayout());

        north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        north.add(title);
        north.setPreferredSize(new Dimension(0, 40));

        south = new JPanel();

        p1 = new JButton("Test Site load balancing");
        p2 = new JButton("Test SQL synchroon op webservers");
        p3 = new JButton("Test Replicatie & failover DB2 > DB1");
        p4 = new JButton("Test Replicatie & failover DB1 > DB2");

        p1.addActionListener(e -> testLoadBalancing());
        p2.addActionListener(e -> testWebServerSynchronous());
        p3.addActionListener(e -> testReplicatieAndFailoverDb2toDb1());
        p4.addActionListener(e -> testReplicatieAndFailoverDb1toDb2());

        south.add(p1);
        south.add(p2);
        south.add(p3);
        south.add(p4);

        add(north, BorderLayout.NORTH);
        add(south, BorderLayout.CENTER);

        // initialise servers
        w1 = new SSHManager("student", "KHxd4gu7", "172.16.0.190");
        w2 = new SSHManager("student", "KHxd4gu7", "172.16.0.191");
        db1 = new SSHManager("student", "wd9AdEuN", "172.16.0.158");
        db2 = new SSHManager("student", "wd9AdEuN", "172.16.0.159");
        sql1 = new DbConnection(1,"172.16.0.190", "root", "ArshhU8K", "wideworldimporters");
        sql2 = new DbConnection(2,"172.16.0.191", "root", "ArshhU8K", "wideworldimporters");

        w1.startSession();
        w2.startSession();
        db1.startSession();
        db2.startSession();

        setVisible(true);
    }

    public void testLoadBalancing() {
        //TODO: implement: webserver1 uit -> wachten (15 sec) -> verbinden met site -> webserver 1 aan -> wachten (10 sec) -> webserver 2 uit -> wachten (15 sec) -> verbinden met site (als dit succesvol is klaar). -> alles aan.
        System.out.println("dit moet nog geïmplementeerd worden");
    }

    public void testReplicatieAndFailoverDb2toDb1() {
        //TODO: implement: DB 1 uit -> invoeren in DB (dit gebeurd op db2) -> DB 1 aan -> DB 2 uit->  Data ophalen (dit gebeurd dan op db 1) -> alles aan
        if(!App.scriptRunning) {
            new Thread(() -> {
                App.scriptRunning = true;
                // clear gui console
                frame.getConsoleJP().clearText();
                frame.getConsoleJP().appendConsoleText("Start test: DB2 -> DB 1 Replicatie & Failover\n");

                // stop db1 and log
                frame.getConsoleJP().appendConsoleText("Stopping data node 1..\n");
                servicesDataNode("stop", db1);
                frame.getConsoleJP().appendConsoleText("Stopped data node 1\nWaiting 5s...\n");
                wait(5);

                // do update and log
                frame.getConsoleJP().appendConsoleText("Inserting data into db..\n");
                sql1.updateTestTable();
                wait(1);
                String result1 = sql1.getLastRowFromTestTable();
                frame.getConsoleJP().appendConsoleText("Updated record into DB:\n" + result1 + "\n");

                wait(5);
                // start db1
                frame.getConsoleJP().appendConsoleText("Starting data node 1..\n");
                servicesDataNode("start", db1);
                frame.getConsoleJP().appendConsoleText("Started data node 1, waiting 30s to start up\n");

                // wait 30s for sync
                wait(30);
                frame.getConsoleJP().appendConsoleText("Data node 1 up\n");

                // stop db2
                frame.getConsoleJP().appendConsoleText("Stopping data node 2..\n");
                servicesDataNode("stop", db2);
                frame.getConsoleJP().appendConsoleText("Stopped data node 2\n");
                wait(5);

                // do select query to check if data is correct
                frame.getConsoleJP().appendConsoleText("Do select query on data node 1 to check\nif data has been replicated.\n");
                String result2 = sql1.getLastRowFromTestTable();
                frame.getConsoleJP().appendConsoleText(result2 + "\n");
                wait(5);

                // start db2 back up
                frame.getConsoleJP().appendConsoleText("start data node 2 again\n");
                servicesDataNode("start", db2);

                // compare results
                frame.getConsoleJP().appendConsoleText("Comparing results:\nResult 1: " + result1 + "\nResult 2: " + result2 + "\n");
                if (result1.equals(result2)) {
                    frame.getConsoleJP().appendConsoleText("Test Finished Successfully.\n");
                } else {
                    frame.getConsoleJP().appendConsoleText("Test Failed.\n");
                }
                App.scriptRunning = false;
            }).start();
        }
    }

    public void testReplicatieAndFailoverDb1toDb2() {
        //TODO: implement:DB 2 uit -> invoeren in DB (dit gebeurd op db1) -> DB 2 aan -> DB 1 uit->  Data ophalen (dit gebeurd dan op db 2) -> alles aan
        if(!App.scriptRunning) {
            new Thread(() -> {
                App.scriptRunning = true;
                // clear gui console
                frame.getConsoleJP().clearText();
                frame.getConsoleJP().appendConsoleText("Start test: DB1 -> DB 2 Replicatie & Failover\n");

                // stop db2 and log
                frame.getConsoleJP().appendConsoleText("Stopping data node 2..\n");
                servicesDataNode("stop", db2);
                frame.getConsoleJP().appendConsoleText("Stopped data node 2\nWaiting 5s...\n");
                wait(5);

                // do update and log
                frame.getConsoleJP().appendConsoleText("Inserting data into db..\n");
                sql1.updateTestTable();
                wait(1);
                String result1 = sql2.getLastRowFromTestTable();
                frame.getConsoleJP().appendConsoleText("Updated record into DB:\n" + result1 + "\n");

                wait(5);
                // start db2
                frame.getConsoleJP().appendConsoleText("Starting data node 2..\n");
                servicesDataNode("start", db2);
                frame.getConsoleJP().appendConsoleText("Started data node 2, waiting 30s to start up\n");

                // wait 30s for sync
                wait(30);
                frame.getConsoleJP().appendConsoleText("Data node 2 up\n");

                // stop db1
                frame.getConsoleJP().appendConsoleText("Stopping data node 1..\n");
                servicesDataNode("stop", db1);
                frame.getConsoleJP().appendConsoleText("Stopped data node 1\n");
                wait(5);

                // do select query to check if data is correct
                frame.getConsoleJP().appendConsoleText("Do select query on data node 2 to check\nif data has been replicated.\n");
                String result2 = sql2.getLastRowFromTestTable();
                frame.getConsoleJP().appendConsoleText(result2 + "\n");
                wait(5);

                // start db1 back up
                frame.getConsoleJP().appendConsoleText("start data node 1 again\n");
                servicesDataNode("start", db1);

                // compare results
                frame.getConsoleJP().appendConsoleText("Comparing results:\nResult 1: " + result1 + "\nResult 2: " + result2 + "\n");
                if (result1.equals(result2)) {
                    frame.getConsoleJP().appendConsoleText("Test Finished Successfully.\n");
                } else {
                    frame.getConsoleJP().appendConsoleText("Test Failed.\n");
                }
                App.scriptRunning = false;
            }).start();
        }
    }

    public void testWebServerSynchronous() {
        //TODO: implement: Connectie web1 (sql) -> invoeren data web1 -> connectie web2 -> checken of data is doorgevoerd op web2 -> alles aan
        System.out.println("dit moet nog geïmplementeerd worden");
    }

    public void servicesDataNode(String option, SSHManager s) {
        if (option.equals("stop")) {
            if (s.equals(db1)) {
                w1.runCommandSudo("ndb_mgm -e '3 stop'");
            } else if (s.equals(db2)) {
                w1.runCommandSudo("ndb_mgm -e '4 stop'");
            }
            s.runCommandSudo("systemctl stop ndbd");
        } else if (option.equals("start")) {
            s.runCommandSudo("systemctl start ndbd");
        }
    }

    private void wait(int s) {
        try {
            Thread.sleep(s*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
