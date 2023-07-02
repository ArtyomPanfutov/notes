package com.panfutov.notes.entity;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "daily_plan")
@Introspected
@Getter
@Setter
@NoArgsConstructor
public class DailyPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "daily_plan_seq")
    @SequenceGenerator(name="daily_plan_seq", sequenceName = "daily_plan_seq", allocationSize = 1)
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    @Column(name = "name")
    private String name;

    @Column(name = "targetDate")
    @NotEmpty(message = "Target date cannot be empty")
    private LocalDate targetDate;

    @Column(name = "created_ts")
    @CreationTimestamp
    private LocalDateTime createdTimestamp;

    @Column(name = "updated_ts")
    @UpdateTimestamp
    private LocalDateTime updatedTimestamp;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "daily_plan_task",
            joinColumns = @JoinColumn(name = "daily_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "daily_task_id")
    )
    @OrderBy(value="lower(order) asc")
    private List<DailyTask> tasks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}
