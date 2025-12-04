package github.lucassilvait.geststocks.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduct;

    private String name;

    private Integer price;

    private Integer margin;

    private Integer quantity;
    
}
