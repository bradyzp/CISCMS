package net.nsnsns.ciscms.services;

import net.nsnsns.ciscms.models.Student;
import net.nsnsns.ciscms.repos.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void registerStudent(Student student) {
        studentRepository.save(student);
    }

    public Optional<Student> getAuthenticatedStudent() {
        return studentRepository.getAuthenticatedStudent();
    }
}
