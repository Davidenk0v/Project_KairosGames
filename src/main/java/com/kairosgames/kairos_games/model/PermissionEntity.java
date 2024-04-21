package com.kairosgames.kairos_games.model;

import jakarta.persistence.*;

@Entity
@Table(name = "permissions")
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String name;

    public PermissionEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PermissionEntity() {
    }

    public static PermissionEntityBuilder builder() {
        return new PermissionEntityBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class PermissionEntityBuilder {
        private Long id;
        private String name;

        PermissionEntityBuilder() {
        }

        public PermissionEntityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PermissionEntityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PermissionEntity build() {
            return new PermissionEntity(this.id, this.name);
        }

        public String toString() {
            return "PermissionEntity.PermissionEntityBuilder(id=" + this.id + ", name=" + this.name + ")";
        }
    }
}