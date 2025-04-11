package app.onlineschool.repository;

import app.onlineschool.model.Course;
import app.onlineschool.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    public Optional<Lesson> findByCourseIdAndLessonNumber(long courseId, long lessonNumber);

    void deleteByLessonNumberAndCourse(int lessonNumber, Course course);

    @Query("SELECT COUNT(l) FROM Lesson l")
    public int countAllLessons();
}
