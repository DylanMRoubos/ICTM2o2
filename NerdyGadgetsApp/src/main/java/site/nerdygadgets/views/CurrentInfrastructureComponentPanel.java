package site.nerdygadgets.views;

import javax.swing.*;
import java.awt.*;

/**
 * CurrentInfrastructureComponentPanel class
 * Displays data in a JPanel based on a Borderlayout
 *
 * @author Mike Thomas & Dylan Roubos
 * @version 1.0
 * @since 01-05-2020
 */
public class CurrentInfrastructureComponentPanel extends JPanel {
    private JLabel name, online, uptime, cpu, memory, disk, uptimeValue, nameValue, onlineValue, cpuValue, memoryValue, diskValue;
    private JPanel southContent;

    public CurrentInfrastructureComponentPanel(String name) {

        setLayout(new BorderLayout());

        this.name = new JLabel("naam: ");
        online = new JLabel("online: ");
        uptime = new JLabel("Uptime: ");
        cpu = new JLabel("CPU: ");
        memory = new JLabel("Geheugen: ");
        disk = new JLabel("Ruimte: ");


        nameValue = new JLabel("");
        onlineValue = new JLabel("");
        uptimeValue = new JLabel("");
        cpuValue = new JLabel("");
        memoryValue = new JLabel("");
        diskValue = new JLabel("");

        southContent = new JPanel();

        southContent.setLayout(new GridLayout(0, 2));

        southContent.add(this.name);
        southContent.add(nameValue);

        southContent.add(online);
        southContent.add(onlineValue);

        southContent.add(uptime);
        southContent.add(uptimeValue);

        southContent.add(cpu);
        southContent.add(cpuValue);

        southContent.add(memory);
        southContent.add(memoryValue);

        southContent.add(disk);
        southContent.add(diskValue);

        add(southContent, BorderLayout.SOUTH);

    }

    public JLabel getUptimeValue() {
        return uptimeValue;
    }

    public JLabel getNameValue() {
        return nameValue;
    }

    public JLabel getOnlineValue() {
        return onlineValue;
    }

    public JLabel getCpuValue() {
        return cpuValue;
    }

    public JLabel getMemoryValue() {
        return memoryValue;
    }

    public JLabel getDiskValue() {
        return diskValue;
    }
}
