package site.nerdygadgets.models;

import org.bson.Document;
import site.nerdygadgets.scraper.Database;
import site.nerdygadgets.views.CurrentInfrastructureComponentPanel;


/**
 * CurrentInfrastructureComponentModel class
 * Keeps data from a component
 * Has a function to get metrics from MongoDB
 *
 * @author Mike Thomas & Dylan Roubos
 * @version 1.0
 * @since 01-05-2020
 */
public class CurrentInfrastructureComponentModel {
    private String name;
    private int serverId;
    private String ip;
    private boolean online;
    private String cpu;
    private String memory;
    private String disk;
    private String uptime;
    private Database db;

    public CurrentInfrastructureComponentModel(int serverId, Database db, String name) {
        this.name = name;
        this.serverId = serverId;
        this.db = db;
    }

    //Get the data from mongodb and save it to the object
    public void getData() {
        Document document = db.getDocumentByServerId(serverId);

        ip  = document.getString("ip");
        online = (boolean) ((Document)document.get("status")).get("online");
        cpu = (String) ((Document)document.get("status")).get("cpu");
        memory = (String) ((Document)document.get("status")).get("memory");
        disk = (String) ((Document)document.get("status")).get("disk");
        uptime = (String) ((Document)document.get("status")).get("uptime");
    }

    public String getName() {
        return name;
    }

    public int getServerId() {
        return serverId;
    }

    public String getIp() {
        return ip;
    }

    public boolean getOnline() {
        return online;
    }

    public String getCpu() {
        return cpu;
    }

    public String getMemory() {
        return memory;
    }

    public String getDisk() {
        return disk;
    }

    public String getUptime() {
        return uptime;
    }
}
