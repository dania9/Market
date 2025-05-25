/*package service;

import domain.Product;
import domain.PriceHistory;
import repository.PriceHistoryRepository;
import repository.ProductRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class PriceHistoryService {
    private final PriceHistoryRepository priceHistoryRepository;
    private final ProductRepository productRepository;

    public PriceHistoryService(PriceHistoryRepository priceHistoryRepository,ProductRepository productRepository) {
        this.priceHistoryRepository = priceHistoryRepository;
        this.productRepository = productRepository;
    }


    public List<PriceHistory> getByProductId(String productId) {
        return priceHistoryRepository.getAll().stream()
                .filter(ph -> ph.getProductId().equals(productId))
                .collect(Collectors.toList());
    }


    public List<PriceHistory> getByCategory(String category) {
        return priceHistoryRepository.getAll().stream()
                .filter(ph -> productRepository.findById(ph.getProductId()).stream()
                        .anyMatch(product -> product.getProductCategory().equalsIgnoreCase(category)))
                .collect(Collectors.toList());
    }

    public List<PriceHistory> getByBrand(String brand) {
        return priceHistoryRepository.getAll().stream()
                .filter(ph -> productRepository.findById(ph.getProductId()).stream()
                        .anyMatch(product -> product.getBrand().equalsIgnoreCase(brand)))
                .collect(Collectors.toList());
    }

    public List<PriceHistory> getPriceHistoryForProductsById(String productId) {
        // Clear previous history session
        priceHistoryRepository.clear();

        // Find all products matching the productId
        List<Product> matchingProducts = productRepository.findById(productId);

        // For each matching product, create a new PriceHistory entry and add it to the repo
        for (Product product : matchingProducts) {
            PriceHistory ph = new PriceHistory(
                    product.getProductId(),
                    LocalDate.now(),
                    product.getPrice()
            );
            priceHistoryRepository.addPriceHistory(ph);
        }


        List<PriceHistory> result = priceHistoryRepository.getAll().stream()
                .sorted(Comparator.comparing(PriceHistory::getDate))
                .collect(Collectors.toList());

        return result;
    }



}
*/