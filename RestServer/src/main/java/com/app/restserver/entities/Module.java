package com.app.restserver.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRawValue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "module")
@Getter @Setter
public class Module extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "args", columnDefinition = "json")
    @JsonRawValue
    private String args;

    @ManyToMany(mappedBy = "modules")
    @JsonIgnore
    private List<Pipe> pipes;
}
