package github.lucassilvait.geststocks.domain.services;

import github.lucassilvait.geststocks.data.repositories.ProductRepository;
import github.lucassilvait.geststocks.data.repositories.StockMovementRepository;
import github.lucassilvait.geststocks.domain.entities.Product;
import github.lucassilvait.geststocks.domain.entities.StockMovement;
import github.lucassilvait.geststocks.domain.entities.StockMovement.TypeMovement;
import github.lucassilvait.geststocks.presentation.dto.ProductEntryData;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockService {

    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    public StockService(ProductRepository productRepository, StockMovementRepository stockMovementRepository) {
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    /**
     * Use Case: Register a product entry, updating the product and recording the movement.
     * The @Transactional annotation ensures that both database operations (update product and save movement)
     * succeed or fail together (Atomicity).
     *
     * @param data The DTO containing the product details and entry quantity.
     * @return The updated Product entity.
     */
    @Transactional
    public Product registerProductEntry(ProductEntryData data) {

        // Find or Create the Product
        Product product = findOrCreateProduct(data);

        // Update the Product Stock
        product.updateStock(data.quantity());

        // 3. Persist the updated Product
        Product savedProduct = productRepository.save(product);

        // 4. Record the Stock Movement
        StockMovement movement = new StockMovement(
                savedProduct,                                  // The product involved
                data.quantity(),                               // The quantity moved
                TypeMovement.ENTRY                             // The type of movement
        );
        stockMovementRepository.save(movement);

        return savedProduct;
    }

    /**
     * Helper method to find an existing product or create a new one based on the DTO.
     *
     * @param data The input DTO.
     * @return An existing or new Product entity.
     * @throws IllegalArgumentException if data is incomplete for creation.
     */
    private Product findOrCreateProduct(ProductEntryData data) {

        // If an ID is provided, try to find the existing product
        if (data.idProduct() != null) {
            Optional<Product> existingProduct = productRepository.findById(data.idProduct());
            if (existingProduct.isPresent()) {
                return existingProduct.get();
            }
        }

        return new Product(
                data.name(),
                data.price(),
                data.margin()
        );
    }

}