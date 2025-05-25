package service;

import domain.Product;
import domain.Discount;
import domain.PriceHistory;
import domain.ShoppingList;
import repository.DiscountRepository;
//import repository.PriceHistoryRepository;
import repository.ShoppingListRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ShoppingListService {

    //private final PriceHistoryRepository priceHistoryRepository;
    private final DiscountRepository discountRepository;
    private final ShoppingListRepository shoppingListRepository;

    public ShoppingListService( DiscountRepository discountRepository, ShoppingListRepository shoppingListRepository) {

        this.discountRepository = discountRepository;
        this.shoppingListRepository = shoppingListRepository;
    }

    public void addItem(ShoppingList item) {
        shoppingListRepository.addItem(item);
    }

    public void removeItem(ShoppingList item) {
        shoppingListRepository.removeItem(item);
    }

    public List<ShoppingList> getAllItems() {
        return shoppingListRepository.getAll();
    }

    /*

    public Map<String, List<ShoppingList>> optimizeShoppingLists(List<ShoppingList> basket, LocalDate shoppingDate) {
        Map<String, List<ShoppingList>> shoppingLists = new HashMap<>();

        for (ShoppingList item : basket) {
            Product product = item.getProduct();
            String productId = product.getProductId();

            // Find all price histories for this product on or before shoppingDate, latest first
            List<PriceHistory> relevantPrices = priceHistoryRepository.getAll().stream()
                    .filter(ph -> ph.getProductId().equals(productId))
                    .filter(ph -> !ph.getDate().isAfter(shoppingDate))
                    .sorted(Comparator.comparing(PriceHistory::getDate).reversed())
                    .collect(Collectors.toList());


            Map<String, Double> storeToPrice = new HashMap<>();
            for (PriceHistory ph : relevantPrices) {
                storeToPrice.putIfAbsent(ph.getStore(), ph.getPrice());
            }

            // For each store, I check if there is a valid discount for this product on shoppingDate
            Map<String, Double> storeToEffectivePrice = new HashMap<>();
            for (Map.Entry<String, Double> entry : storeToPrice.entrySet()) {
                double basePrice = entry.getValue();
                double discountPercent = 0;

                Optional<Discount> discountOpt = discountRepository.findByProductId(productId);
                if (discountOpt.isPresent()) {
                    Discount discount = discountOpt.get();
                    if (!shoppingDate.isBefore(discount.getFromDate()) && !shoppingDate.isAfter(discount.getToDate())) {
                        discountPercent = discount.getPercentageOfDiscount();
                    }
                }

                double effectivePrice = basePrice * (1 - discountPercent / 100);
                storeToEffectivePrice.put(entry.getKey(), effectivePrice);
            }

            // Find store with lowest effective price
            Optional<Map.Entry<String, Double>> bestEntry = storeToEffectivePrice.entrySet().stream()
                    .min(Map.Entry.comparingByValue());

            if (bestEntry.isPresent()) {
                String bestStore = bestEntry.get().getKey();
                shoppingLists.putIfAbsent(bestStore, new ArrayList<>());
                shoppingLists.get(bestStore).add(item);
            } else {

                shoppingLists.putIfAbsent("unknown", new ArrayList<>());
                shoppingLists.get("unknown").add(item);
            }
        }

        return shoppingLists;
    }

     */
}
