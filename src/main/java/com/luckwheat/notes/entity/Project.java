package com.luckwheat.notes.entity;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "project")
@Introspected
@Getter
@Setter
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
    @SequenceGenerator(name="project_seq", sequenceName = "project_seq")
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    @Column(name = "name")
    private String name;
}
