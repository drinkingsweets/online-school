package app.onlineschool.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @Column(columnDefinition = "TEXT")
    private String content;

    private int numberInLesson;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;

    public void clearAndSetAnswers(List<Answer> answers) {
        this.answers.clear(); // Clear existing collection
        if (answers != null) {
            this.answers.addAll(answers); // Add new answers
            answers.forEach(a -> a.setQuestion(this)); // Set bidirectional relationship
        }
    }
}
