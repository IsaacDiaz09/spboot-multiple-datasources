package dev.isadiape.tests.multidatasourcejpa.entity.second;

import jakarta.persistence.*;

@Entity
@Table(name = "table_b")
public class EntityB {


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
        return "EntityB{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
