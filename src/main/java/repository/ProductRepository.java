package repository;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import domain.Product;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ProductRepository {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> findById(String productId) {
        return products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .collect(Collectors.toList());
    }

    public boolean deleteById(String productId) {
        return products.removeIf(p -> p.getProductId().equals(productId));
    }

    public boolean updateProduct(Product oldProduct, Product newProduct) {
        int index = products.indexOf(oldProduct);
        if (index != -1) {
            products.set(index, newProduct);
            return true;
        }
        return false;
    }

    public List<Product> getAll() {
        return new ArrayList<>(products);
    }

    public List<Product> getBestValueProducts(String category) {
        return products.stream()
                .filter(p -> p.getProductCategory().equalsIgnoreCase(category))
                .sorted(Comparator.comparingDouble(Product::getValuePerUnit))
                .toList();
    }

    public void loadFromFile(String filePath) {
        boolean debug = false;
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {
            String[] fields;
            while ((fields = reader.readNext()) != null) {
                if (debug) {
                    System.out.println("Fields length: " + fields.length);
                    for (int i = 0; i < fields.length; i++) {
                        System.out.printf("fields[%d]: '%s'%n", i, fields[i]);
                    }
                }

                if (fields.length != 8) {
                    System.out.println("Skipping line due to unexpected number of fields: " + fields.length);
                    System.out.println("Line content: " + Arrays.toString(fields));
                    continue;
                }

                String productId = fields[0];
                String productName = fields[1];
                String productCategory = fields[2];
                String brand = fields[3];

                double packageQuantity;
                try {
                    packageQuantity = Double.parseDouble(fields[4]);
                } catch (NumberFormatException e) {
                    System.out.println("Skipping line due to invalid package quantity: " + fields[4]);
                    continue;
                }

                String packageUnit = fields[5];

                double price;
                try {
                    price = Double.parseDouble(fields[6]);
                } catch (NumberFormatException e) {
                    System.out.println("Skipping line due to invalid price: " + fields[6]);
                    continue;
                }

                String currency = fields[7];

                Product product = new Product(productId, productName, productCategory, brand,
                        packageQuantity, packageUnit, price, currency);
                addProduct(product);
            }
        } catch (IOException | NumberFormatException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public List<Product> findByCategory(String category) {
        return products.stream()
                .filter(p -> p.getProductCategory().equalsIgnoreCase(category))
                .toList();
    }
}
