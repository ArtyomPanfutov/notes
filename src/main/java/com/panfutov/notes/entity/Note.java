package com.panfutov.notes.entity;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.lucene.analysis.charfilter.HTMLStripCharFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Table(name = "note")
@Indexed(index = "idx_note")
@AnalyzerDefs({
        @AnalyzerDef(name = "el",
                charFilters = {@CharFilterDef(factory = HTMLStripCharFilterFactory.class)},
                tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
                filters = {
                        @TokenFilterDef(factory = StandardFilterFactory.class)
                }
        )
})
@Introspected
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Note {

    public static final String CONTENT_TRANSFORMED = "contentTransformed";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "note_seq")
    @SequenceGenerator(name="note_seq", sequenceName = "note_seq", allocationSize = 1)
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Content cannot be empty")
    @Column(name = "content")
    @Field(name = CONTENT_TRANSFORMED)
    private String content;

    @Column(name = "preview")
    @NotEmpty(message = "Preview cannot be empty")
    private String preview;

    @Column(name = "created_ts")
    @CreationTimestamp
    private LocalDateTime createdTimestamp;

    @Column(name = "updated_ts")
    @UpdateTimestamp
    private LocalDateTime updatedTimestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @IndexedEmbedded(includeEmbeddedObjectId = true)
    private User user;
}
