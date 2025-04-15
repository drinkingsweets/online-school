package app.onlineschool.repository;

import app.onlineschool.model.Answer;
import app.onlineschool.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    void deleteAllByQuestion(Question question);
}
