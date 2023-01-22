package com.panfutov.notes.entity;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
@Introspected
@Getter
@Setter
@NoArgsConstructor
public class User {

    public static final String USER_ID = "user_id";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name="user_seq", sequenceName = "user_seq", allocationSize = 1)
    @Field(name = USER_ID)
    private Long id;

    @NotEmpty(message = "Auth0 sub cannot be empty")
    @Column(name = "auth0_sub")
    private String sub;

    @Column(name = "email")
    private String email;
}
