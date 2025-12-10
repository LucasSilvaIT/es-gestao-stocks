package github.lucassilvait.geststocks.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduct;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Double margin;

    @Column(nullable = false)
    private Integer quantity;

    // Constructors

    public Product() {
        this.quantity = 0; // Initialize stock to zero
    }

    public Product(String name, Double price, Double margin) {
        this.name = name;
        this.price = price;
        this.margin = margin;
        this.quantity = 0;
    }

    // Business logic methods

    public void updateStock(int quantity) {
        this.quantity += quantity;
    }

    // Getters and Setters

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMargin() {
        return margin;
    }

    public void setMargin(Double margin) {
        this.margin = margin;
    }

    public Integer getQuantity() {
        return quantity;
    }

    // Setter for quantity is generally needed for JPA, but business logic
    // should ideally use updateStock(int quantity)
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
