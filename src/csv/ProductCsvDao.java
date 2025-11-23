package csv; 

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import model.Product;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ProductCsvDao {
    private final String path;

    public ProductCsvDao(String path) {
        this.path = path;
    }

    public List<Product> findAll() throws Exception {
        List<Product> products = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] row;
            boolean header = true;
            while ((row = reader.readNext()) != null) {
                if (header) { header = false; continue; }
                Product p = new Product(
                    row[0], row[1], row[2],
                    Double.parseDouble(row[3]),
                    Integer.parseInt(row[4]),
                    Integer.parseInt(row[5])
                );
                products.add(p);
            }
        }
        return products;
    }

    public void saveAll(List<Product> products) throws Exception {
        try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {
            writer.writeNext(new String[]{"id","name","category","price","stock","minStock"});
            for (Product p : products) {
                writer.writeNext(new String[]{
                    p.getId(), p.getName(), p.getCategory(),
                    String.valueOf(p.getPrice()),
                    String.valueOf(p.getStock()),
                    String.valueOf(p.getMinStock())
                });
            }
        }
    }
}
