package net.nsnsns.ciscms.services;

import net.nsnsns.ciscms.models.Instructor;
import net.nsnsns.ciscms.models.Student;
import net.nsnsns.ciscms.repos.InstructorRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Transactional
    @PostAuthorize("returnObject.get().owner.username == authentication.name")
    public Optional<Instructor> getInstructor(Integer id) {
        Optional<Instructor> instructor = instructorRepository.findById(id);
        instructor.ifPresent(value -> value.getOfficeHoursList().size());
        return instructor;

    }

    public List<Instructor> getStudentInstructors(Student student) {
        return instructorRepository.getAllByOwner(student);
    }

    public void save(Instructor instructor) {
        instructorRepository.save(instructor);
    }
}
