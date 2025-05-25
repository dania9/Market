package controller;

import domain.*;
//import repository.PriceHistoryRepository;
import service.ProductService;
import service.DiscountService;
import service.PriceAlertService;
//import service.PriceHistoryService;
import service.ShoppingListService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Controller {
    private final ProductService productService;
    private final DiscountService discountService;
    private final PriceAlertService priceAlertService;
    //private final PriceHistoryService priceHistoryService;
    private final ShoppingListService shoppingListService;

    private final Scanner scanner = new Scanner(System.in);

    public Controller(ProductService productService, DiscountService discountService, PriceAlertService priceAlertService, ShoppingListService shoppingListService) {
        this.productService = productService;
        this.discountService = discountService;
        this.priceAlertService = priceAlertService;
        this.shoppingListService = shoppingListService;
    }

    public void mainMenu() {
        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("0 : EXIT");
            System.out.println("1 : Product Management");
            System.out.println("2 : Discount Management");
            System.out.println("3 : Price Alerts Management");
            System.out.println("4 : Shopping List");
            System.out.println("5 : Show Product Substitutes");
            //System.out.println("6 : Show Price History");

            System.out.print("Choose your option: ");
            int command = scanner.nextInt();
            scanner.nextLine();

            switch (command) {
                case 0: return;
                case 1: productMenu(); break;
                case 2: discountMenu(); break;
                case 3: priceAlertMenu(); break;
                case 4: dailyShoppingBasket(); break;
                //case 7: showPriceHistory(); break;
                case 5: showProductSubstitutes(); break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


    private void productMenu() {
        while (true) {
            System.out.println("\n--- Product Menu ---");
            System.out.println("0 : Go back to the Main Menu");
            System.out.println("1 : Add Product");
            System.out.println("2 : Update Product");
            System.out.println("3 : Show All Products");
            System.out.println("4 : Find Product by ID");
            System.out.println("5 : Delete Product");

            System.out.print("Choose your option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 0: return;
                case 1: addProduct(); break;
                case 2: updateProduct(); break;
                case 3: showAllProducts(); break;
                case 4: findProductById(); break;
                case 5: deleteProduct(); break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addProduct() {
        System.out.print("Product ID: ");
        String id = scanner.nextLine();

        System.out.print("Product Name: ");
        String name = scanner.nextLine();

        System.out.print("Product Category: ");
        String category = scanner.nextLine();

        System.out.print("Brand: ");
        String brand = scanner.nextLine();

        System.out.print("Package Quantity: ");
        double quantity = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Package Unit: ");
        String unit = scanner.nextLine();

        System.out.print("Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Currency: ");
        String currency = scanner.nextLine();

        Product product = new Product(id, name, category, brand, quantity, unit, price, currency);
        productService.addProduct(product);
        System.out.println("Product added.");
    }

    private void updateProduct() {
        System.out.print("Enter product ID to update: ");
        String id = scanner.nextLine();

        var products = productService.getProductsById(id);
        if (products.isEmpty()) {
            System.out.println("No products found with that ID.");
            return;
        }


        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i));
        }

        System.out.print("Choose which product to update (1-" + products.size() + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine();


        if (choice < 1 || choice > products.size()) {
            System.out.println("Invalid choice.");
            return;
        }


        Product selectedProduct = products.get(choice - 1);

        System.out.println("Updating product: " + selectedProduct);

        System.out.print("New Product Name: ");
        String name = scanner.nextLine();

        System.out.print("New Product Category: ");
        String category = scanner.nextLine();

        System.out.print("New Brand: ");
        String brand = scanner.nextLine();

        System.out.print("New Package Quantity: ");
        double quantity = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("New Package Unit: ");
        String unit = scanner.nextLine();

        System.out.print("New Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("New Currency: ");
        String currency = scanner.nextLine();


        Product updatedProduct = new Product(selectedProduct.getProductId(), name, category, brand, quantity, unit, price, currency);


        productService.updateProduct(selectedProduct, updatedProduct);

        System.out.println("Product updated.");
    }

    private void showAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            for (Product product : products) {
                System.out.println(product);
                System.out.println();
            }
        }
    }

    private void deleteProduct() {
        System.out.print("Enter product ID to delete: ");
        String id = scanner.nextLine();

        var products = productService.getProductsById(id);
        if (products.isEmpty()) {
            System.out.println("No products found with that ID.");
            return;
        }

        // If multiple products with same ID, show them and let user choose
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i));
        }

        System.out.print("Choose which product to delete (1-" + products.size() + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > products.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Product selectedProduct = products.get(choice - 1);
        boolean deleted = productService.deleteProductById(selectedProduct.getProductId());

        if (deleted) {
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Failed to delete the product.");
        }
    }


    private void findProductById() {
        System.out.print("Enter product ID: ");
        String id = scanner.nextLine();
        var products = productService.getProductsById(id);

        if (products.isEmpty()) {
            System.out.println("Product not found.");
        } else {
            for (Product p : products) {
                System.out.println(p);
                System.out.println("---");
            }
        }
    }


    private void discountMenu() {
        while (true) {
            System.out.println("\n--- Discount Menu ---");
            System.out.println("0 : Go back to the Main Menu");
            System.out.println("1 : Add Discount");
            System.out.println("2 : Show All Discounts");
            System.out.println("3 : List The Best Discounts");
            System.out.println("4 : List New Discounts");



            System.out.print("Choose your option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 0: return;
                case 1: addDiscount(); break;
                case 2: showAllDiscounts(); break;
                case 3: listBestDiscounts(); break;
                case 4: listNewDiscounts(); break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addDiscount() {
        System.out.print("Enter Product ID for discount: ");
        String productId = scanner.nextLine();

        var products = productService.getProductsById(productId);
        if (products.isEmpty()) {
            System.out.println("Product not found.");
            return;
        }

        Product product;
        if (products.size() > 1) {
            System.out.println("Multiple products found with ID " + productId + ":");
            for (int i = 0; i < products.size(); i++) {
                System.out.println((i + 1) + ". " + products.get(i));
            }

            System.out.print("Choose which product to apply discount (1-" + products.size() + "): ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            if (choice < 1 || choice > products.size()) {
                System.out.println("Invalid choice.");
                return;
            }

            product = products.get(choice - 1);
        } else {
            product = products.get(0);
        }

        System.out.print("From Date (yyyy-mm-dd): ");
        String from = scanner.nextLine();

        System.out.print("To Date (yyyy-mm-dd): ");
        String to = scanner.nextLine();

        System.out.print("Discount Percentage: ");
        double percentage = scanner.nextDouble();
        scanner.nextLine();

        Discount discount = new Discount(product, LocalDate.parse(from), LocalDate.parse(to), percentage);
        discountService.addDiscount(discount);
        System.out.println("Discount added.");
    }

    private void showAllDiscounts() {
        List<Discount> discounts = discountService.getAllDiscounts();
        if (discounts.isEmpty()) {
            System.out.println("No discounts available.");
        } else {
            for (Discount discount : discounts) {
                System.out.println(discount);
                System.out.println();
            }
        }
    }

    private void listBestDiscounts() {
        List<Discount> bestDiscounts = discountService.getBestDiscounts();
        if (bestDiscounts.isEmpty()) {
            System.out.println("\nNo current discounts available.");
            return;
        }

        System.out.println("\nProducts with Highest Current Discounts:");
        for (Discount d : bestDiscounts) {
            Product p = d.getProduct();
            System.out.printf("- %s | Brand: %s | Category: %s | Package: %.2f %s | %.2f%% off \n",
                    p.getProductName(),
                    p.getBrand(),
                    p.getProductCategory(),
                    p.getPackageQuantity(),
                    p.getPackageUnit(),
                    d.getPercentageOfDiscount());
        }
    }

    private void listNewDiscounts() {
        LocalDate yesterday = LocalDate.now().minus(1, ChronoUnit.DAYS);
        List<Discount> newDiscounts = discountService.getNewDiscounts(yesterday);
        System.out.println("\nDiscounts Added Within Last 24 Hours:");
        for (Discount d : newDiscounts) {
            System.out.printf("- %s: %.2f%% off (Added on %s)\n",
                    d.getProduct().getProductName(),
                    d.getPercentageOfDiscount(),
                    d.getFromDate());
        }
    }



    private void priceAlertMenu() {
        while (true) {
            System.out.println("\n--- Price Alert Menu ---");
            System.out.println("0 : Go back to the Main Menu");
            System.out.println("1 : Add Price Alert");
            System.out.println("2 : Check Triggered Alerts");

            System.out.print("Choose your option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 0: return;
                case 1: addPriceAlert(); break;
                case 2: checkTriggeredAlerts(); break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addPriceAlert() {
        System.out.print("Enter Product ID for the price alert: ");
        String productId = scanner.nextLine();

        var productOpt = productService.getProductsById(productId);
        if (productOpt.isEmpty()) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Target Price: ");
        double targetPrice = scanner.nextDouble();
        scanner.nextLine();

        priceAlertService.addAlert(productId, targetPrice);
        System.out.println("Price alert added.");
    }

    private void checkTriggeredAlerts() {
        List<PriceAlert> triggeredAlerts = priceAlertService.checkTriggeredAlerts();

        if (triggeredAlerts.isEmpty()) {
            System.out.println("No alerts have been triggered.");
            return;
        }

        System.out.println("Triggered Alerts:");
        for (PriceAlert alert : triggeredAlerts) {
            System.out.println("- Product ID: " + alert.getProductId() + " | Target Price: " + alert.getTargetPrice());
        }
    }



    private void dailyShoppingBasket() {
        System.out.println("Manage your shopping basket (type 'add', 'remove', 'view' or 'done'):");

        List<ShoppingList> basket = new ArrayList<>();

        while (true) {
            System.out.print("Choose action (add/remove/view/done): ");
            String action = scanner.nextLine().trim().toLowerCase();

            if ("done".equals(action)) {
                break;
            }

            switch (action) {
                case "add":
                    System.out.print("Product ID to add: ");
                    String addProductId = scanner.nextLine().trim();
                    var productsToAdd = productService.getProductsById(addProductId);

                    if (productsToAdd.isEmpty()) {
                        System.out.println("Product not found, try again.");
                        continue;
                    }

                    Product selectedProductToAdd;
                    if (productsToAdd.size() == 1) {
                        selectedProductToAdd = productsToAdd.get(0);
                    } else {
                        System.out.println("Multiple products found with this ID:");
                        for (int i = 0; i < productsToAdd.size(); i++) {
                            System.out.printf("%d. %s\n", i + 1, productsToAdd.get(i));
                        }
                        System.out.print("Choose product number: ");
                        int choice;
                        try {
                            choice = Integer.parseInt(scanner.nextLine());
                            if (choice < 1 || choice > productsToAdd.size()) {
                                System.out.println("Invalid choice, defaulting to 1");
                                choice = 1;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input, defaulting to 1");
                            choice = 1;
                        }
                        selectedProductToAdd = productsToAdd.get(choice - 1);
                    }

                    System.out.print("Quantity to add: ");
                    int qtyToAdd;
                    try {
                        qtyToAdd = Integer.parseInt(scanner.nextLine());
                        if (qtyToAdd <= 0) {
                            System.out.println("Quantity must be positive.");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid quantity.");
                        continue;
                    }

                    ShoppingList itemToAdd = new ShoppingList(selectedProductToAdd, qtyToAdd);
                    basket.add(itemToAdd);
                    shoppingListService.addItem(itemToAdd);
                    // Refresh basket from DB
                    basket = new ArrayList<>(shoppingListService.getAllItems());
                    System.out.println("Item added to basket.");
                    break;

                case "remove":
                    if (basket.isEmpty()) {
                        System.out.println("Basket is empty, nothing to remove.");
                        continue;
                    }

                    System.out.println("Current basket items:");
                    for (int i = 0; i < basket.size(); i++) {
                        ShoppingList item = basket.get(i);
                        System.out.printf("%d. %s x%d\n", i + 1, item.getProduct().getProductName(), item.getQuantity());
                    }

                    System.out.print("Choose item number to remove: ");
                    int removeIndex;
                    try {
                        removeIndex = Integer.parseInt(scanner.nextLine());
                        if (removeIndex < 1 || removeIndex > basket.size()) {
                            System.out.println("Invalid item number.");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input.");
                        continue;
                    }

                    ShoppingList itemToRemove = basket.get(removeIndex - 1);
                    basket.remove(removeIndex - 1);
                    shoppingListService.removeItem(itemToRemove);
                    // Refresh basket from DB
                    basket = new ArrayList<>(shoppingListService.getAllItems());
                    System.out.println("Item removed from basket.");
                    break;
                case "view":
                    List<ShoppingList> allItems = shoppingListService.getAllItems();
                    if (allItems.isEmpty()) {
                        System.out.println("Your shopping basket is empty.");
                    } else {
                        System.out.println("\nCurrent Shopping Basket Items:");
                        for (ShoppingList item : allItems) {
                            Product p = item.getProduct();
                            System.out.printf("- %s | Category: %s | Brand: %s | Quantity: %d\n",
                                    p.getProductName(), p.getProductCategory(), p.getBrand(), item.getQuantity());
                        }
                    }
                    break;

                default:
                    System.out.println("Unknown action, please type 'add', 'remove' , 'view' or 'done'.");
            }
        }

        if (basket.isEmpty()) {
            System.out.println("No products in basket.");
            return;
        }


        System.out.println("\nShopping Basket:");
        basket.forEach(item -> {
            Product p = item.getProduct();
            System.out.printf("- %s | Category: %s | Brand: %s | Quantity: %d\n",
                    p.getProductName(), p.getProductCategory(), p.getBrand(), item.getQuantity());
        });
    }





    /*
    private void showPriceHistory() {
        System.out.print("Enter Product ID: ");
        String productId = scanner.nextLine();


        List<PriceHistory> priceHistoryList = priceHistoryService.getPriceHistoryForProductsById(productId);


        System.out.print("Filter by Category (or leave blank): ");
        String category = scanner.nextLine();

        System.out.print("Filter by Brand (or leave blank): ");
        String brand = scanner.nextLine();



        if (!category.isBlank()) {
            // Get all price histories for the category, then filter by productId
            List<PriceHistory> byCategory = priceHistoryService.getByCategory(category);
            priceHistoryList = byCategory.stream()
                    .filter(ph -> ph.getProductId().equals(productId))
                    .sorted(Comparator.comparing(PriceHistory::getDate))
                    .toList();

        } else if (!brand.isBlank()) {
            // Get all price histories for the brand, then filter by productId
            List<PriceHistory> byBrand = priceHistoryService.getByBrand(brand);
            priceHistoryList = byBrand.stream()
                    .filter(ph -> ph.getProductId().equals(productId))
                    .sorted(Comparator.comparing(PriceHistory::getDate))
                    .toList();

        } else {
            // Directly get all price histories for the productId
            priceHistoryList = priceHistoryService.getByProductId(productId).stream()
                    .sorted(Comparator.comparing(PriceHistory::getDate))
                    .toList();
        }

        System.out.println("\nPrice History Data Points:");
        if (priceHistoryList.isEmpty()) {
            System.out.println("No price history found.");
            return;
        }

        for (PriceHistory ph : priceHistoryList) {
            System.out.printf("%s : %.2f\n", ph.getDate(), ph.getPrice());
        }
    }

     */

    private void showProductSubstitutes() {
        System.out.print("Enter product category to find substitutes: ");
        String category = scanner.nextLine();

        List<Product> products = productService.getProductsByCategory(category);
        if (products.isEmpty()) {
            System.out.println("No products found in this category.");
            return;
        }

        System.out.println("\nProducts sorted by value per unit (price per unit):");
        products.stream()
                .sorted(Comparator.comparingDouble(this::calculateValuePerUnit))
                .forEach(p -> {
                    double valuePerUnit = calculateValuePerUnit(p);
                    System.out.printf("- %s | Brand: %s | Category: %s | Package: %.2f %s | Price: %.2f %s | Price per %s: %.2f %s\n",
                            p.getProductName(),
                            p.getBrand(),
                            p.getProductCategory(),
                            p.getPackageQuantity(),
                            p.getPackageUnit(),
                            p.getPrice(),
                            p.getCurrency(),
                            p.getPackageUnit(),
                            valuePerUnit,
                            p.getCurrency());
                });
    }

    private double calculateValuePerUnit(Product product) {
        if (product.getPackageQuantity() == 0) return Double.MAX_VALUE;
        return product.getPrice() / product.getPackageQuantity();
    }
}