package net.nsnsns.ciscms.controllers;

import net.nsnsns.ciscms.models.Course;
import net.nsnsns.ciscms.models.Gradeable;
import net.nsnsns.ciscms.services.CourseService;
import net.nsnsns.ciscms.services.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;
    private final StudentService studentService;

    CourseController(CourseService courseService, StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @GetMapping
    public String courseOverview(Model model) {
        model.addAttribute("courses", courseService.getCourses());
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "courses";
    }

    @GetMapping("/{id}")
    public String courseDetail(@PathVariable(name = "id") Integer id, Model model) {
        Optional<Course> course = courseService.getCourse(id);

        if (course.isPresent()) {
            model.addAttribute("course", course.get());
            return "course_detail";
        }

        System.out.println(String.format("Course by id %d doesn't exist", id));
        return "redirect:/course";
    }

    @GetMapping("/{id}/edit")
    public String editCourse(@PathVariable(name = "id") Integer id, Model model) {
        Course course = courseService.getCourse(id).orElseThrow();
        model.addAttribute("course", course);


        return "course_edit";
    }

    @PostMapping(value = "/{cid}/edit", params = {"addRow"})
    public String addGradeableRow(@PathVariable(name = "cid") Long courseId, Course course) {

        Gradeable g = new Gradeable();
        g.setCourse(course);
        g.setOwner(studentService.getAuthenticatedStudent().orElseThrow());
        course.getGradeables().add(g);

        return "course_edit";
    }

    @PostMapping(value = "/{cid}/edit")
    public String saveCourse(@PathVariable(name = "cid") Long courseId, Course course) {
        System.out.println("Saving course details");
        courseService.saveCourse(course);

        return "redirect:/course/" + courseId;
    }


    @GetMapping("/create")
    public String createCourse(Model model) {
        model.addAttribute("course", new Course());

        return "course_creation";
    }

    @PostMapping("/create")
    public String createCourse(Course course) {
        System.out.println("Creating course: " + course);
        courseService.addCourse(course);

        return "redirect:/course";
    }
}
