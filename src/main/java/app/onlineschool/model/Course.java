package app.onlineschool.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Course entity that stores info about courses and includes lessons
 */
@Entity
@Table(name = "courses")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(unique = true)
    private String title;

    @NotBlank
    private String shortDescription;

    @CreatedDate
    private LocalDate createdAt;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons = new ArrayList<>();

    @ManyToMany(mappedBy = "courses")
    private List<User> users = new ArrayList<>();

    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
        lesson.setCourse(null); // Важно! Чтобы Hibernate понял, что lesson действительно удаляется
    }
}
