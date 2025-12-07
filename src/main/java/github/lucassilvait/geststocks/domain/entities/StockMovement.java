package github.lucassilvait.geststocks.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMovement;

    @Column(nullable = false)
    private LocalDateTime dateMovement;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeMovement typeMovement;

    public enum TypeMovement {
        ENTRY,
        EXIT,
        RETURN
    }

    // Constructors

    // Default constructor for JPA
    public StockMovement() {
        this.dateMovement = LocalDateTime.now();
    }

    public StockMovement(Product product, Integer quantity, TypeMovement typeMovement) {
        this.product = product;
        this.quantity = quantity;
        this.typeMovement = typeMovement;
        this.dateMovement = LocalDateTime.now();
    }

    public Integer getIdMovement() {
        return idMovement;
    }

    public LocalDateTime getDateMovement() {
        return dateMovement;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public TypeMovement getTypeMovement() {
        return typeMovement;
    }

    public void setTypeMovement(TypeMovement typeMovement) {
        this.typeMovement = typeMovement;
    }
}
