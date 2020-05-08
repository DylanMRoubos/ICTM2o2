package site.nerdygadgets.models;

import site.nerdygadgets.functions.ComponentType;
import site.nerdygadgets.functions.Serialization;

import java.io.IOException;
import java.util.ArrayList;

public class DesignModel {
    private ArrayList<ComponentModel> list;

    public DesignModel(){
        reloadList();
    }

    public void reloadList(){
        try {
            list = Serialization.deserializeComponents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ComponentModel> getDatabaseModels(){
        ArrayList<ComponentModel> database = new ArrayList<ComponentModel>();
        for(ComponentModel l : list){
            if(l.getType() == ComponentType.Database){
                database.add(l);
            }
        }
        return database;
    }

    public ArrayList<ComponentModel> getWebModels(){
        ArrayList<ComponentModel> web = new ArrayList<ComponentModel>();
        for(ComponentModel l : list){
            if(l.getType() == ComponentType.Webserver){
                web.add(l);
            }
        }
        return web;
    }

    public ArrayList<ComponentModel> getPfsenseModels(){
        ArrayList<ComponentModel> pfsense = new ArrayList<ComponentModel>();
        for(ComponentModel l : list){
            if(l.getType() == ComponentType.Firewall){
                pfsense.add(l);
            }
        }
        return pfsense;
    }
}
