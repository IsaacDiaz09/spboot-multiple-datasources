package dev.isadiape.tests.multidatasourcejpa.repository.second;

import dev.isadiape.tests.multidatasourcejpa.entity.second.EntityB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondRepository extends JpaRepository<EntityB, Long> {
}
