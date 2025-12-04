package github.lucassilvait.geststocks.data.repositories;

import github.lucassilvait.geststocks.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
