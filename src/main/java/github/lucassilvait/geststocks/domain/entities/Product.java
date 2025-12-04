package github.lucassilvait.geststocks.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduto;

    private String nome;

    private Integer preco;

    private Integer margem;

    private Integer quantidade;
    
}
