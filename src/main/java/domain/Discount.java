package domain;
import java.time.LocalDate;

public class Discount {
    private Product product;
    private LocalDate fromDate;
    private LocalDate toDate;
    private double percentageOfDiscount;


    public Discount(String productId, String productName, String brand, double packageQuantity, String packageUnit, String productCategory, LocalDate fromDate, LocalDate toDate, double percentageOfDiscount) {
        this.product = new Product(productId, productName, brand, packageQuantity, packageUnit, productCategory);
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.percentageOfDiscount = percentageOfDiscount;
    }

    public Discount(Product product, LocalDate fromDate, LocalDate toDate, double percentageOfDiscount) {
        this.product = product;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.percentageOfDiscount = percentageOfDiscount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public double getPercentageOfDiscount() {
        return percentageOfDiscount;
    }

    public void setPercentageOfDiscount(double percentageOfDiscount) {
        this.percentageOfDiscount = percentageOfDiscount;
    }

    private String formatQuantity(double quantity) {
        if (quantity == (long) quantity) {
            return String.format("%d", (long) quantity);
        } else {
            return String.valueOf(quantity);
        }
    }

    @Override
    public String toString() {
        String quantityFormatted = (product.getPackageQuantity() == Math.floor(product.getPackageQuantity()))
                ? String.format("%.0f", product.getPackageQuantity())
                : String.format("%.2f", product.getPackageQuantity());

        return String.format(
                "Id: %s\n" +
                        "Name: %s\n" +
                        "Brand: %s\n" +
                        "Quantity: %s\n" +
                        "Unit: %s\n" +
                        "Category: %s\n" +
                        "From: %s\n" +
                        "To: %s\n" +
                        "Discount: %d%%",
                product.getProductId(),
                product.getProductName(),
                product.getBrand(),
                quantityFormatted,
                product.getPackageUnit(),
                product.getProductCategory(),
                fromDate,
                toDate,
                (int) percentageOfDiscount
        );
    }

}
