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

    public ServerManager() {
        // instantiate database connection with a connectionstring to test database.
        Database database = new Database("mongodb+srv://admin:admin@cluster0-gzerr.mongodb.net/test?retryWrites=true&w=majority");

        // TODO instantiate server inside arraylist
        // instantiate servers
        Server web1 = new Server(database,1, ServerType.WEB, "172.16.0.190", "student", "KHxd4gu7");
        Server web2 = new Server(database,2, ServerType.WEB, "172.16.0.191", "student", "KHxd4gu7");
        Server database1 = new Server(database,3, ServerType.DATABASE, "172.16.0.158", "student", "wd9AdEuN");
        Server database2 = new Server(database,4, ServerType.DATABASE, "172.16.0.159", "student", "wd9AdEuN");
        Server pfsense = new Server(database, 5, ServerType.PFSENSE, "172.16.0.1", "console", "pr7cmHKNX6VhPaHc");

        // instantiate arraylist and add servers to list
        servers = new ArrayList<>();
        servers.add(web1);
        servers.add(web2);
        servers.add(database1);
        servers.add(database2);
        servers.add(pfsense);


        // instantiate timer and add self to timer.
        Timer timer = new Timer();
        timer.schedule(this, 0,30000); // run on start and then every 30 seconds.
    }

    public void run() {
        // TODO cleanup loop
        // loop through servers and grab metrics with SSH.
        for (Server server : servers) {
            server.grabData();
            System.out.println(server);
        }

        // loop through servers and write data to MongoDB.
        for (Server server : servers) {
            server.writeToDatabase();
            System.out.println("wrote to database");
        }
    }
}
