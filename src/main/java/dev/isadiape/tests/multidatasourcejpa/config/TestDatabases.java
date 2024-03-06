package dev.isadiape.tests.multidatasourcejpa.config;

import dev.isadiape.tests.multidatasourcejpa.entity.first.EntityA;
import dev.isadiape.tests.multidatasourcejpa.entity.second.EntityB;
import dev.isadiape.tests.multidatasourcejpa.repository.first.FirstRepository;
import dev.isadiape.tests.multidatasourcejpa.repository.second.SecondRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class TestDatabases {

    private final FirstRepository firstRepository;
    private final SecondRepository secondRepository;

    public TestDatabases(FirstRepository firstRepository, SecondRepository secondRepository) {
        this.firstRepository = firstRepository;
        this.secondRepository = secondRepository;
    }

    @PostConstruct
    public void test() {
        testFirstDb();
        testSecondDb();
    }

    private void testFirstDb() {
        var entityA = new EntityA();
        entityA.setName("My First persisted entity");
        var entityPersisted = firstRepository.save(entityA);
        System.out.println("Persisted Entity A :" + entityPersisted);

        var foundEntity = firstRepository.findById(entityPersisted.getId());
        System.out.println("Found Entity A :" + foundEntity);

        System.out.println("Entity A - Count: " + firstRepository.count());
    }

    private void testSecondDb() {
        var entityB = new EntityB();
        entityB.setName("My Second persisted entity");
        var entityPersisted = secondRepository.save(entityB);
        System.out.println("Persisted Entity B :" + entityPersisted);

        var foundEntity = firstRepository.findById(entityPersisted.getId());
        System.out.println("Found Entity B :" + foundEntity);

        System.out.println("Entity B - Count: " + firstRepository.count());
    }
}
