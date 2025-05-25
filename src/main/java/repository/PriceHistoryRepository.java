/*package repository;

import domain.PriceHistory;

import java.util.*;
import java.util.stream.Collectors;

public class PriceHistoryRepository {
    private List<PriceHistory> priceHistories = new ArrayList<>();

    public List<PriceHistory> getAll() {
        return new ArrayList<>(priceHistories);
    }
    public void addPriceHistory(PriceHistory ph) {
        priceHistories.add(ph);
    }

    public void clear() {
        priceHistories.clear();
    }


    public List<PriceHistory> getByProductId(String productId) {
        return priceHistories.stream()
                .filter(ph -> ph.getProductId().equals(productId))
                .collect(Collectors.toList());
    }

}
*/