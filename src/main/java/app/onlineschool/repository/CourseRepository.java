package app.onlineschool.repository;

import app.onlineschool.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("SELECT c.title FROM Course c")
    List<String> findAllCourseNames();

    // New method to count courses created in a given month.
    @Query("SELECT COUNT(c) FROM Course c WHERE EXTRACT(MONTH FROM c.createdAt) = ?1")
    Integer countCoursesByMonth(int month);

    @Query("SELECT COUNT(c) FROM Course c")
    Integer countAllCourses();
}
