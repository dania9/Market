package service;

import domain.PriceAlert;
import domain.Product;
import repository.PriceAlertRepository;
import repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PriceAlertService {

    private final PriceAlertRepository priceAlertRepo;
    private final ProductRepository productRepo;

    public PriceAlertService(PriceAlertRepository priceAlertRepo, ProductRepository productRepo) {
        this.priceAlertRepo = priceAlertRepo;
        this.productRepo = productRepo;
    }


    public void addAlert(String productId, double targetPrice) {
        priceAlertRepo.addAlert(new PriceAlert(productId, targetPrice));
    }


    public boolean removeAlert(String productId) {
        return priceAlertRepo.removeAlert(productId);
    }


    public List<PriceAlert> getAllAlerts() {
        return priceAlertRepo.getAllAlerts();
    }

    public List<PriceAlert> checkTriggeredAlerts() {
        List<PriceAlert> allAlerts = priceAlertRepo.getAllAlerts();
        List<PriceAlert> triggeredAlerts = new ArrayList<>();

        for (PriceAlert alert : allAlerts) {
            List<Product> products = productRepo.findById(alert.getProductId());

            for (Product product : products) {
                if (product.getPrice() <= alert.getTargetPrice()) {
                    triggeredAlerts.add(alert);
                    break;
                }
            }
        }

        return triggeredAlerts;
    }


    public Optional<PriceAlert> getAlertByProductId(String productId) {
        return priceAlertRepo.getAlertByProductId(productId);
    }
}
