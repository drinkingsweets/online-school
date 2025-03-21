package app.onlineschool.repositoty;

import app.onlineschool.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTitleContainingIgnoreCase(String query);

    List<Course> findByTitleContainingIgnoreCaseAndCreatedAtBetween(String query, LocalDate startDate, LocalDate endDate);

    List<Course> findByCreatedAtBetween(LocalDate startDate, LocalDate endDate);

    List<Course> findByTitleContainingIgnoreCaseAndCreatedAtAfter(String query, LocalDate startDate);

    List<Course> findByTitleContainingIgnoreCaseAndCreatedAtBefore(String query, LocalDate endDate);

    List<Course> findByCreatedAtAfter(LocalDate startDate);

    List<Course> findByCreatedAtBefore(LocalDate endDate);

}
