package com.app.restserver.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRawValue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "execution_module")
@Getter @Setter @NoArgsConstructor
public class ExecutionModule extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "execution_id")
    @JsonIgnore
    private Execution execution;

    @Column(name = "module_name")
    private String moduleName;

    @Column(name = "args")
    @JsonRawValue
    private String args;

    public ExecutionModule(Execution execution, String moduleName, String args) {
        this.execution = execution;
        this.moduleName = moduleName;
        this.args = args;
    }
}
