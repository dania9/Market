package service;

import domain.Product;
import repository.ProductRepository;

import java.util.List;
import java.util.Optional;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }

    public List<Product> getProductsById(String productId) {
        return productRepository.findById(productId);
    }

    public boolean deleteProductById(String productId) {
        return productRepository.deleteById(productId);
    }

    public boolean updateProduct(Product oldProduct, Product newProduct) {
        return productRepository.updateProduct(oldProduct, newProduct);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAll();
    }

    public void printBestValueProducts(String category) {
        List<Product> bestValueProducts = productRepository.getBestValueProducts(category);
        if (bestValueProducts.isEmpty()) {
            System.out.println("No products found in category: " + category);
            return;
        }

        System.out.printf("%-20s %-10s %-10s %-10s %-10s %-10s%n",
                "Product Name", "Brand", "Price", "Qantity", "Unit", "Value/Unit");

        for (Product p : bestValueProducts) {
            System.out.printf("%-20s %-10s %-10.2f %-10d %-10s %-10.2f%n",
                    p.getProductName(), p.getBrand(), p.getPrice(), p.getPackageQuantity(),
                    p.getPackageUnit(), p.getValuePerUnit());
        }

        // Highlight the best value
        Product bestBuy = bestValueProducts.get(0);
        System.out.println("\nBest value: " + bestBuy.getProductName() + " (" +
                bestBuy.getBrand() + ") at " + bestBuy.getValuePerUnit() + " per " +
                bestBuy.getPackageUnit());
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

}


