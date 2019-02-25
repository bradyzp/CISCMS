package net.nsnsns.ciscms.repos;

import net.nsnsns.ciscms.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("select c from Course c where c.student.username = ?#{principal.username}")
    Set<Course> getStudentCourses();

}
