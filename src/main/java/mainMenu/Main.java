package mainMenu;

import repository.*;
import service.*;
import controller.Controller;
import tech.tablesaw.api.Table;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");

        ProductRepository productRepository = new ProductRepository();
        DiscountRepository discountRepository = new DiscountRepository();

        Path resourceDirectory = Path.of("data");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(resourceDirectory, "*.csv")) {
            for (Path file : stream) {
                String filename = file.getFileName().toString();
                System.out.println(filename);

                if (filename.matches("^[a-zA-Z]+_discounts_\\d{4}-\\d{2}-\\d{2}\\.csv$")) {
                    System.out.println("The file for discount matches: " + filename);
                    discountRepository.loadFromFile(file.toString());
                } else if (filename.matches("^[a-zA-Z]+_\\d{4}-\\d{2}-\\d{2}\\.csv$")) {
                    System.out.println("The file for product matches: " + filename);
                    productRepository.loadFromFile(file.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        PriceAlertRepository priceAlertRepository = new PriceAlertRepository(connection);
        //PriceHistoryRepository priceHistoryRepository = new PriceHistoryRepository();
        ShoppingListRepository shoppingListRepository = new ShoppingListRepository(connection,productRepository);
        ProductService productService = new ProductService(productRepository);
        DiscountService discountService = new DiscountService(discountRepository);
        PriceAlertService priceAlertService = new PriceAlertService(priceAlertRepository, productRepository);
       // PriceHistoryService priceHistoryService = new PriceHistoryService(priceHistoryRepository, productRepository);
        ShoppingListService shoppingListService = new ShoppingListService( discountRepository, shoppingListRepository);


        Controller c = new Controller(productService, discountService, priceAlertService, shoppingListService);

        c.mainMenu();
    }

    /*private static void printCsvWithTablesaw(String csvFilePath) {
        try {
            Table table = Table.read().csv(
                    tech.tablesaw.io.csv.CsvReadOptions.builder(csvFilePath)
                            .separator(';')
                            .build()
            );
            System.out.println("=== Data from: " + csvFilePath + " ===");
            System.out.println(table.print());
        } catch (RuntimeException e) {
            System.out.println("Failed to load or parse CSV file: " + csvFilePath);
            e.printStackTrace();
        }*/
}

