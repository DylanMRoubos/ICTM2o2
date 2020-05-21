package site.nerdygadgets;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * SSHManager class
 * connects to a server with SSH.
 * provides simple method that returns a string output from a command.
 *
 * @author Mike Thomas & Dylan Roubos
 * @version 1.0
 * @since 30-04-2020
 */

public class SSHManager {
    private String user;
    private String password;
    private String host;
    private int port = 22;
    private Session session;

    public SSHManager(String user, String password, String host) {
        this.user = user;
        this.password = password;
        this.host = host;
    }

    public void startSession() {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            this.session = session;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    //Run a command on the server
    public String runCommand(String command) {
        try {
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            InputStream outputCommand = channel.getInputStream();
            channel.connect();

            //Put results in a String
            byte[] tmp = new byte[1024];
            String result = "";
            while (true) {
                while (outputCommand.available() > 0) {
                    int i = outputCommand.read(tmp, 0, 1024);
                    if (i < 0) break;
                    result += (new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    break;
                }
            }
            channel.disconnect();
            return result;

        } catch (Exception e) {
            System.err.println("Something went wrong with creating a channel or running the command, command: " + command);
            return null;
        }
    }

    //Run a command on the server in Sudo mode
    public String runCommandSudo(String command) {
        try {
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand("sudo -S -p '' " + command);
            InputStream outputCommand =channel.getInputStream();
            OutputStream out=channel.getOutputStream();
            ((ChannelExec)channel).setErrStream(System.err);
            channel.connect();

            out.write((password+"\n").getBytes());
            out.flush();

            //Put results in a String
            byte[] tmp = new byte[1024];
            String result = "";
            while (true) {
                while (outputCommand.available() > 0) {
                    int i = outputCommand.read(tmp, 0, 1024);
                    if (i < 0) break;
                    result += (new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    break;
                }
            }
            channel.disconnect();
            return result;

        } catch (Exception e) {
            System.err.println("Something went wrong with creating a channel or running the command, command: " + command);
            return null;
        }
    }

    public void endSession(Session session) {
        session.disconnect();
    }

    public boolean isConnected() {
        if (session == null) {
            return false;
        }
        return session.isConnected();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SSHManager that = (SSHManager) o;
        return Objects.equals(host, that.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host);
    }
}