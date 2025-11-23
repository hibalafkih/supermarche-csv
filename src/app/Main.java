package app;
import csv.ProductCsvDao;
import csv.CustomerCsvDao;
import csv.OrderCsvDao;
import model.Product;
import model.Customer;
import model.Order;
import model.OrderItem;
import service.InventoryService;
import service.OrderService;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        ProductCsvDao productDao = new ProductCsvDao("data/products.csv");
        CustomerCsvDao customerDao = new CustomerCsvDao("data/customers.csv");
        OrderCsvDao orderDao = new OrderCsvDao("data/orders.csv", "data/order_items.csv");

        List<Product> products = productDao.findAll();
        List<Customer> customers = customerDao.findAll();
        List<Order> orders = orderDao.findAll();

        InventoryService inventory = new InventoryService(products);
        OrderService orderService = new OrderService(inventory);

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Supermarché ===");
            System.out.println("1. Lister produits");
            System.out.println("2. Ajouter produit");
            System.out.println("3. Modifier stock");
            System.out.println("4. Alertes stock");
            System.out.println("5. Lister clients");
            System.out.println("6. Ajouter client");
            System.out.println("7. Créer commande");
            System.out.println("8. Sauvegarder et quitter");
            System.out.print("Choix: ");
            String choice = sc.nextLine();

            try {
                switch (choice) {
                    case "1" -> inventory.all().forEach(System.out::println);
                    case "2" -> {
                        System.out.print("ID: "); String id = sc.nextLine();
                        System.out.print("Nom: "); String name = sc.nextLine();
                        System.out.print("Catégorie: "); String cat = sc.nextLine();
                        System.out.print("Prix: "); double price = Double.parseDouble(sc.nextLine());
                        System.out.print("Stock: "); int stock = Integer.parseInt(sc.nextLine());
                        System.out.print("Stock min: "); int minStock = Integer.parseInt(sc.nextLine());
                        inventory.addProduct(new Product(id, name, cat, price, stock, minStock));
                        System.out.println("Produit ajouté.");
                    }
                    case "3" -> {
                        System.out.print("ID produit: "); String id = sc.nextLine();
                        System.out.print("Delta (+/-): "); int delta = Integer.parseInt(sc.nextLine());
                        inventory.updateStock(id, delta);
                        System.out.println("Stock mis à jour.");
                    }
                    case "4" -> {
                        var alerts = inventory.lowStockAlerts();
                        if (alerts.isEmpty()) System.out.println("Aucune alerte.");
                        else alerts.forEach(p -> System.out.println("ALERTE: " + p));
                    }
                    case "5" -> customers.forEach(System.out::println);
                    case "6" -> {
                        System.out.print("ID: "); String id = sc.nextLine();
                        System.out.print("Nom: "); String name = sc.nextLine();
                        System.out.print("Email: "); String email = sc.nextLine();
                        System.out.print("Téléphone: "); String phone = sc.nextLine();
                        customers.add(new Customer(id, name, email, phone));
                        System.out.println("Client ajouté.");
                    }
                    case "7" -> {
                        System.out.print("ID commande: "); String orderId = sc.nextLine();
                        System.out.print("ID client: "); String customerId = sc.nextLine();
                        Order order = new Order(orderId, customerId, "2025-11-23");

                        System.out.print("ID produit: "); String pid = sc.nextLine();
                        System.out.print("Quantité: "); int qty = Integer.parseInt(sc.nextLine());
                        Product p = inventory.findById(pid).orElseThrow();
                        order.addItem(new OrderItem(orderId, pid, qty, p.getPrice()));

                        orderService.applyOrder(order);
                        orders.add(order);
                        System.out.println("Commande créée: " + order);
                    }
                    case "8" -> {
                        productDao.saveAll(inventory.all());
                        customerDao.saveAll(customers);
                        orderDao.saveAll(orders);
                        running = false;
                        System.out.println("Toutes les données ont été sauvegardées. Bye!");
                    }
                    default -> System.out.println("Choix invalide.");
                }
            } catch (Exception e) {
                System.out.println("Erreur: " + e.getMessage());
            }
        }
    }
}
