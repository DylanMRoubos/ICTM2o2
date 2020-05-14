package site.nerdygadgets.views;

import org.pushingpixels.substance.api.ComponentState;
import org.pushingpixels.substance.api.SubstanceCortex;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

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
//        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        setBackground(SubstanceCortex.GlobalScope.getCurrentSkin().getColorScheme(this, ComponentState.DEFAULT).getLightColor());

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
        southContent.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
        southContent.setBackground(SubstanceCortex.GlobalScope.getCurrentSkin().getColorScheme(southContent, ComponentState.DEFAULT).getLightColor());

        southContent.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        // name
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        southContent.add(this.name, c);

        // name value
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        southContent.add(nameValue, c);

        // online
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        southContent.add(online, c);

        // online value
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        southContent.add(onlineValue, c);


        // uptime
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        southContent.add(uptime, c);

        // uptime value
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        southContent.add(uptimeValue, c);

        // cpu
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        southContent.add(cpu, c);

        //cpu value
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;
        southContent.add(cpuValue, c);

        // memory
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        southContent.add(memory, c);

        // memory value
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2;
        southContent.add(memoryValue, c);

        // disk
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        southContent.add(disk, c);

        // disk value
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 2;
        southContent.add(diskValue, c);

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
