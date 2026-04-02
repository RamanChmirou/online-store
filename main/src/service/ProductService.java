package service;

import entity.Product;
import exception.ProductDoesNotExistsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final List<Product> productList = new ArrayList<>();

    public List<Product> findAll() {
        return productList;
    }

    public Optional<Product> findById(long id) {
        return productList.stream()
                .filter(product -> product.getId() == id)
                .findFirst();
    }

    public void create(Product product) {
        if (findById(product.getId()).isPresent()) {
            throw new ProductDoesNotExistsException("Ten produkt już istnieje.");
        }
        productList.add(product);
    }

    public void update(long id, Product newProduct) {
        Product product = findById(id)
                .orElseThrow(() -> new ProductDoesNotExistsException("Tego produktu nie istnieje."));
        product.setQuantity(newProduct.getQuantity());
        product.setStandardPrice(newProduct.getStandardPrice());
        product.setStandardConfigurationList(newProduct.getStandardConfigurationList());
    }

    public void delete(long id) {
        if (findById(id).isEmpty()) {
            throw new ProductDoesNotExistsException("Tego produktu nie istnieje.");
        }
        productList.removeIf(product -> product.getId() == id);
    }
}
