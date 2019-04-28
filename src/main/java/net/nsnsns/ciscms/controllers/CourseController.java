package net.nsnsns.ciscms.controllers;

import net.nsnsns.ciscms.models.*;
import net.nsnsns.ciscms.services.CourseService;
import net.nsnsns.ciscms.services.InstructorService;
import net.nsnsns.ciscms.services.SemesterService;
import net.nsnsns.ciscms.services.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/course")
public class CourseController {
    private static final String COURSES_OVERVIEW = "courses";
    private static final String COURSE_DETAIL = "course_detail";
    private static final String COURSE_EDIT = "course_edit";
    private static final String COURSE_CREATE = "course_creation";
    private static final String COURSE_REDIRECT = "redirect:/course";
    private static final String COURSES_REDIRECT = "redirect:/courses";
    private static final String COURSE_CREATE_REDIRECT = "redirect:/course/create";
    private static final String INSTRUCTOR_EDIT = "edit_instructor";
    private static final String SEMESTER_EDIT = "edit_semester";

    private final CourseService courseService;
    private final StudentService studentService;
    private final SemesterService semesterService;
    private final InstructorService instructorService;

    CourseController(CourseService courseService, StudentService studentService, SemesterService semesterService, InstructorService instructorService) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.semesterService = semesterService;
        this.instructorService = instructorService;
    }

    @GetMapping
    public String courseOverview(Model model) {
        final Student student = studentService.getAuthenticatedStudent().orElseThrow();
        model.addAttribute("student", student);
        model.addAttribute("courses", courseService.getCourses());
        model.addAttribute("semester", semesterService.getLatestStudentSemester(student).orElse(null));

        return COURSES_OVERVIEW;
    }

    @GetMapping("/{id}")
    public String courseDetail(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Course> course = courseService.getCourse(id);

        if (course.isPresent()) {
            model.addAttribute("course", course.get());

            return COURSE_DETAIL;
        } else {
            redirectAttributes.addFlashAttribute("warning", "Course not found");
            return COURSES_REDIRECT;
        }
    }

    @GetMapping("/{id}/edit")
    public String editCourse(@PathVariable(name = "id") Integer id, Model model) {
        final Course course = courseService.getCourse(id).orElseThrow();
        final Student student = studentService.getAuthenticatedStudent().orElseThrow();
        model.addAttribute("course", course);
        model.addAttribute("semesters", semesterService.getStudentSemesters(student));

        return COURSE_EDIT;
    }

    @PostMapping(value = "/{cid}/edit", params = {"addRow"})
    public String addGradeableRow(@PathVariable(name = "cid") Long courseId, Course course) {

        Gradeable g = new Gradeable();
        g.setCourse(course);
        g.setOwner(studentService.getAuthenticatedStudent().orElseThrow());
        course.getGradeables().add(g);

        return COURSE_EDIT;
    }

    @PostMapping(value = "/{cid}/edit")
    public String saveCourse(@PathVariable(name = "cid") Long courseId, Course course) {
        System.out.println("Saving course details");
        courseService.saveCourse(course);

        return "redirect:/course/" + courseId;
    }


    @GetMapping("/create")
    public String createCourse(Model model) {
        final Student student = studentService.getAuthenticatedStudent().orElseThrow();

        model.addAttribute("course", new Course());
        model.addAttribute("semesters", semesterService.getStudentSemesters(student));
        model.addAttribute("instructors", instructorService.getStudentInstructors(student));

        return COURSE_CREATE;
    }

    @PostMapping(value = "/create", params = {"addSemester"})
    public String addSemester(Model model) {
        model.addAttribute("semester", new Semester());

        return SEMESTER_EDIT;
    }

    @PostMapping(value = "/create", params = {"createSemester"})
    public String createSemester(Semester semester) {
        final Student student = studentService.getAuthenticatedStudent().orElseThrow();
        semester.setOwner(student);
        semesterService.save(semester);

        return COURSE_CREATE_REDIRECT;
    }

    @PostMapping(value = "/create", params = {"addInstructor"})
    public String addInstructor(Model model) {
        Instructor instructor = new Instructor();
        instructor.getOfficeHoursList().add(new OfficeHoursBlock());
        model.addAttribute(instructor);

        return INSTRUCTOR_EDIT;
    }

    @PostMapping(value = "/create", params = {"addOfficeHours"})
    public String addOfficeHours(Instructor instructor, Model model) {
        instructor.getOfficeHoursList().add(new OfficeHoursBlock());
        model.addAttribute(instructor);

        return INSTRUCTOR_EDIT;
    }


    @PostMapping(value = "/create", params = {"commitInstructor"})
    public String createInstructor(Instructor instructor, RedirectAttributes redirectAttributes) {
        System.out.println("Saving instructor: " + instructor);
        final Student student = studentService.getAuthenticatedStudent().orElseThrow();
        instructor.setOwner(student);
        for (var ohb : instructor.getOfficeHoursList()) {
            ohb.setInstructor(instructor);
        }
        instructorService.save(instructor);

        redirectAttributes.addFlashAttribute("message", "Created Instructor");

        return COURSE_CREATE_REDIRECT;
    }

    @PostMapping(value = "/create", params = {"save"})
    public String createCourse(@Valid Course course) {
        courseService.addCourse(course);

        return COURSE_REDIRECT;
    }
}
