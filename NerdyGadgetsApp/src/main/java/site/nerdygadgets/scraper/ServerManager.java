package site.nerdygadgets.scraper;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ServerManager extends TimerTask {
    private ArrayList<Server> servers;

    public ServerManager() {
        Database database = new Database("mongodb+srv://admin:admin@cluster0-gzerr.mongodb.net/test?retryWrites=true&w=majority");

        Server web1 = new Server(database,1, ServerType.WEB, "172.16.0.190", "student", "KHxd4gu7");
        Server web2 = new Server(database,2, ServerType.WEB, "172.16.0.191", "student", "KHxd4gu7");
        Server database1 = new Server(database,3, ServerType.DATABASE, "172.16.0.158", "student", "wd9AdEuN");
        Server database2 = new Server(database,4, ServerType.DATABASE, "172.16.0.159", "student", "wd9AdEuN");
        Server pfsense = new Server(database, 5, ServerType.PFSENSE, "172.16.0.1", "console", "pr7cmHKNX6VhPaHc");

        servers = new ArrayList<>();
        servers.add(web1);
        servers.add(web2);
        servers.add(database1);
        servers.add(database2);
        servers.add(pfsense);

        Timer timer = new Timer();
        timer.schedule(this, 0,30000);
    }

    public void run() {
        for (Server server : servers) {
            server.grabData();
            System.out.println(server);
        }
        for (Server server : servers) {
            server.writeToDatabase();
            System.out.println("wrote to database");
        }
    }
}
