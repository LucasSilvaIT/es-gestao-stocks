package github.lucassilvait.geststocks.presentation.controllers;

import github.lucassilvait.geststocks.domain.entities.Product;
import github.lucassilvait.geststocks.domain.services.StockService;
import github.lucassilvait.geststocks.presentation.dto.ProductCreation;
import github.lucassilvait.geststocks.presentation.dto.ProductEntryData;
import org.springframework.validation.SmartValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/stock")
public class StockEntryController {

    private final StockService stockService;
    private final SmartValidator validator;

    public StockEntryController(StockService stockService, SmartValidator validator) {
        this.stockService = stockService;
        this.validator = validator;
    }

    /**
     * Handles GET requests to display the stock entry form.
     */
    @GetMapping("/entry")
    public String showEntryForm(Model model) {

        if (!model.containsAttribute("productEntryData")) {
            model.addAttribute("productEntryData",
                    new ProductEntryData(null, null, null, null, 1));
        }

        return "stock-entry";
    }

    /**
     * Handles POST requests when the user submits the form.
     */
    @PostMapping("/entry")
    public String registerEntry( @ModelAttribute("productEntryData") ProductEntryData data,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {

        validator.validate(data, result);

        if (data.idProduct() == null) {
            validator.validate(data, result, ProductCreation.class);
        }

        if (result.hasErrors()) {
            return "stock-entry";
        }

        try {
            // Call the Service Layer to execute the business logic
            Product savedProduct = stockService.registerProductEntry(data);

            // Handle Success
            String successMessage = String.format(
                    "Entrada de %d unidades para o produto '%s' registada com sucesso. Atualmente existem %d %s em stock.",
                    data.quantity(),
                    savedProduct.getName(),
                    savedProduct.getQuantity(),
                    savedProduct.getName()
            );
            redirectAttributes.addFlashAttribute("successMessage", successMessage);

            // Redirect to the form to prevent double submission
            return "redirect:/stock/entry";

        } catch (IllegalArgumentException e) {
            // Handle Business Logic Errors (e.g., product not found, name already exists)
            redirectAttributes.addFlashAttribute("errorMessage", "Erro de Registo: " + e.getMessage());

            // Re-add the failed DTO to pre-populate the form after redirect
            redirectAttributes.addFlashAttribute("productEntryData", data);

            return "redirect:/stock/entry";
        }
    }

}