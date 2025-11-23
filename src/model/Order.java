package model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String id;
    private String customerId;
    private String date;
    private double total;
    private List<OrderItem> items = new ArrayList<>();

    public Order(String id, String customerId, String date) {
        this.id = id;
        this.customerId = customerId;
        this.date = date;
    }

    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public String getDate() { return date; }
    public double getTotal() { return total; }
    public List<OrderItem> getItems() { return items; }

    public void setTotal(double total) { this.total = total; }
    public void addItem(OrderItem item) { items.add(item); }

    @Override
    public String toString() {
        return String.format("Commande %s client %s - total: %.2f", id, customerId, total);
    }
}
