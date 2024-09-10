package com.app.restserver.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pipe")
@Getter @Setter @NoArgsConstructor
public class Pipe extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "is_public")
    private boolean isPublic;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "pipe")
    @JsonIgnore
    private List<Execution> executions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "pipe_module",
            joinColumns = @JoinColumn(name = "pipe_id"),
            inverseJoinColumns = @JoinColumn(name = "module_id")
    )
    private List<Module> modules;

    public Pipe(User user, String name, boolean isPublic, List<Module> modules) {
        this.user = user;
        this.name = name;
        this.isPublic = isPublic;
        this.modules = modules;
    }

    @PrePersist
    public void setDefaultValuesForInsert() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void setDefaultValuesForUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
