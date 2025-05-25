package repository;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import domain.Discount;
import domain.Product;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class DiscountRepository {
    private List<Discount> discounts = new ArrayList<>();

    public void addDiscount(Discount discount) {
        discounts.add(discount);
    }

    public Optional<Discount> findByProductId(String productId) {
        return discounts.stream()
                .filter(d -> d.getProduct().getProductId().equals(productId))
                .findFirst();
    }

    public boolean deleteByProductId(String productId) {
        return discounts.removeIf(d -> d.getProduct().getProductId().equals(productId));
    }

    public boolean updateDiscount(Discount discount) {
        for (int i = 0; i < discounts.size(); i++) {
            if (discounts.get(i).getProduct().getProductId().equals(discount.getProduct().getProductId())) {
                discounts.set(i, discount);
                return true;
            }
        }
        return false;
    }

    public List<Discount> getAll() {
        return new ArrayList<>(discounts);
    }

    public void loadFromFile(String filePath) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {
            String[] fields;


            while ((fields = reader.readNext()) != null) {
                if (fields.length != 9) {
                    System.out.println("Skipping incomplete line in " + filePath);

                    continue;
                }

                try {
                    String productId = fields[0];
                    String productName = fields[1];
                    String brand = fields[2];
                    double packageQuantity = Double.parseDouble(fields[3]);
                    String packageUnit = fields[4];
                    String productCategory = fields[5];


                    LocalDate fromDate = LocalDate.parse(fields[6], formatter);
                    LocalDate toDate = LocalDate.parse(fields[7], formatter);
                    double percentageOfDiscount = Double.parseDouble(fields[8]);


                    Product product = new Product(productId, productName, brand, packageQuantity, packageUnit, productCategory);

                    Discount discount = new Discount(product, fromDate, toDate, percentageOfDiscount);

                    addDiscount(discount);

                } catch (NumberFormatException | DateTimeParseException e) {
                    System.out.println("Invalid data in line, skipping.");
                }
            }


        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
