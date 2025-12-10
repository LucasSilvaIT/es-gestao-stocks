package github.lucassilvait.geststocks.presentation.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;

import java.io.Serializable;

public record ProductEntryData(

        Integer idProduct,
        String name,
        Double price,
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
