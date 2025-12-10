package github.lucassilvait.geststocks.domain.services;

import github.lucassilvait.geststocks.data.repositories.ProductRepository;
import github.lucassilvait.geststocks.data.repositories.StockMovementRepository;
import github.lucassilvait.geststocks.domain.entities.Product;
import github.lucassilvait.geststocks.domain.entities.StockMovement;
import github.lucassilvait.geststocks.presentation.dto.ProductEntryData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @InjectMocks
    private StockService stockService;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private StockMovementRepository stockMovementRepository;

    private Product existingProduct;

    @BeforeEach
    void setUp() {
        existingProduct = new Product("Camisola Azul", 30.0, 40.0);
        existingProduct.setIdProduct(1);
        existingProduct.setQuantity(10);
    }

    @Test
    void registerEntry_shouldCreateNewProductWhenNameIsUnique() {

        ProductEntryData newProductData = new ProductEntryData(
                "T-shirt Verde", 20.0, 50.0, 50); // Construtor de criação (ID=null)

        when(productRepository.findByName(newProductData.name())).thenReturn(Optional.empty());

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product product = invocation.getArgument(0);
            product.setIdProduct(2);
            return product;
        });

        Product savedProduct = stockService.registerProductEntry(newProductData);

        verify(productRepository, times(1)).save(any(Product.class));

        verify(stockMovementRepository, times(1)).save(any(StockMovement.class));

        assertEquals(50, savedProduct.getQuantity(), "O stock final deve ser igual à entrada inicial.");
    }

    @Test
    void registerEntry_shouldThrowExceptionAndProvideIdWhenNameAlreadyExists() {
        ProductEntryData duplicateNameData = new ProductEntryData(
                existingProduct.getName(), 20.0, 50.0, 50);

        when(productRepository.findByName(duplicateNameData.name())).thenReturn(Optional.of(existingProduct));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            stockService.registerProductEntry(duplicateNameData);
        }, "Deveria lançar exceção ao tentar criar produto com nome duplicado.");

        String expectedMessagePart = String.format("ID %d.", existingProduct.getIdProduct());
        assertTrue(thrown.getMessage().contains(expectedMessagePart),
                "A mensagem de erro deve incluir o ID do produto existente (Simulação do UC Consultar Stock).");

        verify(productRepository, never()).save(any());
        verify(stockMovementRepository, never()).save(any());
    }

    @Test
    void registerEntry_shouldUpdateExistingProductStockById() {

        int entryQuantity = 25;
        ProductEntryData updateData = new ProductEntryData(existingProduct.getIdProduct(), entryQuantity);

        when(productRepository.findById(existingProduct.getIdProduct())).thenReturn(Optional.of(existingProduct));

        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        int expectedQuantity = existingProduct.getQuantity() + entryQuantity; // 10 + 25 = 35

        Product updatedProduct = stockService.registerProductEntry(updateData);

        assertEquals(expectedQuantity, updatedProduct.getQuantity(),
                "O stock final deve ser a soma do stock inicial com a entrada.");

        verify(productRepository, times(1)).save(existingProduct);

        verify(stockMovementRepository, times(1)).save(any(StockMovement.class));
    }

    @Test
    void registerEntry_shouldPreserveDetailsWhenUpdatingStockByIdWithNullFields() {
        // ARRANGE
        double originalPrice = existingProduct.getPrice(); // 30.0
        double originalMargin = existingProduct.getMargin(); // 40.0
        String originalName = existingProduct.getName(); // "Camisola Azul"

        ProductEntryData updateData = new ProductEntryData(
                existingProduct.getIdProduct(), // ID=1
                null,                          // Nome nulo
                null,                          // Preço nulo
                null,                          // Margem nula
                5                              // Quantidade de entrada
        );

        when(productRepository.findById(existingProduct.getIdProduct())).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        Product updatedProduct = stockService.registerProductEntry(updateData);

        assertEquals(15, updatedProduct.getQuantity(), "O stock deve ter sido atualizado.");

        assertEquals(originalName, updatedProduct.getName(), "O nome deve ser preservado.");
        assertEquals(originalPrice, updatedProduct.getPrice(), 0.001, "O preço deve ser preservado.");
        assertEquals(originalMargin, updatedProduct.getMargin(), 0.001, "A margem deve ser preservada.");
    }

    @Test
    void registerEntry_shouldThrowExceptionWhenIdForUpdateIsNotFound() {

        int nonExistentId = 99;
        ProductEntryData invalidIdData = new ProductEntryData(nonExistentId, 10);

        when(productRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            stockService.registerProductEntry(invalidIdData);
        }, "Deveria lançar exceção se o ID não for encontrado.");

        String expectedMessage = String.format("Produto com o ID %d não existe no sistema.", nonExistentId);
        assertTrue(thrown.getMessage().contains(expectedMessage), "A mensagem de erro deve indicar que o ID não foi encontrado.");

        verify(productRepository, never()).save(any());
        verify(stockMovementRepository, never()).save(any());
    }
}