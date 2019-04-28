package net.nsnsns.ciscms.repos;

import net.nsnsns.ciscms.models.Instructor;
import net.nsnsns.ciscms.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

    List<Instructor> getAllByOwner(Student owner);
}
