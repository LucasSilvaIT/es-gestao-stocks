package github.lucassilvait.geststocks.domain.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "movements")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMovement;

    private Date dateMovement;

    private Integer quantity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product-")
    private Product product;

    private TypeMovement typeMovement;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public enum TypeMovement {
        ENTRY,
        EXIT,
        RETURN
    }

}
