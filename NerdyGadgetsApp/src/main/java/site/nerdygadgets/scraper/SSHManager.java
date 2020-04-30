package site.nerdygadgets.scraper;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;

//Simple SSH POC class to demonstrate usage of JSch
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

        startSession();
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
            e.printStackTrace();
        }
    }

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
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }
            }
            channel.disconnect();
            return result;

        } catch (Exception e) {
            System.out.println("Something went wrong with creating a channel or running the command");
            return null;
        }
    }

    public void endSession(Session session) {
        session.disconnect();
    }
}