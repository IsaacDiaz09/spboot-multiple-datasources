package dev.isadiape.tests.multidatasourcejpa.repository.first;

import dev.isadiape.tests.multidatasourcejpa.entity.first.EntityA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirstRepository extends JpaRepository<EntityA, Long> {
}
