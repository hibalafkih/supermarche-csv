package model;

public class OrderItem {
    private String orderId;
    private String productId;
    private int quantity;
    private double unitPrice;

    public OrderItem(String orderId, String productId, int quantity, double unitPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getOrderId() { return orderId; }
    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }

    @Override
    public String toString() {
        return String.format("Produit %s x%d (%.2f)", productId, quantity, unitPrice);
    }
}
