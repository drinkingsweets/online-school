package app.onlineschool.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false) // Explicitly define the foreign key column
    private Course course;

    @NotBlank
    private String title;

//    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    private int lessonNumber;
}
