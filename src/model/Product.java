package model;

public class Product {
    private String id;
    private String name;
    private String category;
    private double price;
    private int stock;
    private int minStock;

    public Product(String id, String name, String category, double price, int stock, int minStock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.minStock = minStock;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public int getMinStock() { return minStock; }

    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - %.2f | stock: %d", id, name, category, price, stock);
    }
}
