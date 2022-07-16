package com.luckwheat.notes.entity;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Table(name = "note")
@Introspected
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "note_seq")
    @SequenceGenerator(name="note_seq", sequenceName = "note_seq", allocationSize = 1)
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Name cannot be empty")
    @Column(name = "content")
    private String content;

    @Column(name = "created_ts")
    private LocalDateTime createdTimestamp;

    @Column(name = "updated_ts")
    private LocalDateTime updatedTimestamp;

    @ManyToOne
    private Project project;
}
