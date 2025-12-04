package github.lucassilvait.geststocks.data.repositories;

import github.lucassilvait.geststocks.domain.entities.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Integer> {



}
