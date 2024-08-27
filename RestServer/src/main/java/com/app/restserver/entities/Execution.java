package com.app.restserver.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "execution")
@Getter @Setter @NoArgsConstructor
public class Execution extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pipe_id")
    private Pipe pipe;

    @Column(name = "status")
    private String status;

    @Column(name = "result")
    private String result;

    @Column(name = "input")
    private String input;

    @Column(name = "version")
    private int version;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @OneToMany(mappedBy = "execution", fetch = FetchType.EAGER)
    private List<ExecutionModule> executionModules;

    public Execution(User user, Pipe pipe, String input, int version) {
        this.user = user;
        this.pipe = pipe;
        this.input = input;
        this.version = version;
    }

    @PrePersist
    public void setDefaultValuesForInsert() {
        this.status = "NOT_PROCESSED";
        this.createdAt = LocalDateTime.now();
    }

}
