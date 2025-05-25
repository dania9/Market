package domain;

public class Product {
    private String productId;
    private String productName;
    private String productCategory;
    private String brand;
    private double packageQuantity;
    private String packageUnit;
    private double price;
    private String currency;


    public Product(String productId, String productName, String brand, double packageQuantity, String packageUnit, String productCategory) {
        this.productId = productId;
        this.productName = productName;
        this.brand = brand;
        this.packageQuantity = packageQuantity;
        this.packageUnit = packageUnit;
        this.productCategory = productCategory;
    }


    public Product(String productId, String productName, String productCategory, String brand, double packageQuantity, String packageUnit, double price, String currency) {
        this.productId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.brand = brand;
        this.packageQuantity = packageQuantity;
        this.packageUnit = packageUnit;
        this.price = price;
        this.currency = currency;
    }

    public String getProductId() {
        return productId; }

    public String getProductName() {
        return productName; }

    public String getProductCategory() {
        return productCategory; }

    public String getBrand() {
        return brand; }

    public double getPackageQuantity() {
        return packageQuantity; }

    public String getPackageUnit() {
        return packageUnit; }

    public double getPrice() {
        return price; }

    public String getCurrency() {
        return currency; }


    public void setProductId(String productId) {
        this.productId = productId; }

    public void setProductName(String productName) {
        this.productName = productName; }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory; }

    public void setBrand(String brand) {
        this.brand = brand; }

    public void setPackageQuantity(double packageQuantity) {
        this.packageQuantity = packageQuantity; }

    public void setPackageUnit(String packageUnit) {
        this.packageUnit = packageUnit; }

    public void setPrice(double price) {
        this.price = price; }

    public void setCurrency(String currency) {
        this.currency = currency; }


    @Override
    public String toString() {
        String quantityFormatted = (packageQuantity == Math.floor(packageQuantity))
                ? String.format("%.0f", packageQuantity)
                : String.format("%.2f", packageQuantity);

        return String.format(
                "Id: %s\n" +
                        "Name: %s\n" +
                        "Category: %s\n" +
                        "Brand: %s\n" +
                        "Quantity: %s\n" +
                        "Unit: %s\n" +
                        "Price: %.2f\n" +
                        "Currency: %s",
                productId, productName, productCategory, brand, quantityFormatted, packageUnit, price, currency.toUpperCase()
        );
    }

    public double getValuePerUnit() {
        if (packageQuantity == 0) return Double.MAX_VALUE;
        return price / packageQuantity;
    }
}


