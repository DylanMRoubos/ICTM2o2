package site.nerdygadgets.models;

import site.nerdygadgets.functions.Serialization;

import java.io.IOException;
import java.util.ArrayList;
/**
 * ComponentsModel class
 * Edit compnent model
 *
 * @author Tristan Scholten & Jordy Wielaard
 * @version 1.0
 * @since 14-05-2020
 */
public class ComponentsModel {
    private ArrayList<ComponentModel> componentModels;

    public ComponentsModel(){
        componentModels = new ArrayList<ComponentModel>();
    }

    public ArrayList<ComponentModel> getComponentModels() {
        return componentModels;
    }

    public void setComponentModels(ArrayList<ComponentModel> componentModels) {
        this.componentModels = componentModels;
    }

// Adds component to ArrayList and file
    public boolean addComponentModel(ComponentModel model) {
        try {
            ArrayList<ComponentModel> l = Serialization.deserializeComponents();
            for (ComponentModel m : l) {
                if (m.getName().equals(model.getName()) && m.getType().name().equals(model.getType().name())) {
                    return false;
                }
            }
            componentModels.add(model);
            Serialization.serializeComponents(componentModels);
            return true;
        }catch (IOException e){
            System.out.println("Error");
            return false;
        }
    }
// Reload ArrayList
    public void reloadComponentModel() {
        try {
            this.componentModels = Serialization.deserializeComponents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
// Remove component from ArrayList and file
    public void removeAt(int index) {
        componentModels.remove(index);
        try {
            Serialization.serializeComponents(componentModels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printComponents(){
        for(ComponentModel m: componentModels){
            System.out.println(m);
        }
    }
}
