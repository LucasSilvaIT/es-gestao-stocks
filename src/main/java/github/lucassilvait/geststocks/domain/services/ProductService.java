package github.lucassilvait.geststocks.domain.services;

import github.lucassilvait.geststocks.data.repositories.ProductRepository;
import github.lucassilvait.geststocks.domain.entities.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct() {
        productRepository.save(new Product());
    }

}
