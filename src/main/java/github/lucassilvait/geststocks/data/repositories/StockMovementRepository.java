package github.lucassilvait.geststocks.data.repositories;

import github.lucassilvait.geststocks.domain.entities.StockMovement;
import github.lucassilvait.geststocks.domain.entities.StockMovement.TypeMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Integer> {

    List<StockMovement> findByProductIdProductOrderByDateMovementDesc(Integer idProduct);

    List<StockMovement> findByDateMovementBetweenAndTypeMovement(
            LocalDateTime startDate,
            LocalDateTime endDate,
            TypeMovement type
    );

}
