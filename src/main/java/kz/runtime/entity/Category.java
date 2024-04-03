package kz.runtime.entity;

import jakarta.persistence.*;

@Entity
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long left_key;

    private Long right_key;

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

    public Long getLeft_key() {
        return left_key;
    }

    public void setLeft_key(Long left_key) {
        this.left_key = left_key;
    }

    public Long getRight_key() {
        return right_key;
    }

    public void setRight_key(Long right_key) {
        this.right_key = right_key;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    private Long level;

}
