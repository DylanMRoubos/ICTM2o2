package site.nerdygadgets;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

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
        south.setLayout(new GridLayout(0, 1,0,10));
        south.setBorder(BorderFactory.createEmptyBorder(0,20,550,20));

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
        if(!App.scriptRunning) {
            new Thread(() -> {
                App.scriptRunning = true;
                // clear gui console
                frame.getConsoleJP().clearText();
                frame.getConsoleJP().appendConsoleText("Start test: Load balancing\n");


                // webserver 1 uitzetten (alleen apache)
                frame.getConsoleJP().appendConsoleText("Stopping web1\n");
                serviceApache("stop", w1);
                frame.getConsoleJP().appendConsoleText("Stopped web1\n");

                // wacht 20s
                frame.getConsoleJP().appendConsoleText("Waiting for load balancer to catch up\n");
                wait(20);

                // verbind met site (status.php) > result wordt "webserver 2"
                frame.getConsoleJP().appendConsoleText("Connect to site...\n");
                String result1 = connectToSite();
                if (result1.equals("")) {
                    frame.getConsoleJP().appendConsoleText("Connection failed!\n");
                } else {
                    frame.getConsoleJP().appendConsoleText("Connected to: " + result1 + "\n");
                }

                // web server 1 aanzetten
                frame.getConsoleJP().appendConsoleText("Starting web1\n");
                serviceApache("start", w1);
                frame.getConsoleJP().appendConsoleText("Started web1\n");

                // wait 5s
                wait(5);

                // web server 2 uitzetten
                frame.getConsoleJP().appendConsoleText("Stopping web2\n");
                serviceApache("stop", w2);
                frame.getConsoleJP().appendConsoleText("Stopped web2\n");

                // wacht 20s
                frame.getConsoleJP().appendConsoleText("Waiting for load balancer to catch up\n");
                wait(20);

                // verbind met site (status.php) > result wordt "webserver 1"
                frame.getConsoleJP().appendConsoleText("Connect to site...\n");
                String result2 = connectToSite();
                if (result2.equals("")) {
                    frame.getConsoleJP().appendConsoleText("Connection failed!\n");
                } else {
                    frame.getConsoleJP().appendConsoleText("Connected to: " + result2 + "\n");
                }

                // web server 2 aanzetten
                frame.getConsoleJP().appendConsoleText("Starting web2\n");
                serviceApache("start", w2);
                frame.getConsoleJP().appendConsoleText("Started web2\n");

                // done
                if(!result1.equals("") && !result2.equals("")) {
                    frame.getConsoleJP().appendConsoleText("Test Finished Successfully");
                } else {
                    frame.getConsoleJP().appendConsoleText("Test Failed");
                }

                App.scriptRunning = false;
            }).start();
        }
    }

    public void testReplicatieAndFailoverDb2toDb1() {
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
        if (!App.scriptRunning) {
            new Thread(() -> {
                App.scriptRunning = true;

                frame.getConsoleJP().clearText();
                frame.getConsoleJP().appendConsoleText("Start test: Synchroon database\n");
                wait(1);

                // insert data sql1 and show
                frame.getConsoleJP().appendConsoleText("Inserting data into sql1:\n");
                sql1.updateTestTable();
                String sql1result1 = sql1.getLastRowFromTestTable();
                frame.getConsoleJP().appendConsoleText("Result from sql1: " + sql1result1 + "\n");

                // wait 2s
                wait(2);

                // show data from sql2
                frame.getConsoleJP().appendConsoleText("Getting data from sql2:\n");
                String sql2result1 = sql2.getLastRowFromTestTable();
                frame.getConsoleJP().appendConsoleText("Result from sql2: " + sql2result1 + "\n");

                // compare
                boolean equalResultSet1;
                frame.getConsoleJP().appendConsoleText("Comparing results: ");
                if (sql1result1.equals(sql2result1)) {
                    frame.getConsoleJP().appendConsoleText("equal\n");
                    equalResultSet1 = true;
                } else {
                    frame.getConsoleJP().appendConsoleText("not equal\n");
                    equalResultSet1 = false;
                }

                // wait 5s
                wait(5);

                // insert data sql2 and show
                frame.getConsoleJP().appendConsoleText("Inserting data into sql2:\n");
                sql2.updateTestTable();
                String sql2result2 = sql2.getLastRowFromTestTable();
                frame.getConsoleJP().appendConsoleText("Result from sql2: " + sql2result2 + "\n");

                // wait 2s
                wait(2);

                // show data from sql1
                frame.getConsoleJP().appendConsoleText("Getting data from sql1:\n");
                String sql1result2 = sql1.getLastRowFromTestTable();
                frame.getConsoleJP().appendConsoleText("Result from sql1: " + sql1result2 + "\n");

                // compare
                boolean equalResultSet2;
                frame.getConsoleJP().appendConsoleText("Comparing results: ");
                if (sql1result2.equals(sql2result2)) {
                    frame.getConsoleJP().appendConsoleText("equal\n");
                    equalResultSet2 = true;
                } else {
                    frame.getConsoleJP().appendConsoleText("not equal\n");
                    equalResultSet2 = false;
                }

                // if both compare were good, test successful
                if (equalResultSet1 && equalResultSet2) {
                    frame.getConsoleJP().appendConsoleText("Test Finished Successfully");
                } else {
                    frame.getConsoleJP().appendConsoleText("Test Failed");
                }

                App.scriptRunning = false;
            }).start();
        }
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

    private String connectToSite() {
        int tries = 0;
        while (tries < 5) {
            try {
                URL url = new URL("https://www.nerdy-gadgets.site/status.php");
                HttpsURLConnection con;
                con = (HttpsURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                int status = con.getResponseCode();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                return content.toString();

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Can't connect");
            }
            tries++;
            wait(2);
        }

        return "";
    }

    public void serviceApache(String option, SSHManager s) {
        if (option.equals("stop")) {
            s.runCommandSudo("systemctl stop apache2");
        } else if (option.equals("start")) {
            s.runCommandSudo("systemctl start apache2");
        }
    }

}
