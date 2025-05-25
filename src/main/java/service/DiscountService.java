package service;

import domain.Discount;
import repository.DiscountRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DiscountService {
    private final DiscountRepository discountRepository;
    private int numberOfDiscounts = 10;  // default limit for best discounts

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public void addDiscount(Discount discount) {
        discountRepository.addDiscount(discount);
    }

    public Optional<Discount> getDiscountByProductId(String productId) {
        return discountRepository.findByProductId(productId);
    }

    public boolean deleteDiscountByProductId(String productId) {
        return discountRepository.deleteByProductId(productId);
    }

    public boolean updateDiscount(Discount discount) {
        return discountRepository.updateDiscount(discount);
    }

    public List<Discount> getAllDiscounts() {
        return discountRepository.getAll();
    }

    // List products with the highest current percentage discounts across all tracked stores.
    public List<Discount> getBestDiscounts() {
        LocalDate today = LocalDate.now();
        return discountRepository.getAll().stream()
                .filter(d -> !d.getFromDate().isAfter(today) && !d.getToDate().isBefore(today))
                .sorted(Comparator.comparingDouble(Discount::getPercentageOfDiscount).reversed())
                .limit(numberOfDiscounts)
                .collect(Collectors.toList());
    }

    // Overloaded method if you want to specify limit dynamically
    public List<Discount> getBestDiscounts(int limit) {
        LocalDate today = LocalDate.now();
        return discountRepository.getAll().stream()
                .filter(d -> !d.getFromDate().isAfter(today) && !d.getToDate().isBefore(today))
                .sorted(Comparator.comparingDouble(Discount::getPercentageOfDiscount).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    // List discounts that have been newly added (e.g., within the last 24 hours).
    public List<Discount> getNewDiscounts(LocalDate sinceDate) {
        return discountRepository.getAll().stream()
                .filter(discount -> !discount.getFromDate().isBefore(sinceDate))
                .collect(Collectors.toList());
    }
    public void setNumberOfDiscounts(int numberOfDiscounts) {
        this.numberOfDiscounts = numberOfDiscounts;
    }
}
