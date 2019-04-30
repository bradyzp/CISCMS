package net.nsnsns.ciscms.repos;

import net.nsnsns.ciscms.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findStudentByUsername(String username);

    @Query("select s from Student s where s.username = :#{principal.username}")
    Optional<Student> getAuthenticatedStudent();

    @Query("select s from Student s join fetch s.courses where s.username = :#{principal.username}")
    List<Student> getStudentAndCourses();
}
