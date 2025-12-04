package github.lucassilvait.geststocks.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "movements")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

}
