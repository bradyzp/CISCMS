package net.nsnsns.ciscms.services;

import net.nsnsns.ciscms.models.Instructor;
import net.nsnsns.ciscms.models.Student;
import net.nsnsns.ciscms.repos.InstructorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public Optional<Instructor> getInstructor(Integer id) {
        return instructorRepository.findById(id);
    }

    public List<Instructor> getStudentInstructors(Student student) {
        return instructorRepository.getAllByOwner(student);
    }

    public void save(Instructor instructor) {
        instructorRepository.save(instructor);
    }
}
