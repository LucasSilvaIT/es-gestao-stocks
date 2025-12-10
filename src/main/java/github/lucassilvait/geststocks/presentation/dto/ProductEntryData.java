package github.lucassilvait.geststocks.presentation.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;

import java.io.Serializable;

public record ProductEntryData(

        Integer idProduct,
        // PASSO 2: Aplicar o grupo ProductCreation.class
        @NotBlank(message = "O nome é obrigatório", groups = ProductCreation.class)
        String name,

        @NotNull(message = "O preço é obrigatório.", groups = ProductCreation.class)
        @DecimalMin(value = "0.01", message = "O preço deve ser positivo.", groups = ProductCreation.class)
        Double price,

        @NotNull(message = "A margem é obrigatória.", groups = ProductCreation.class)
        @DecimalMin(value = "0.0", message = "A margem não pode ser negativa.", groups = ProductCreation.class)
        Double margin,

        @NotNull(message = "A quantidade de entrada é obrigatória.")
        @Min(value = 1, message = "A quantidade deve ser pelo menos 1.")
        Integer quantity

) implements Serializable {

    public ProductEntryData(Integer idProduct, Integer quantity) {
        // Calls the primary constructor with default/null values for other fields
        this(idProduct, null, null, null, quantity);
    }

    public ProductEntryData(String name, Double price, Double margin, Integer quantity) {
        // Calls the primary constructor with a null ID
        this(null, name, price, margin, quantity);
    }

}
