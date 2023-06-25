package com.panfutov.notes.entity;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "daily_task")
@Introspected
@Getter
@Setter
@NoArgsConstructor
public class DailyTask {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "daily_task_seq")
    @SequenceGenerator(name="daily_task_seq", sequenceName = "daily_task_seq", allocationSize = 1)
    private Long id;

    @NotEmpty(message = "Text cannot be empty")
    @Column(name = "text")
    private String text;

}
