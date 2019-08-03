package Entities;

public class Item {
    private String name;
    private int quantity;
    private String unit;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item(String name, int quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }


    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }
}
