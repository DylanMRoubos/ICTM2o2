package site.nerdygadgets.scraper;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
/**
 * ServerManager class
 * A class which creates the servers and based on a timer grabs data and puts that in a database.
 * Runs every 30 seconds.
 *
 * @author Mike Thomas & Dylan Roubos
 * @version 1.0
 * @since 30-04-2020
 */
public class ServerManager extends TimerTask {
    private ArrayList<Server> servers;

    public ServerManager(Database database) {

        // instantiate arraylist and instantiate servers inside list
        servers = new ArrayList<>();
        servers.add(new Server(database,1, ServerType.WEB, "172.16.0.190", "student", "KHxd4gu7"));
        servers.add(new Server(database,2, ServerType.WEB, "172.16.0.191", "student", "KHxd4gu7"));
        servers.add(new Server(database,3, ServerType.DATABASE, "172.16.0.158", "student", "wd9AdEuN"));
        servers.add(new Server(database,4, ServerType.DATABASE, "172.16.0.159", "student", "wd9AdEuN"));
        servers.add(new Server(database, 5, ServerType.PFSENSE, "172.16.0.1", "console", "pr7cmHKNX6VhPaHc"));

        // instantiate timer and add self to timer.
        Timer timer = new Timer();
        timer.schedule(this, 0,30000); // run on start and then every 30 seconds.
    }

    public void run() {
        // loop through servers, grab metrics with SSH, then write to MongoDB Database.
        for (Server server : servers) {
            server.grabData();
            System.out.println(server);

            server.writeToDatabase();
            System.out.println("wrote to database");
        }
    }
}
