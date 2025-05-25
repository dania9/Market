package domain;

import java.time.LocalDate;

public class PriceHistory {
    private String productId;
    private LocalDate date;
    private double price;

    public PriceHistory(String productId, LocalDate date, double price) {
        this.productId = productId;
        this.date = date;
        this.price = price;
    }


    public String getProductId() {
        return productId;
    }


    public LocalDate getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }



    public void setDate(LocalDate date) {
        this.date = date;
    }


    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("PriceHistory{productId='%s', date=%s, price=%.2f}",
                productId, date, price);
    }
}

