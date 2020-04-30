package site.nerdygadgets.scraper;

public class Server {
    private int id;
    private ServerType type;
    private String ip;
    private String user;
    private String password;
    private SSHManager sshManager;

    public Server(int id, ServerType type, String ip, String user, String password) {
        this.id = id;
        this.type = type;
        this.ip = ip;
        this.user = user;
        this.password = password;
        sshManager = new SSHManager(this.user, this.password, this.ip);
    }

    public String getCPU() {
        if (type == ServerType.UBUNTU) {
            return sshManager.runCommand("");
        } else if (type == ServerType.FREEBSD) {
            return sshManager.runCommand("");
        }
        return null;
    }
}
