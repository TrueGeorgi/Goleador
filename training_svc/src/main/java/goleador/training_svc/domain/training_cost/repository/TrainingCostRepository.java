package goleador.training_svc.domain.training_cost.repository;

import goleador.training_svc.domain.training_cost.model.TrainingCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingCostRepository extends JpaRepository<TrainingCost, UUID> {

    Optional<TrainingCost> findFirstBySkillLevelAndActiveTrue(int skillLevel);
}
