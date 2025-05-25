package repository;

import domain.PriceAlert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PriceAlertRepository {
    private final Connection connection;

    public PriceAlertRepository(Connection connection) {
        this.connection = connection;
    }

    public void addAlert(PriceAlert alert) {
        String sql = "INSERT OR REPLACE INTO price_alerts (product_id, target_price) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, alert.getProductId());
            stmt.setDouble(2, alert.getTargetPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<PriceAlert> getAlertByProductId(String productId) {
        String sql = "SELECT product_id, target_price FROM price_alerts WHERE product_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new PriceAlert(
                        rs.getString("product_id"),
                        rs.getDouble("target_price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean removeAlert(String productId) {
        String sql = "DELETE FROM price_alerts WHERE product_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, productId);
            int affected = stmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<PriceAlert> getAllAlerts() {
        List<PriceAlert> alerts = new ArrayList<>();
        String sql = "SELECT product_id, target_price FROM price_alerts";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                alerts.add(new PriceAlert(
                        rs.getString("product_id"),
                        rs.getDouble("target_price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alerts;
    }
}
