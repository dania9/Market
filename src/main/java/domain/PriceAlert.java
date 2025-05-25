package domain;

public class PriceAlert {
    private String productId;
    private double targetPrice;

    public PriceAlert(String productId, double targetPrice) {
        this.productId = productId;
        this.targetPrice = targetPrice;
    }

    public String getProductId() {
        return productId;
    }

    public double getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(double targetPrice) {
        this.targetPrice = targetPrice;
    }

}

