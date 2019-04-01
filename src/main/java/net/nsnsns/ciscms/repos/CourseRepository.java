package net.nsnsns.ciscms.repos;

import net.nsnsns.ciscms.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query("select c from Course c where c.student.username = :#{principal.username}")
    Set<Course> getStudentCourses();

    @Query("SELECT c FROM Course c WHERE c.id = :id AND c.student.username = :#{principal.username}")
    Optional<Course> getCourseDetails(@Param("id") Integer id);
}
