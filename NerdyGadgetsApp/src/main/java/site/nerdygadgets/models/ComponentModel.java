package site.nerdygadgets.models;

public class ComponentModel {
    private String name;
    private double price;

    public ComponentModel(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ComponentModel{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
