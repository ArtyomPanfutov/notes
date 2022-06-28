package com.luckwheat.notes.entity;

import javax.persistence.*;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_generator")
    @SequenceGenerator(name="project_generator", sequenceName = "project_seq")
    private Long id;

    @Column(name = "name")
    private String name;
}
