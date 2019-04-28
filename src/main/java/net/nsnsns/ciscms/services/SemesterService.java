package net.nsnsns.ciscms.services;

import net.nsnsns.ciscms.models.Semester;
import net.nsnsns.ciscms.models.Student;
import net.nsnsns.ciscms.repos.SemesterRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SemesterService {
    private final SemesterRepository repository;

    public SemesterService(SemesterRepository repository) {
        this.repository = repository;
    }

    public Collection<Semester> getStudentSemesters(Student student) {
        return repository.getAllByOwner(student);
    }

    public void save(Semester semester) {
        repository.save(semester);
    }
}
