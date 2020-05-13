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
public class ProcedureJP extends JPanel implements ActionListener {
    private JLabel title;
    private SSHManager w1, w2, db1, db2;
    private DbConnection sql1, sql2;
    private TestScriptFrame frame;
    //TODO: knoppen toevoegen


    public ProcedureJP(TestScriptFrame frame) {
        this.frame = frame;
        title = new JLabel("Infrastructuur Procedures:");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        add(title);

        setVisible(true);
    }

    public void testLoadBalancing() {
        //TODO: implement: webserver1 uit -> wachten (15 sec) -> verbinden met site -> webserver 1 aan -> wachten (10 sec) -> webserver 2 uit -> wachten (15 sec) -> verbinden met site (als dit succesvol is klaar). -> alles aan.
    }

    public void testReplicatieAndFailoverDb1toDb2() {
        //TODO: implement: DB 1 uit -> invoeren in DB (dit gebeurd op db2) -> DB 1 aan -> DB 2 uit->  Data ophalen (dit gebeurd dan op db 1) -> alles aan
    }

    public void testReplicatieAndFailoverDb2toDb1() {
        //TODO: implement:DB 2 uit -> invoeren in DB (dit gebeurd op db1) -> DB 2 aan -> DB 1 uit->  Data ophalen (dit gebeurd dan op db 2) -> alles aan
    }

    public void testWebServerSynchronous() {
        //TODO: implement: Connectie web1 (sql) -> invoeren data web1 -> connectie web2 -> checken of data is doorgevoerd op web2 -> alles aan
    }

    //TODO: implement: actionlistener
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
