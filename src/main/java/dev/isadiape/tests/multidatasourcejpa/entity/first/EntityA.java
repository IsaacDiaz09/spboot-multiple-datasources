package dev.isadiape.tests.multidatasourcejpa.entity.first;

import jakarta.persistence.*;

@Entity
@Table(name = "table_a")
public class EntityA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EntityA{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
