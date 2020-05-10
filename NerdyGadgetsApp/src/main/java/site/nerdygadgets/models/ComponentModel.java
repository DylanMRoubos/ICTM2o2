package site.nerdygadgets.models;

import site.nerdygadgets.functions.ComponentType;
import site.nerdygadgets.functions.Serialization;

import java.io.IOException;
import java.util.ArrayList;

public class ComponentModel {
    private String name;
    private double availability;
    private double price;
    private ComponentType type;

    public ComponentModel(String name, double availability, double price, ComponentType type) {
        this.name = name;
        this.availability = availability;
        this.price = price;
        this.type = type;
    }

    public ComponentType getType() {
        return this.type;
    }

    public double getPrice() {
        return this.price;
    }

    public double getAvailability() {
        return this.availability;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "ComponentModel{" +
                "name='" + name + '\'' +
                ", availability=" + availability +
                ", price=" + price +
                ", type=" + type +
                '}';
    }

    public static ComponentModel getModel(String name, ComponentType type) {
        try {
            ArrayList<ComponentModel> l = Serialization.deserializeComponents();
            for (ComponentModel m : l) {
                if (m.getType() == type && m.getName() == name) {
                    return m;
                }
            }
        }
        catch (IOException e) { }
        return null;
    }
}