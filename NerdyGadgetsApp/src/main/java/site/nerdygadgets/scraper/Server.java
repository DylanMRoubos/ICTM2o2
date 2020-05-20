package site.nerdygadgets.scraper;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Server class
 * Contains all the information of single server.
 * Has the functionality to grab data from ssh and send data to the database class.
 *
 * @author Mike Thomas & Dylan Roubos
 * @version 1.0
 * @since 30-04-2020
 */
public class Server {
    private int id;
    private ServerType type;
    private String ip;
    private String user;
    private String password;
    private SSHManager sshManager;
    private Database database;
    private boolean online;
    private String cpu;
    private String memory;
    private String disk;
    private String uptime;

    public Server(Database database, int id, ServerType type, String ip, String user, String password) {
        this.database = database;
        this.id = id;
        this.type = type;
        this.ip = ip;
        this.user = user;
        this.password = password;
        sshManager = new SSHManager(this.user, this.password, this.ip);
    }

    public void grabData() {
        // check if the ssh client is still connected. if not, try once to connect. if it can't reconnect the server will be marked offline.
        if (!sshManager.isConnected()) {
            if (pingServer()) {
                sshManager.startSession();
            }
        }
        if (!sshManager.isConnected()) {
            // server offline
            online = false;
            cpu = "-";
            memory = "-";
            disk = "-";
            uptime = "-";
        } else {
            // server online
            online = true;
            if (type == ServerType.WEB || type == ServerType.DATABASE) {
                cpu = sshManager.runCommand("top -bn2 | grep \"Cpu(s)\" | tail -n1 | sed \"s/.*, *\\([0-9.]*\\)%* id.*/\\1/\" | awk '{print 100 - $1 \"%\"}'");
                memory = sshManager.runCommand("free --mega | grep 'Mem:' | awk '{print $3 \"M/\" $2 \"M\"}'");
                disk = sshManager.runCommand("df -h --total | grep 'total' | awk '{print $3 \"/\" $2}'");
                uptime = sshManager.runCommand("uptime -p");
            } else if (type == ServerType.PFSENSE) {
                cpu = sshManager.runCommand("top -nd2 | grep \"CPU:\" | tail -n1 | sed \"s/.*, *\\([0-9.]*\\)%* id.*/\\1/\" | awk '{print 100 - $1 \"%\"}'");
                memory = sshManager.runCommand("sh freebsd-memory.sh | egrep 'mem_total|mem_used' | paste -d \" \" - - | awk '{print int($2/1024/1024) \"M/\" int($12/1024/1024) \"M\"}'");
                disk = sshManager.runCommand("df -h / | grep / | awk '{print $3 \"/\" $2}'");
                uptime = sshManager.runCommand("uptime | awk '{print $3 \" \" $4 \" \" $5}'");
            }
        }

    }

    private boolean pingServer() {

        try {
            try (Socket soc = new Socket()) {
                soc.connect(new InetSocketAddress(ip, 22), 2000);
            }
            System.out.println("Host is reachable");
            return true;
        } catch (IOException ex) {
//            System.out.println("Sorry ! We can't reach to this host");
            return false;
        }
    }

    public void writeToDatabase() {
        // write current metrics to MongoDB.
        database.createDocument(id, type, ip, online, cpu, memory, disk, uptime);
    }

    @Override
    public String toString() {
        return "Server{" +
                "online=" + online +
                ", cpu='" + cpu + '\'' +
                ", memory='" + memory + '\'' +
                ", disk='" + disk + '\'' +
                ", uptime='" + uptime + '\'' +
                '}';
    }
}
