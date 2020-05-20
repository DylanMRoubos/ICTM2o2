package site.nerdygadgets.models;

import site.nerdygadgets.functions.ComponentType;
import site.nerdygadgets.functions.Serialization;

import java.io.IOException;
import java.util.ArrayList;
/**
 * DesignModel class
 * Get model for creating designs
 *
 * @author Tristan Scholten & Jordy Wielaard
 * @version 1.0
 * @since 14-05-2020
 */
public class DesignModel {
    private ArrayList<ComponentModel> list;

    public DesignModel(){
        reloadList();
    }
// Updates list with file
    public void reloadList(){
        try {
            list = Serialization.deserializeComponents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ComponentModel> getDatabaseModels(){
        if (list == null)
            reloadList();
        ArrayList<ComponentModel> database = new ArrayList<ComponentModel>();
        for(ComponentModel l : list){
            if(l.getType() == ComponentType.Database){
                database.add(l);
            }
        }
        return database;
    }

    public ArrayList<ComponentModel> getWebModels(){
        if (list == null)
            reloadList();
        ArrayList<ComponentModel> web = new ArrayList<ComponentModel>();
        for(ComponentModel l : list){
            if(l.getType() == ComponentType.Webserver){
                web.add(l);
            }
        }
        return web;
    }

    public ArrayList<ComponentModel> getFirewallModels(){
        if (list == null)
            reloadList();
        ArrayList<ComponentModel> firewall = new ArrayList<ComponentModel>();
        for(ComponentModel l : list){
            if(l.getType() == ComponentType.Firewall){
                firewall.add(l);
            }
        }
        return firewall;
    }

    public ArrayList<ComponentModel> getList() {
        return list;
    }
}
