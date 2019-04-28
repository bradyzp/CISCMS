package net.nsnsns.ciscms.repos;

import net.nsnsns.ciscms.models.Semester;
import net.nsnsns.ciscms.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface SemesterRepository extends JpaRepository<Semester, Integer> {

    Collection<Semester> getAllByOwner(Student owner);

}
