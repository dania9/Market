package domain;

public class ShoppingList {
    private Product product;
    private int quantity;

    public ShoppingList() {}

    public ShoppingList(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("%s x%d", product.getProductName(), quantity);
    }
}

