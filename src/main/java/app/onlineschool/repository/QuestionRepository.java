package app.onlineschool.repository;

import app.onlineschool.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByTestIdAndNumberInLesson(long id, int numberInLesson);

    List<Question> findByTestIdAndNumberInLessonGreaterThan(long testId, int deletedQuestionNumber);
}
