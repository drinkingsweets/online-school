package app.onlineschool.repository;

import app.onlineschool.model.Course;
import app.onlineschool.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.courseProgress cp WHERE KEY(cp) = :courseId")
    List<User> findUsersByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT c.id, COUNT(u) FROM User u JOIN u.courses c GROUP BY c.id ORDER BY COUNT(u) DESC")
    List<Object[]> countUsersByCourseId();

    @Query("SELECT COUNT(u) FROM User u WHERE EXTRACT(MONTH FROM u.lastLogin) = :month")
    Integer countUsersByMonth(@Param("month") int month);

    @Query("SELECT COUNT(u) FROM User u")
    Integer countAllUsers();

    List<User> findByCoursesContaining(Course course);
}