package repository;

import domain.Product;
import domain.ShoppingList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShoppingListRepository {

    private final Connection connection;
    private final ProductRepository productRepository;

    public ShoppingListRepository(Connection connection, ProductRepository productRepository) {
        this.connection = connection;
        this.productRepository = productRepository;
    }

    public List<ShoppingList> getAll() {
        List<ShoppingList> shoppingListItems = new ArrayList<>();
        String sql = "SELECT product_id, quantity FROM shopping_list";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String productId = rs.getString("product_id");
                int quantity = rs.getInt("quantity");


                List<Product> productsFound = productRepository.findById(productId);

                if (!productsFound.isEmpty()) {
                    Product product = productsFound.get(0);
                    shoppingListItems.add(new ShoppingList(product, quantity));
                } else {

                    System.out.printf("Warning: productId %s found in shopping_list but not in ProductRepository%n", productId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoppingListItems;
    }


    public void addItem(ShoppingList item) {
        String sql = "INSERT INTO shopping_list (product_id, quantity) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, item.getProduct().getProductId());
            pstmt.setInt(2, item.getQuantity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean removeItem(ShoppingList item) {
        String sql = "DELETE FROM shopping_list WHERE product_id = ? AND quantity = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, item.getProduct().getProductId());
            pstmt.setInt(2, item.getQuantity());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
