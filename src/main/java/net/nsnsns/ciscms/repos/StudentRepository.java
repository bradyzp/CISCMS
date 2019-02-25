package net.nsnsns.ciscms.repos;

import net.nsnsns.ciscms.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findStudentByUsername(String username);

    @Query("select s from Student s where s.username = ?#{principal.username}")
    List<Student> getAuthenticatedStudent();
}
