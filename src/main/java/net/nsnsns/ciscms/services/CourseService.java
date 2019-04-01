package net.nsnsns.ciscms.services;

import net.nsnsns.ciscms.models.Course;
import net.nsnsns.ciscms.models.Gradeable;
import net.nsnsns.ciscms.repos.CourseRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseService {
    private final StudentService studentService;
    private final CourseRepository courseRepository;

    CourseService(StudentService studentService, CourseRepository courseRepository) {
        this.studentService = studentService;
        this.courseRepository = courseRepository;
    }

    public Set<Course> getCourses() {
        return courseRepository.getStudentCourses();
    }

    //    @PostAuthorize("returnObject.get().student.username == authentication.name")
    public Optional<Course> getCourse(Integer id) {
        return courseRepository.getCourseDetails(id);
    }

    public void addCourse(Course course) {
        course.setStudent(studentService.getAuthenticatedStudent().orElseThrow());
        courseRepository.save(course);
    }

    @Transactional
    public void saveCourse(Course course) {
        for (Gradeable g : course.getGradeables()) {
            g.setCourse(course);
        }
        courseRepository.save(course);
    }

}
